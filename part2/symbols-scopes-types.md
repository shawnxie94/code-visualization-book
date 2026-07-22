# 符号表、作用域与类型关系

## 本章要解决的问题

为什么有了 AST 还不够？名字、作用域和类型如何把“表达式”变成“可导航关系”？

## 读者读完应获得什么

1. 能解释符号、定义、引用、作用域、类型的基本关系。
2. 能说明 IDE“跳转到定义”背后需要哪些查询。
3. 能在 `mini-shop` 上区分同名文本与真实绑定。

## 本章不讲什么

- 不完整实现类型推断算法。
- 不覆盖所有 OOP/泛型边角。

---

![定义-引用绑定示意](../imgs/fig-04-def-ref.svg)

AST 告诉我们“这里有一个名字、一次调用”，但还不能稳定回答：

- 这个名字定义在哪里？
- 它在当前作用域是否可见？
- 调用应绑定到哪个方法？

符号表、作用域和类型系统补的就是这一层。

## 定义与引用

以 `mini-shop` 为例：

```java
private final DiscountPolicy discountPolicy;

double total = discountPolicy.apply(customerType, amount);
```

- 定义：字段 `discountPolicy` 的声明
- 引用：方法体中的 `discountPolicy`
- 调用引用：`apply` 应绑定到 `DiscountPolicy.apply`

如果只有 AST，你只知道有一个标识符；有了符号信息，才能建立：

```text
ref:discountPolicy --resolves_to--> field:PricingService.discountPolicy
call:apply --resolves_to--> method:DiscountPolicy#apply
```

## 作用域

作用域决定名字可见性。常见层次：

```text
编译单元/文件
 -> 类/接口
 -> 方法
 -> 语句块
```

同名变量可以在不同作用域合法共存。代码理解系统必须按作用域解析，而不能全局字符串匹配。

## 类型关系

类型帮助消解重载、继承和接口实现：

- 方法参数类型影响重载选择
- 接口引用可能指向多个实现
- 泛型擦除/推断影响静态确定性

对可视化与影响面来说，类型关系至少应支持：

| 关系 | 用途 |
| --- | --- |
| extends / implements | 架构与层次图 |
| typed_as | 字段/参数/返回值 |
| overrides | 多态调用候选 |
| resolves_to | 精确或候选定义 |

## IDE 跳转背后的查询

“跳转到定义”通常不是魔法，而是：

```text
1. 定位光标处 AST 节点
2. 取标识符与上下文类型
3. 查符号表得到候选定义
4. 按作用域/类型排序消解
5. 跳到定义节点源码位置
```

“查找引用”则是反向索引：从定义找所有 resolves_to 边。

## 对调用图的影响

未做符号消解时，`apply(` 只能得到候选调用；完成消解后，`mini-shop` 可得到较可靠边：

```text
PricingService.calculateTotal -> DiscountPolicy.apply
OrderService.createOrder -> PricingService.calculateTotal
```

这对 `PR-42` 影响面分析是前提：变更实体必须能连到真实调用方。

## 和 AI Agent 的关系

Agent 若只搜索 `apply` 文本，可能误伤无关方法。更稳妥的上下文应包含：

- 目标符号 ID
- 定义位置
- 直接引用与调用方
- 类型与模块边界

也就是把符号层事实写进上下文包，而不是只贴源码片段。

## 最小符号表 schema（教学可用）

把符号层事实落成可查询记录时，不必一上来做完整编译器。最小表可以是：

```json
{
  "symbols": [
    {
      "id": "method:DiscountPolicy#apply",
      "kind": "method",
      "name": "apply",
      "owner": "class:DiscountPolicy",
      "file": "src/main/java/com/minishop/pricing/DiscountPolicy.java",
      "start_line": 3,
      "end_line": 10,
      "signature": "apply(String customerType, double amount) -> double"
    }
  ],
  "refs": [
    {
      "id": "ref:PricingService#calculateTotal:discountPolicy",
      "name": "discountPolicy",
      "file": "src/main/java/com/minishop/pricing/PricingService.java",
      "line": 12,
      "resolves_to": "field:PricingService#discountPolicy",
      "confidence": "high"
    },
    {
      "id": "call:PricingService#calculateTotal->DiscountPolicy#apply",
      "name": "apply",
      "file": "src/main/java/com/minishop/pricing/PricingService.java",
      "line": 13,
      "resolves_to": "method:DiscountPolicy#apply",
      "confidence": "high",
      "receiver_type": "DiscountPolicy"
    }
  ]
}
```

关键字段解释：

| 字段 | 作用 |
| --- | --- |
| `id` | 稳定符号 ID，供图谱与 Agent 上下文引用 |
| `owner` | 所属类/文件，支持作用域导航 |
| `resolves_to` | 定义-引用边的目标 |
| `confidence` | 绑定把握；中低置信必须保留，不可静默丢弃 |
| `signature` / `receiver_type` | 帮助重载与多态消解 |

这张表直接支撑：

1. 跳转到定义
2. 查找引用
3. 调用图边
4. `PR-42` 变更实体定位

## 失败模式对照

| 错误做法 | 症状 | 正确做法 |
| --- | --- | --- |
| 全局字符串匹配 `apply` | 误绑无关方法 | 按作用域 + 接收者类型消解 |
| 忽略 shadowing | 内层变量被当成外层字段 | 从内向外查作用域链 |
| 把候选当唯一 | 影响面漏路径或假精确 | 输出候选集 + confidence |
| 只存名字不存 ID | 重命名后历史断链 | 稳定 ID + 限定名 |
| Agent 上下文只贴源码 | 改错同名符号 | 附 `symbol_id` 与 callers |

## 工作示例：从 AST 到可导航边

输入：`PricingService.calculateTotal` 中的 `discountPolicy.apply(...)`。

处理步骤：

1. AST 识别 `MethodCallExpr` 与 `NameExpr`
2. 作用域解析 `discountPolicy` → 字段定义
3. 取字段类型 `DiscountPolicy`
4. 在 `DiscountPolicy` 中按签名匹配 `apply`
5. 写出 `calls` 边与 `resolves_to` 边

输出（简化）：

```text
field:PricingService#discountPolicy  typed_as  class:DiscountPolicy
call@PricingService:13  resolves_to  method:DiscountPolicy#apply
method:PricingService#calculateTotal  calls  method:DiscountPolicy#apply
```

没有第 2-4 步，就只剩“看到了 apply 三个字符”。

## 常见问题：符号与类型

### 为什么 IDE 能跳转，我的脚本却不行？

IDE 背后通常有完整语言服务（符号表 + 类型 + 索引）。脚本若只扫 AST 文本，缺少绑定层。

### 动态代理 / DI 注入怎么办？

静态层给候选与置信度；运行时/配置事实可在后续动态分析章补充，而不是假装静态唯一。

### 是否必须实现完整类型推断？

教学与影响面第一阶段不需要。优先做定义-引用、简单类型与方法绑定，再按场景加深。


## 局限

- 动态语言、反射、依赖注入会降低静态绑定精度。
- 跨项目/生成代码需要额外索引。
- 多实现多态时往往只能给候选集，不能假装唯一确定。

## 小结

1. AST 给结构，符号与类型给绑定。
2. 作用域是正确解析名字的前提。
3. 定义-引用关系是 IDE、调用图和影响面的共同基础。
4. Agent 上下文应使用符号 ID，而不是裸字符串。

## 解析不确定时的工程策略

当静态绑定无法唯一确定时，系统不应假装唯一：

```text
resolves_to candidates = [ImplA.m, ImplB.m]
confidence = medium
reason = polymorphic_receiver
```

对影响面，这意味着路径要按候选集合扩展；对 Agent，这意味着修改前要更高确认级别。把不确定性藏起来，比“查不到”更危险。

## mini-shop 中的绑定练习

- `discountPolicy`：字段定义在 `PricingService`
- `apply`：绑定到 `DiscountPolicy.apply`
- `save`：绑定到 `OrderRepository.save`，不能与日志文本混淆

这三条是后续调用图与 PR-42 影响面的前提。

## 关键要点复盘

围绕「符号表、作用域与类型关系」，读者离开本章前应能做到：

1. 解释 AST 为何不够，需要定义-引用
2. 在 mini-shop 标出 discountPolicy 绑定
3. 说明同名 apply 不能直接当精确边
4. 写出不确定绑定时的 confidence 策略
5. 衔接到 IR/CFG/DFG

若任一做不到，请先复习本章例子与练习，再继续向后读。

## 练习

1. 在 `PricingService` 中标出 `discountPolicy` 的定义点与引用点。
2. 说明为何“同名 apply”不能直接当精确调用边。
3. 用 LSP 的 go-to-definition / find-references 类比，写出图谱应支持的两条查询。

## 本章检查清单

1. 定义与引用是否能落到稳定 ID
2. 是否处理同名/多态的 confidence
3. Agent 上下文是否带 symbol_id 而非裸字符串

## 本章导航

- 上一章：[从字符到 AST](source-to-ast.md)
- 下一章：[IR、SSA、CFG 与 DFG](ir-ssa-cfg-dfg.md)
- 相关章：[静态分析](../part3/static-analysis.md)；[采集源码结构](../part6/collect-source-structure.md)

## 延伸阅读与参考资料

- [JLS §6 Names](https://docs.oracle.com/javase/specs/jls/se17/html/jls-6.html)：名称与作用域一级规则。资料卡：`../docs/research-cards/rc-jls-names.md`
- [Language Server Protocol](https://microsoft.github.io/language-server-protocol/)：定义/引用查询的协议化。资料卡：`../docs/research-cards/rc-lsp.md`
- [TypeScript Compiler API](https://github.com/microsoft/TypeScript/wiki/Using-the-Compiler-API)：类型与符号信息工程入口。
- [JavaParser Symbol Solver 相关文档](https://javaparser.org/)：Java 符号解析实践。
- [Oracle Java Tutorials: Packages/Names](https://docs.oracle.com/javase/tutorial/java/package/index.html)：包与可见性基础。
- 本书案例：[`examples/mini-shop/`](../examples/mini-shop/)。

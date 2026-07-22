# 贯穿模拟案例：mini-shop

本目录定义全书共用的模拟案例包。案例不对应真实业务或真实仓库，目标是让不同章节复用同一组代码、同一组变更和同一组 Agent 任务。

## 案例目标

`mini-shop` 模拟一个极小的订单计价系统，覆盖本书主线：

```text
源码结构 -> AST/符号/调用 -> 代码图谱 -> 变更影响面 -> Agent 上下文 -> 验证报告
```

它故意保持很小，但足够体现：

1. 跨模块调用：`order` 依赖 `pricing` 和 `payment`
2. 同名方法歧义：多处出现 `save` / `calculate`
3. 测试关联：改计价逻辑会影响订单测试
4. Agent 风险：只看局部文件时容易漏改调用方和测试

## 仓库结构

```text
examples/mini-shop/
  README.md
  src/main/java/com/minishop/
    order/
      OrderController.java
      OrderService.java
      OrderRepository.java
    pricing/
      PricingService.java
      DiscountPolicy.java
    payment/
      PaymentClient.java
  src/test/java/com/minishop/
    order/
      OrderServiceTest.java
    pricing/
      PricingServiceTest.java
```

## 核心业务故事

用户创建订单时：

1. `OrderController.create` 接收请求
2. `OrderService.createOrder` 组装订单
3. `PricingService.calculateTotal` 计算总价
4. `DiscountPolicy.apply` 应用折扣
5. `PaymentClient.charge` 发起支付
6. `OrderRepository.save` 持久化订单

## 贯穿任务

| 任务 ID | 场景 | 用于章节 |
| --- | --- | --- |
| T1 | 从 `PricingService.calculateTotal` 源码提取 AST | part2/source-to-ast |
| T2 | 解析 `calculateTotal` 的符号、参数类型和方法引用 | part2/symbols-scopes-types |
| T3 | 构建 order/pricing/payment 调用图 | part3/code-graph-model |
| T4 | PR-42：修改折扣逻辑后的影响面分析 | part4/change-impact-verification |
| T5 | Agent 任务：调整 VIP 折扣，需要正确上下文包 | part5/agent-context-engineering |
| T6 | 生成验证报告：相关测试、风险路径、查询轨迹 | part6/ai-change-verification-report |

## PR-42 变更摘要

假设一次未完成修改：

- 文件：`pricing/DiscountPolicy.java`
- 目标：VIP 折扣从 `0.9` 改为 `0.85`
- 风险：
  - `PricingService.calculateTotal` 直接受影响
  - `OrderService.createOrder` 间接受影响
  - `OrderServiceTest` 和 `PricingServiceTest` 需要更新断言
  - 日志文本中的 `calculate` 字符串不应被误判为调用

## Agent 常见错误

如果 Agent 只读取 `DiscountPolicy.java`：

1. 可能只改常量，不更新测试
2. 可能不知道 `OrderService` 依赖该折扣结果
3. 可能用字符串搜索误匹配日志文本
4. 可能无法说明影响路径，导致 Review 无法审计

正确上下文至少应包含：

- 目标符号：`DiscountPolicy.apply`
- 直接调用方：`PricingService.calculateTotal`
- 上层调用方：`OrderService.createOrder`
- 相关测试：`PricingServiceTest`、`OrderServiceTest`
- 架构约束：pricing 不应直接依赖 payment

## 使用约定

1. 正文引用案例时统一写 `mini-shop`，不要另起一套虚构业务。
2. 例子优先使用 Java，因为类型和声明结构更适合讲解 AST/符号。
3. 如需对照其他语言，可补充 TypeScript/Python 片段，但不替换主案例。
4. 所有报告、JSON、查询接口示例都应基于本案例的实体命名。

## 相关文件

- 案例源码：[`../../examples/mini-shop/`](../../examples/mini-shop/)
- AST 样章：[`../../part2/source-to-ast.md`](../../part2/source-to-ast.md)
- 影响面样章：[`../../part4/change-impact-verification.md`](../../part4/change-impact-verification.md)
- Agent 上下文样章：[`../../part5/agent-context-engineering.md`](../../part5/agent-context-engineering.md)

## 标准产物（artifacts）

为便于各章引用同一输入输出，`examples/mini-shop/artifacts/` 提供：

| 文件 | 用途 |
| --- | --- |
| `code-graph.json` | 最小代码图谱 |
| `pr-42.diff` | 模拟 PR Diff |
| `impact-report-pr-42.json` | 影响面分析结果 |
| `agent-context-pack.json` | Agent 上下文包 |
| `verification-report-pr-42.md` | 验证报告 |

后续写作与实践实现应以这些契约为准。

## 标准产物路径

| 产物 | 路径 |
| --- | --- |
| 代码图谱 | `examples/mini-shop/artifacts/code-graph.json` |
| PR diff | `examples/mini-shop/artifacts/pr-42.diff` |
| 影响面报告 | `examples/mini-shop/artifacts/impact-report-pr-42.json` |
| Agent 上下文包 | `examples/mini-shop/artifacts/agent-context-pack.json` |
| 验证报告 | `examples/mini-shop/artifacts/verification-report-pr-42.md` |

正文与练习引用上述路径时，实体 ID 以图谱为准（例如 `method:DiscountPolicy#apply`）。

## 全书跟做主线（建议 90 分钟）

把下面顺序当作阅读/实践主线；各章细节都服务于这条链。

| 步 | 动作 | 打开的文件 | 对应章节 |
| --- | --- | --- | --- |
| 1 | 读关键路径与测试断言（180.0） | `src/**`、两个 Test | part2/part4 |
| 2 | 读 PR-42 diff | `artifacts/pr-42.diff` | part3 变更分析、part4 影响面 |
| 3 | 对照图谱实体与调用边 | `artifacts/code-graph.json` | part3 图谱、part6 建图 |
| 4 | 对照影响面报告 | `artifacts/impact-report-pr-42.json` | part4 影响面、part6 影响面实现 |
| 5 | 读 Agent 上下文包 | `artifacts/agent-context-pack.json` | part5 上下文/查图 |
| 6 | 对照验证报告并理解人工确认项 | `artifacts/verification-report-pr-42.md` | part5 Review、part6 验证报告 |

金标数字：

- 折扣：`0.9` → `0.85`
- 示例总价：`180.0` → `170.0`
- 变更实体：`method:DiscountPolicy#apply`
- 架构规则：`pricing` 不得依赖 `payment`

源码保持改前状态，便于你自己应用 diff 并复算。

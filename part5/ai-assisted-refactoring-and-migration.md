# AI 辅助重构与系统迁移

## 本章要解决的问题

AI 如何参与重构与迁移，同时避免破坏系统边界与行为？

## 读者读完应获得什么

1. 能设计“小步改动 + 行为保持 + 对比证据”的流程。
2. 能判断哪些重构适合 Agent 自动做。
3. 能定义迁移前后的验证报告。

## 本章不讲什么

- 不提供某框架版本升级的逐步操作手册。
- 不鼓励无测试大爆炸重写。

## 本章与邻章边界

- 本章聚焦受约束的重构/迁移协作方式。
- 不写成某框架升级操作手册；架构事实地图见第四篇遗留改造章。

---

重构与迁移的核心不是“把代码改新”，而是“在约束下改变结构，并证明行为可接受”。AI 适合加速机械步骤，不适合无地图推进。

## 推荐闭环

```mermaid
flowchart LR
 Map[现状地图] --> Guard[测试/规则护栏]
 Guard --> Step[小步修改]
 Step --> DiffGraph[结构/依赖对比]
 DiffGraph --> Verify[测试与影响面]
 Verify --> Next[下一步 / 回滚]
```

## 适合 AI 的任务

- 重命名与导入修复
- 重复样板转换
- 测试骨架生成
- 迁移脚本草稿
- 依赖差异总结

## 不适合无约束交给 AI 的任务

- 边界不清的模块拆分
- 无表征测试的核心交易路径改写
- 多服务协议同时变更且无可观测性

## mini-shop 小例子

目标：把折扣系数提取为可配置常量，行为先保持 `0.9`，再切换到 `0.85`。

1. 用测试锁住 VIP/非 VIP 价格
2. Agent 只改 `DiscountPolicy` 结构，不改外部 API
3. 对比调用图：对外调用关系应不变
4. 再改系数，跑影响面与测试
5. 输出迁移报告：行为差异、测试更新、规则检查

## 验证对比项

| 项 | 迁移前 | 迁移后 |
| --- | --- | --- |
| 关键路径 | create->...->apply | 应保持 |
| 架构规则 | pricing 不依赖 payment | 应保持 |
| VIP 总价 | 180.0 | 170.0（若业务切换） |
| 测试 | 旧断言 | 更新后全绿 |

## 失败案例（应避免）

1. **无测试抽取接口**：Agent 把 `DiscountPolicy` 抽成接口并移动包名，但没有表征测试，Review 无法判断价格行为是否保持。
2. **越界清理**：任务只是改折扣，Agent 顺便“优化”`OrderService` 日志与支付重试，导致 diff 噪声与风险上升。
3. **依赖反向**：为了复用支付费率，Agent 让 `pricing` 依赖 `payment`，破坏架构规则。

这些失败都可通过“范围约束 + 规则检查 + 影响面报告”在合并前拦截。

## 迁移报告模板（可直接套用）

每次 AI 参与的结构改造，至少产出：

```markdown
# Migration Report: <title>
## Scope
- allowed files/modules:
- forbidden changes:
## Guardrails before edit
- characterization tests:
- architecture rules:
## Steps
1. behavior-preserving structural change
2. verify tests/graph/rules
3. intentional behavior change (if any)
4. update tests + impact report
## Evidence
- call graph before/after:
- tests before/after:
- rules before/after:
## Residual risks
- ...
## Rollback
- ...
```

对 `mini-shop` 折扣配置化，第 1 步应保持 VIP 总价 `180.0`；第 3 步才切到 `0.85` 并更新断言到 `170.0`。

## 范围约束如何写给 Agent

不要只说“帮我重构定价模块”，而要给可检查边界：

```text
GOAL: extract discount factor constant without behavior change
ALLOW: DiscountPolicy.java only
DENY: OrderService, PaymentClient, public API signatures
MUST_KEEP: VIP total=180.0 for qty=2 unit=100
MUST_PASS: architecture rule pricing-no-payment
OUTPUT: diff + callgraph summary + test results
```

边界越可机器检查，AI 越不容易“顺便优化”。

## 与影响面/验证章的衔接

- 小步结构改：重点看调用图是否保持、测试是否仍绿
- 行为变更步：走完整影响面报告（见变更影响分析章）
- 合并前：验证报告必须同时包含规则与相关测试（见 AI Review 证据 / 验证报告章）


## 局限

- 小步策略依赖测试与规则护栏，缺少护栏时 AI 容易扩大 diff。
- 迁移中的业务语义取舍无法只由图谱决定。
- 跨仓库/跨服务迁移需要比单仓 `mini-shop` 更强的链路观测。

## 小结

1. 重构/迁移先护栏后改动。
2. AI 负责加速，系统负责约束与证明。
3. 每次小步都要有可对比证据。
4. 无测试无地图的大改应拒绝自动化裸奔。

## 行为保持证明的层次

1. **编译/解析通过**（必要但不充分）
2. **表征测试通过**
3. **架构规则通过**
4. **关键路径对比（调用图/契约）**
5. **必要时的运行时对比**

AI 很容易完成第 1 层并宣称成功。出版级工程实践要求至少到第 2-4 层。

## 工作示例：可接受的 AI 小步

任务：折扣系数配置化，但行为暂保持 0.9

1. Agent 仅修改 `DiscountPolicy` 内部读取常量/配置
2. 表征测试保持 180.0
3. 调用图对外边不变
4. 再开第二个 PR 调整为 0.85 并更新测试

把“结构改造”和“行为变更”拆开，Review 复杂度会显著下降。

## 常见问题：AI 重构

### 能否让 Agent 一次完成大迁移？

通常不应。应拆成可验证小步，先行为保持，再行为变更。

### 没有测试能不能自动重构？

高风险。至少先补表征测试，再允许结构改动。

### 如何判断 AI 重构成功？

解析通过、测试通过、架构规则通过、关键路径对比通过，缺一不可轻易宣称成功。

## 本章检查清单

1. 是否拆成小步
2. 是否有表征测试
3. 是否对比调用图/规则
4. 是否保留回滚点
5. 是否避免无关清理 diff

## 关键要点复盘

围绕「AI 辅助重构与系统迁移」，读者离开本章前应能做到：

1. 拆分行为保持与行为变更两步
2. 写出给 Agent 的范围约束
3. 使用迁移报告模板
4. 用调用图/规则/测试证明小步成功
5. 衔接到理解基础设施

若任一做不到，请先复习本章例子与练习，再继续向后读。

## 反模式对照表

| 反模式 | 后果 | 纠正 |
| --- | --- | --- |
| 大爆炸重写 | 无法定位回归 | Strangler 小步 |
| 无表征测试抽取接口 | 行为漂移 | 先锁测试 |
| 顺手清理无关代码 | diff 不可审 | 范围冻结 |
| 忽略架构规则 | 边界腐蚀 | 规则门禁 |
| 只看编译通过 | 假成功 | 多层证明 |

## 练习

1. 把折扣配置化拆成 3 步，并给每步验收标准。
2. 指出 2 类不应交给无约束 Agent 的改造。
3. 设计迁移前后对比表：路径、规则、测试、行为。

## 本章导航

- 上一章：[AI 生成代码的 Review 证据层](ai-code-review-evidence.md)
- 下一章：[从代码可视化到软件理解基础设施](software-understanding-infrastructure.md)
- 相关章：[给 AI Agent 的查询接口](../part6/query-interface-for-ai-agent.md)；[AI 修改后的验证报告](../part6/ai-change-verification-report.md)

## 延伸阅读与参考资料

- [Strangler Fig](https://martinfowler.com/bliki/StranglerFigApplication.html)。资料卡：`../docs/research-cards/rc-strangler-fig.md`
- [Refactoring.com catalog](https://refactoring.com/catalog/)
- [Characterization testing 概念](https://michaelfeathers.silvrback.com/characterization-testing)
- [ADR](https://adr.github.io/)。资料卡：`../docs/research-cards/rc-adr.md`
- [ArchUnit](https://www.archunit.org/)
- 本书案例：[`examples/mini-shop/`](../examples/mini-shop/)。

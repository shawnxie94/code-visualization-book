# Agent 上下文工程

## 本章要解决的问题

如何给 AI Agent 提供正确、充分、可验证的代码上下文，而不是单纯扩大窗口？

## 读者读完应获得什么

1. 能区分“上下文窗口”和“代码理解”。
2. 能设计包含符号、调用方、测试和规则的上下文包。
3. 能记录查询轨迹，使 Agent 行为可审计。

## 本章不讲什么

- 不讨论具体模型供应商的提示词技巧大全。
- 不把向量检索当成唯一上下文方案。

## 本章与邻章边界

- 本章聚焦**改前**：如何构造任务化、可审计的上下文包。
- 图谱工具清单与协议映射详见“代码图谱如何服务 AI Agent”；改后审计详见 Review 证据层。

---

Agent 上下文工程关注如何围绕任务选择、组织和约束信息。如果没有这层工程，Agent 容易变成“会写代码的搜索器”：能读文件、改文件，但不一定知道哪些边界不能跨、哪些测试必须跑。

```mermaid
flowchart TB
 Task[任务/Issue] --> Intent[意图和边界]
 Intent --> Retrieval[语义检索]
 Intent --> GraphQuery[代码图谱查询]
 GraphQuery --> Symbols[符号/调用/测试/规则]
 Retrieval --> Docs[相关文档片段]
 Symbols --> Pack[Agent 上下文包]
 Docs --> Pack
 Pack --> Agent[AI Agent 修改代码]
 Agent --> Evidence[查询轨迹与验证证据]
```
![Agent 上下文包结构（精确技术图）](../imgs/fig-10-agent-context.svg)

> 后续 AI 配图备注：可生成“Agent 先查询代码图谱再修改”的流程插画。

## 上下文窗口不等于代码理解

把更多文件塞进提示，并不等于更好理解。有效上下文应满足：

1. **相关**：与任务有明确关系
2. **结构化**：说明符号与依赖，而不只是文本
3. **可验证**：结论能追溯到查询与源码

因此上下文工程 = 任务理解 + 检索 + 图谱查询 + 裁剪 + 证据组织。

## 任务：调整 VIP 折扣

任务描述：

```text
将 mini-shop 的 VIP 折扣从 0.9 调整为 0.85，并保证相关测试通过。
```

意图边界：

| 项 | 内容 |
| --- | --- |
| in_scope | `DiscountPolicy.apply`、相关定价/订单测试 |
| out_of_scope | 支付渠道集成、非 VIP 规则重做、无关注架重构 |

先写清边界，再取上下文，可减少 Agent 乱动。

## 相关文件选择

错误做法：全文搜索 `0.9` 或 `calculate`，把日志字符串也当候选。
正确做法：先定位符号，再扩展邻居。

最小相关集合：

1. `DiscountPolicy.java`（修改目标）
2. `PricingService.java`（直接调用方）
3. `PricingServiceTest.java` / `OrderServiceTest.java`（断言依赖）
4. 可选：`OrderService.java`（理解金额如何流向支付）

## 必须查询的图谱问题

1. `find_symbol("DiscountPolicy.apply")`
2. `find_callers(method:DiscountPolicy#apply)`
3. `related_tests(method:DiscountPolicy#apply)`
4. `architecture_rules(module=pricing)`

这些查询的结果应进入上下文包，而不是只留在系统日志里。

## 上下文包结构

完整样例：[`examples/mini-shop/artifacts/agent-context-pack.json`](../examples/mini-shop/artifacts/agent-context-pack.json)

```text
task
intent.in_scope / out_of_scope
symbols[]
callers[]
related_tests[]
architecture_rules[]
snippets[]
query_trace[]
```

示例：

```json
{
 "task": "将 VIP 折扣从 0.9 调整为 0.85，并保证相关测试通过",
 "symbols": [{"id": "method:DiscountPolicy#apply", "role": "primary_edit_target"}],
 "callers": [
 "method:PricingService#calculateTotal",
 "method:OrderService#createOrder"
 ],
 "related_tests": [
 "test:PricingServiceTest#shouldApplyVipDiscount",
 "test:OrderServiceTest#shouldCreateVipOrderWithDiscount"
 ],
 "architecture_rules": ["pricing 模块不得直接依赖 payment 模块"]
}
```

## 查询轨迹和审计

每次工具调用都应记录：

```text
tool name
arguments
result summary
timestamp
```

价值：

- Reviewer 可检查 Agent 是否查过测试
- 失败时可复盘上下文是否缺失
- 可对比“模型声称”和“系统检索到的事实”

对 AI Coding 工具建设者，查询轨迹是产品能力，不是调试边角。

## 与验证闭环衔接

上下文包负责“改前理解”，验证报告负责“改后证明”：

```text
上下文包 -> Agent 修改 -> 影响面分析 -> 测试结果 -> 验证报告
```

`PR-42` 验证报告样例见 [`examples/mini-shop/artifacts/verification-report-pr-42.md`](../examples/mini-shop/artifacts/verification-report-pr-42.md)。

## 常见失败模式

1. 只给目标文件，不给调用方和测试
2. 用文本相似度替代符号关系
3. 无 out_of_scope，导致越改越大
4. 无查询轨迹，Review 只能盲信
5. 把低置信度调用边当确定事实

## 设计原则（可检查）

一个合格上下文包应能通过这些问题：

1. 是否包含**主编辑符号**及其源码位置？
2. 是否包含**直接/关键调用方**，而不只是相似文本？
3. 是否包含**相关测试**？
4. 是否包含**架构规则/范围边界**？
5. 是否包含**查询轨迹**以便审计？

任一题为“否”，Agent 就更像在猜，而不是在受约束地修改。

## 局限

- 图谱不完整时上下文会偏
- 过严裁剪可能漏掉隐式依赖
- 上下文工程不能替代测试与人工设计审查

## 小结

1. 上下文工程是任务化的信息选择，不是窗口堆料。
2. 符号、调用方、测试、规则是最小必备结构。
3. 上下文包应可序列化、可审计、可复用。
4. 查询轨迹让 Agent 从“会改”变成“可审查地改”。

## 关键要点复盘

围绕本章，读者离开前应能做到：

1. 设计上下文包字段
2. 说明切片规则（seed/include/exclude）
3. 把 query_trace 写入包
4. 指出全仓粘贴源码的失败
5. 衔接到图谱查询工具

若任一做不到，请先复习本章例子与练习，再继续向后读。

## 练习

1. 为 VIP 折扣任务写 in_scope / out_of_scope。
2. 写出 4 次图谱查询及期望结果，并形成 query_trace。
3. 比较“只给 DiscountPolicy.java”与完整上下文包的失败风险。
4. 将 `agent-context-pack.json` 改成更短但信息不丢的版本。

## 常见问题：上下文工程

### 上下文窗口更大是否就够？

不够。需要结构化符号、边界与轨迹。

### 要不要把全仓源码塞进 prompt？

不要。按 seed/include/exclude 切片。

## 本章检查清单

1. 上下文包字段是否完整
2. 是否包含 related_tests 与 rules
3. 是否记录 query_trace

## 本章导航

- 上一章：[为什么 AI 时代更需要代码理解](why-code-understanding-matters-in-ai-era.md)
- 下一章：[代码图谱如何服务 AI Agent](code-graph-for-ai-agent.md)
- 相关章：[给 AI Agent 的查询接口](../part6/query-interface-for-ai-agent.md)；[AI 修改后的验证报告](../part6/ai-change-verification-report.md)

## 延伸阅读与参考资料

- [GitHub Copilot: Explore a codebase](https://docs.github.com/en/copilot/tutorials/explore-a-codebase)。资料卡：`../docs/research-cards/rc-github-copilot-explore.md`
- [Model Context Protocol](https://modelcontextprotocol.io/)。资料卡：`../docs/research-cards/rc-mcp.md`
- [LSP](https://microsoft.github.io/language-server-protocol/)：符号级检索基础。资料卡：`../docs/research-cards/rc-lsp.md`
- [RAG survey / retrieval literature 入口](https://arxiv.org/)（检索 Retrieval-Augmented Generation）：语义检索与结构检索互补。
- [SWE-bench](https://github.com/swe-bench/SWE-bench)：仓库级任务对上下文的要求。资料卡：`../docs/research-cards/rc-swe-bench.md`
- 本书样例：[`examples/mini-shop/artifacts/agent-context-pack.json`](../examples/mini-shop/artifacts/agent-context-pack.json)。

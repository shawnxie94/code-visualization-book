# 给 AI Agent 的查询接口

## 本章要解决的问题

Agent 应该通过哪些结构化工具查询代码图谱？

## 读者读完应获得什么

1. 能给出最小工具 schema。
2. 能返回带来源与置信度的结果。
3. 能记录 query_trace。

## 本章不讲什么

- 不实现完整权限系统。
- 不让 Agent 直接任意写图谱。

---

## 设计原则

1. 输入明确、输出结构化
2. 结果带来源证据
3. 标注不确定性
4. 查询可追踪
5. 写操作与查询分离

## 工具清单

### find_symbol

```json
{
 "name": "find_symbol",
 "input": {"query": "DiscountPolicy.apply"},
 "output": {
 "matches": [
 {
 "id": "method:DiscountPolicy#apply",
 "file": "src/main/java/com/minishop/pricing/DiscountPolicy.java",
 "start_line": 4,
 "end_line": 10
 }
 ]
 }
}
```

### find_callers / find_callees

```json
{
 "name": "find_callers",
 "input": {"symbol": "method:DiscountPolicy#apply", "depth": 3},
 "output": {
 "callers": [
 {"id": "method:PricingService#calculateTotal", "confidence": "high"}
 ]
 }
}
```

### impact_analysis

输入 Diff 或 changed files，输出与影响面章节一致的结构。

### related_tests

```json
{
 "input": {"symbol": "method:DiscountPolicy#apply"},
 "output": {
 "tests": [
 "test:PricingServiceTest#shouldApplyVipDiscount",
 "test:OrderServiceTest#shouldCreateVipOrderWithDiscount"
 ]
 }
}
```

### architecture_rules

```json
{
 "input": {"module": "pricing"},
 "output": {
 "rules": [
 {
 "id": "rule:pricing-no-payment",
 "status": "pass"
 }
 ]
 }
}
```

## 查询轨迹

每次调用追加：

```json
{
 "tool": "find_callers",
 "args": {"symbol": "method:DiscountPolicy#apply"},
 "summary": "1 caller found",
 "ts": "2026-07-22T12:00:00Z"
}
```

轨迹应进入上下文包与验证报告。

## MCP / 本地 API 映射

可先实现 CLI：

```text
cv-query find_symbol --q DiscountPolicy.apply
cv-query find_callers --id method:DiscountPolicy#apply
```

再包装为 MCP tools 或 HTTP JSON。

## 错误处理

- 未知符号：返回 empty + suggestion
- 低置信结果：`confidence=low` 且 `needs_confirmation=true`
- 图未索引：明确错误，不可用幻觉补全

## 验收

- 对 mini-shop 图谱 5 类工具可用
- 结果可被 Agent 直接序列化进上下文包
- 轨迹完整

## 局限

- 接口不负责保证 Agent 一定正确使用结果。
- 无权限模型时不适合直接暴露到公网。
- 图不完整时，工具会诚实返回空/低置信，而不是编造。

## 小结

1. 工具少而稳，胜过自由对话式乱读仓库。
2. schema 与证据字段是接口核心。
3. 查询轨迹是审计能力。
4. 实现可从 CLI 平滑升级到 MCP。

## 安全与权限最小集

即便是教学系统，也建议预留：

1. **只读查询默认开启**
2. **写操作（若有）与查询分离**
3. **返回体避免塞入密钥/本地绝对路径敏感信息**
4. **对超大结果强制 limit + pagination**

示例：

```json
{
 "ok": false,
 "error": "RESULT_TOO_LARGE",
 "hint": "reduce depth or add filter"
}
```

Agent 面对该错误应收缩查询，而不是改去全文读取仓库绕过图谱。

## 契约测试

为防止接口漂移，实践项目应为每个工具准备契约样例：

- 输入 fixture
- 期望输出关键字段
- 在 `mini-shop` 图谱上的金标结果

例如 `find_callers(DiscountPolicy.apply)` 的金标应包含 `PricingService.calculateTotal`。

## 工具响应示例

`impact_analysis` 响应应可直接渲染：

```json
{
  "tool": "impact_analysis",
  "input": {"changed": ["method:DiscountPolicy#apply"]},
  "result": {
    "paths": [["method:DiscountPolicy#apply", "method:PricingService#calculateTotal", "method:OrderService#createOrder", "method:OrderController#create"]],
    "related_tests": ["test:PricingServiceTest#shouldApplyVipDiscount"],
    "risk": "medium"
  },
  "trace_id": "q-17"
}
```

错误响应也要结构化（未知 ID、图过期、权限不足），避免 Agent 把失败当成空影响面。


## 练习

1. 为 `find_callers` 写 JSON schema（输入/输出）。
2. 设计未知符号与低置信结果的错误返回。
3. 记录 PR-42 的完整 query_trace（至少 4 步）。

## 延伸阅读与参考资料

- [Model Context Protocol](https://modelcontextprotocol.io/)。资料卡：`../docs/research-cards/rc-mcp.md`
- [JSON Schema](https://json-schema.org/)
- [LSP](https://microsoft.github.io/language-server-protocol/)。资料卡：`../docs/research-cards/rc-lsp.md`
- [OpenAPI 参考](https://swagger.io/specification/)：若走 HTTP API 的契约思路
- [GitHub Copilot tool/platform docs](https://docs.github.com/en/copilot)
- 样例：[`agent-context-pack.json`](../examples/mini-shop/artifacts/agent-context-pack.json)

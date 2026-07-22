# 构建代码图谱

## 本章要解决的问题

如何把采集结果变成可查询、可校验的图谱模型？

## 读者读完应获得什么

1. 能定义 nodes/edges 表结构。
2. 能完成候选调用到实体的解析策略。
3. 能对图谱做基本完整性校验。

## 本章不讲什么

- 不引入必须的 Neo4j 集群。
- 不做分布式图计算。

---

采集产出的是素材，图谱构建负责统一 ID、补边、写属性和提供查询。

## 最小存储

### JSON

```json
{
 "nodes": [{"id": "...", "type": "method"}],
 "edges": [{"type": "calls", "from": "...", "to": "..."}]
}
```

### SQLite 示意

```sql
CREATE TABLE nodes(
 id TEXT PRIMARY KEY,
 type TEXT,
 name TEXT,
 file_path TEXT,
 start_line INT,
 end_line INT,
 props_json TEXT
);
CREATE TABLE edges(
 id TEXT PRIMARY KEY,
 type TEXT,
 from_id TEXT,
 to_id TEXT,
 confidence TEXT,
 source TEXT
);
```

## 调用消解策略（最小）

1. 同文件/同类方法名精确匹配
2. 唯一类名 + 方法名匹配
3. 多候选时保留 candidates，并标 `confidence=medium`
4. 无候选则保留未解析调用记录

对 `mini-shop`，`discountPolicy.apply` 可消解到 `DiscountPolicy.apply`。

## 必需边集合

对验收，至少存在：

```text
OrderController.create -> OrderService.createOrder
OrderService.createOrder -> PricingService.calculateTotal
PricingService.calculateTotal -> DiscountPolicy.apply
OrderService.createOrder -> PaymentClient.charge
tests 边连接两个测试到对应方法
```

参考：[`artifacts/code-graph.json`](../examples/mini-shop/artifacts/code-graph.json)

## 校验规则

1. 所有边端点都存在
2. method 节点有 file/line
3. 无自环 calls（除非真实递归）
4. 架构规则可独立存储并查询

## 增量更新

文件变更时：

1. 删除该文件旧节点与边
2. 重新采集该文件
3. 重建相关 calls/tests
4. 更新 `updated_at`

## 局限

- 最小消解策略在重载/多态场景会留下候选。
- JSON 方案不适合超大规模仓，需后续分层存储。
- 框架注入边需要专用增强，不会凭空出现。

## 小结

1. 先有干净模型，再谈高级图算法。
2. 消解允许候选，但必须标置信度。
3. 校验器比“看起来有数据”更重要。
4. JSON/SQLite 足够支撑全书实践。

## 进阶要点：增量更新事务

文件级重建时建议：

```text
begin
 delete nodes/edges where file_path = F
 insert new nodes/edges for F
 re-link unresolved calls touching F
commit
```

并记录 `index_version` 与 `updated_at`。Agent 查询若发现索引过期，应明确报错，而不是静默返回陈旧图。

## 工作示例：未解析调用的诚实表达

```json
{
 "type": "calls_unresolved",
 "from": "method:X#y",
 "callee_text": "foo.bar",
 "confidence": "low"
}
```

宁可保留 unresolved，也不要为了“图好看”伪造精确边。Agent 与 Reviewer 都需要知道哪里不确定。

## 常见问题：构建图谱

### 候选调用要不要丢弃？

不要丢，标记 confidence。

### 如何做增量？

按文件删旧建新并重链。

### 如何防陈旧索引？

index_version + 过期报错。

## 本章检查清单

1. schema 清晰
2. 消解策略
3. 校验器
4. 增量更新
5. 金标链可查

## 关键要点复盘

围绕「构建代码图谱」，读者离开本章前应能做到：

1. 用自己的话解释核心概念与边界
2. 在 `mini-shop` / `PR-42` 上指出对应实体、路径或产物
3. 说明它如何服务人或 AI 的具体决策
4. 列出至少两个局限或失败模式
5. 知道下一章将把它连接到哪一层能力

若任一做不到，请先复习本章例子与练习，再继续向后读。

## 练习

1. 写出 nodes/edges 的最小 SQL schema。
2. 对 `discountPolicy.apply` 给出消解策略与 confidence。
3. 列出 4 条图谱完整性校验。

## 延伸阅读与参考资料

- [SQLite docs](https://www.sqlite.org/docs.html)。资料卡：`../docs/research-cards/rc-sqlite.md`
- [Neo4j modeling](https://neo4j.com/docs/getting-started/data-modeling/)。资料卡：`../docs/research-cards/rc-neo4j-modeling.md`
- [Joern CPG](https://docs.joern.io/code-property-graph/)
- [LSP](https://microsoft.github.io/language-server-protocol/)。资料卡：`../docs/research-cards/rc-lsp.md`
- [JSON Graph / property graph 实践综述入口](https://neo4j.com/docs/)
- 样例：[`code-graph.json`](../examples/mini-shop/artifacts/code-graph.json)

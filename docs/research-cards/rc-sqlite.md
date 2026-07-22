# 资料卡：SQLite Documentation

- 来源链接：https://www.sqlite.org/docs.html
- 来源类型：官方文档
- 可信度：高
- 适用章节：part6/build-code-graph
- 对应问题：本地可分发的图谱存储如何实现？
- 关键结论：SQLite 适合本地可分发的图谱/索引存储与 SQL 查询教学；不是分布式图数据库替代品。
- 局限性：并发写入与超大规模图遍历能力有限；复杂图算法需应用层实现或迁移图库。
- 使用的数据：nodes/edges 表或 JSON 导入
- 可复现实验：用 SQLite 建 nodes/edges 表并查询 DiscountPolicy 的 callers

# 资料卡：Tree-sitter

- 来源链接：https://tree-sitter.github.io/tree-sitter/
- 来源类型：官方文档
- 可信度：高
- 更新时间：查阅官方站点（以页面为准）
- 适用章节：part2/source-to-ast, part2/compiler-view, part6/collect-source-structure
- 对应问题：如何增量、鲁棒地解析源码结构？
- 使用的数据：AST/CST、源码位置
- 分析方法：Parser generator + 增量重新解析
- 可视化或查询方式：语法树遍历、节点类型查询
- 关键结论：Tree-sitter 适合多语言结构抽取与编辑器场景；语义绑定通常需额外层
- 可复现实验：用 tree-sitter 解析 Java/TS 片段并打印节点类型与行列
- 可引用图表：源码到语法树流程
- 局限性：不是完整类型系统/符号解析器
- 待核查点：目标语言 grammar 成熟度

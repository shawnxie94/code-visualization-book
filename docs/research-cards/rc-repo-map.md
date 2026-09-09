# 资料卡：Repo Map（仓库地图）与混合索引

- 来源链接：https://aider.chat/docs/repomap.html
- 来源类型：工程实现文档（Aider / Agent Patterns Catalog 等，交叉见 https://www.agentpatternscatalog.org/patterns/repo-map-context/）
- 可信度：中高（Aider 是广泛使用的成熟实现；Agent Patterns Catalog 为模式聚合——是产业实践归纳，非学术定论）
- 适用章节：part5/code-graph-for-ai-agent, part5/agent-context-engineering, part6/query-interface-for-ai-agent
- 对应问题：Agent 面对远超窗口的大仓，如何在“读文件之前”建立方向感？
- 关键结论：
  - repo map = 全仓符号的紧凑拓扑（目录 + 关键符号签名），默认约 1k token，动态展开目标模块
  - 语义索引（embedding/代码搜索）回答“描述相似”，图谱索引（符号/调用/依赖）回答“结构相关”，二者互补
  - 官方实证：GitHub Copilot repository indexing（https://docs.github.com/en/copilot/concepts/context/repository-indexing）、Sourcegraph Deep Search / Precise Code Navigation
  - AGENTS.md 类上下文文件有效性的实证评估存在但结论因仓库/任务而异（arXiv 预印本）
- 局限性：实现细节（token 预算、展开策略）随产品演进；repo map 不能取代动态查询，只是第一层方向感
- 关联资产：本书 `code-graph-for-ai-agent.md` 的“仓库地图（Repo Map）：预计算的结构拓扑”节
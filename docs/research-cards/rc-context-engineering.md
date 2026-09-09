# 资料卡：Effective Context Engineering for AI Agents

- 来源链接：https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents
- 来源类型：官方工程博客（Anthropic Applied AI 团队）
- 可信度：高（一线厂商一手方法论；正式发布，非营销页）
- 适用章节：part5/agent-context-engineering, part5/code-graph-for-ai-agent, part6/query-interface-for-ai-agent
- 对应问题：给 AI Agent 的上下文应如何选择、组织并随时间管理？
- 关键结论：
  - 上下文是有限资源，存在 context rot（token 增多 → 回忆下降）与 attention budget（注意力预算）
  - 长任务靠三件套管理：compaction（压缩）、structured note-taking（结构化笔记）、sub-agent 架构
  - just-in-time context：轻量引用 + 运行时按需取（glob/grep/图谱查询），避开陈旧索引
  - 渐进披露（progressive disclosure）：Agent 逐层探索组装理解，优于一次性预载
- 局限性：基于 Claude 生态经验，具体数值（子 Agent 回传 1–2k token 等）是工程经验而非普适定律；不同模型/供应商实现有差异
- 关联资产：本书 `agent-context-engineering.md` 的"上下文是有限资源（2026 方法论）"节
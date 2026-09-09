# 资料卡：Context Rot（上下文腐烂）

- 来源链接：https://www.trychroma.com/research/context-rot
- 来源类型：厂商研究报告（Chroma）+ 学术论文（EMNLP 2025 Findings）
- 可信度：中高（Chroma 评测 18 个模型有实测；EMNLP 论文有同行评审；但存在反证，需平衡引用）
- 适用章节：part5/agent-context-engineering, part5/why-code-understanding-matters-in-ai-era
- 对应问题：把更多 token 塞进上下文窗口是否一定提升 Agent 能力？
- 关键结论：
  - Chroma：随输入 token 数增加，模型对窗口内信息的准确回忆下降（"不会用满上下文"）
  - EMNLP 2025 "Context Length Alone Hurts LLM Performance Despite Perfect Retrieval"：即使检索完美，长度本身也损害性能
  - 反证：受控实验 "Is Context Rot Real?"（Zenodo，150k tokens 合成场景）给出有界负结果，提示退化的程度与模型、任务相关
- 局限性：证据强度不一；"退化"的幅度不是常数，不宜当作铁律或耸动标题
- 对本书的用法：作为"为何要管理上下文而不依赖大窗口"的风险依据，同时明确标注存在反证，避免过度承诺
- 关联资产：`agent-context-engineering.md` 的 context rot 小节；`docs/glossary.md` 词条（若补）
# 资料卡：同源审阅盲点与独立验证（2026 研究）

- 来源链接：https://arxiv.org/html/2608.08950（Independent Patch Verification, bidirectional reconstruct-and-verify）；https://arxiv.org/html/2607.14890v1（Proof-or-Stop）
- 来源类型：学术预印本（arXiv）+ 早期工程实践（Patchward 等项目）
- 可信度：中（预印本、早期实践；非大规模已定论结论，需标注证据强度）
- 适用章节：part5/ai-code-review-evidence, part5/ai-assisted-refactoring-and-migration, part6/ai-change-verification-report
- 对应问题：AI 生成的修复能否被“与它同源的模型”可信地审阅？生命周期的“reviewed/ready-to-merge”状态是否可信？
- 关键结论：
  - 同源盲点（model synchopathy）：生成与审阅共享模型家族与假设 → 共享盲点；“AI 不应既写修复又写批准它的测试”
  - 独立修补验证：脱离生成时解释的框架（reconstruct-and-verify 等）尝试独立确认补丁是否真解决问题
  - 证据门禁（Proof-or-Stop 类）：生命周期状态是 claim，只有机械可验证证据支撑才放行
  - 实证：LLM 审阅在风格问题上接近人类，但漏架构问题（研究报告称高达 87%，24k+ 实例）
- 局限性：多为预印本与早期项目；具体数字（如 87%）未被广泛复现，本书只作“风险强调”，不作定论
- 关联资产：`ai-code-review-evidence.md` 的“同源盲点与独立验证（2026 研究视角）”节
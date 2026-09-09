# 版本与更新说明

## 2026-09-09 — 公开站点上线（E006 关闭，RC 完成）

- 启用 GitHub Pages（Actions 源）并部署成功：https://shawnxie94.github.io/code-visualization-book/
- 首次部署失败根因：Pages 未在仓库启用（deploy 步骤 404）；启用后 workflow_dispatch 立即成功
- 抽检：首页/three 样章页/glossary/changelog 全部 200；线上内容验证为最新（行号 10/15、context rot 新节已上线）
- README 更新：新站为主地址，旧 GitBook 站保留为历史
- 关闭 E006，更新 rc-completion-log 与 definition-of-done §12；可宣布出版级终稿（RC）

## 2026-07-22 — 全书快速终检（链接与状态清理）

- `docs/book-outline.md`：修正附录资产状态为已完成（术语表 61+ 词条、图示清单 FIG-01..13、资料卡 22 张、贯穿案例源码 + 5 artifacts），删除过时的“待补全/待建设/已有骨架”
- `part2/ir-ssa-cfg-dfg.md`：替换已下架的 Elsevier Muchnick 书页 → Amazon 稳定书目链接（验证 200）
- 终检结论：33 pages 构建成功；内链/图片 0 断链；106 个外链中仅剩 IEEE/OpenAI 为反爬误报（浏览器可访问）；PR-42 数字合同、6 方法行号、artifacts 实体交叉引用全部一致；27 个 mermaid 块无空块；无 TODO/工作备忘残留

## 2026-07-22 — part6 实践章叙述纵深轮（弱势章改造）

- `part6/build-code-graph.md`：新增“为什么不能只按名字匹配”失败推导（同名/接收者/自信错误三失败）、方法级 vs 文件级边对照实验（PR-42 影响面对比）、脏图比没图更危险、陈旧索引代价；中文字数 478→2114
- `part6/query-interface-for-ai-agent.md`：新增“什么算一个工具”设计挣扎（工具边界跟消费端走、五工具闭环）、失败必须与空结果结构化区分（空影响面被 Agent 当成“无影响”）；921→1384
- `part6/collect-source-structure.md`：扫描失败隔离的代价权衡、稳定 ID 身份≠行号≠路径展开、采集与建图切分（为什么留下不确定）；≈1000→1652
- `part6/build-change-impact-analysis.md`：diff 映射“坐标语言→图谱语言”、反向路径为什么选 inbound、风险分数可解释性是硬要求；763→1281
- 维持不动的章（已厚或角色适配）：build-visualization-ui（1443）、mini-code-understanding-system（1357 总览章）、ai-change-verification-report（1266 收尾章）
- 方式：全部采用失败推导 + 对照实验 + 决策性叙述，未破坏 12 项结构要素；build/断链均过

## 2026-07-22 — AI 章节补 2026 图谱索引与独立验证（检索校正轮 · 续）

- `part5/code-graph-for-ai-agent.md` 新增“仓库地图（Repo Map）：预计算的结构拓扑”节：repo map 概念与 ~1k token 紧凑投影、AGENTS.md 实证疑问（呼应 arXiv 论文）、语义索引 vs 图谱索引分工表（GitHub Copilot / Sourcegraph 官方实证）；补充 2 道练习、小结呼应
- `part5/ai-code-review-evidence.md` 新增“同源盲点与独立验证（2026 研究视角）”节：model synchopathy 概念、独立修补验证框架、Proof-or-Stop 证据门禁、LLM 审阅漏架构问题实证；补充 1 道练习、小结呼应
- 新增资料卡 2 张：`rc-repo-map.md`、`rc-independent-verification.md`；登记入索引
- 术语表新增：repo map、模型同源盲点（Model Synchopathy）
- 依据：Aider repo map、GitHub Copilot repository indexing、Sourcegraph Deep Search/Precise Code Navigation（官方实证）；arXiv 预印本（Independent Patch Verification、Proof-or-Stop、AGENTS.md 评估、LLM 审阅漏架构 87%）——新研究中预印本部分均标注证据强度

## 2026-07-22 — AI 章节补 2026 一手方法论（检索校正轮）

- 全网检索后确认知识缺口：书内“上下文工程”原只讲内容/结构层（选什么、查什么、记轨迹），缺“上下文作为有限资源的管理”方法论层
- `part5/agent-context-engineering.md` 新增“上下文是有限资源（2026 方法论）”节：context rot（含反证标注）、attention budget、compaction / structured note-taking / sub-agent、just-in-time context、progressive disclosure，并加 2 道练习、FAQ 扩 1 题、小结呼应
- 新增资料卡 2 张：`rc-context-engineering.md`（Anthropic 官方）、`rc-context-rot.md`（Chroma 评测 + EMNLP 论文 + 反证，标注证据强度）；登记入索引
- 术语表新增：context rot、compaction、structured note-taking、just-in-time context、sub-agent 架构
- 依据：Anthropic “Effective context engineering for AI agents”（官方一手）、Chroma Context Rot 研究、EMNLP 2025 Findings、“Is Context Rot Real?”（反证）

## 2026-07-22 — 数据契约与引用修正轮（内容一致性审校）

- 修正 mini-shop 行号契约：`code-graph.json` 中 `PricingService.calculateTotal` 由 (11,16) 改为真实 (10,15)；`DiscountPolicy.apply` 由 (4,10) 改为真实 (4,9)；同步修正正文 `source-to-ast.md`、`collect-source-structure.md`、`query-interface-for-ai-agent.md`、`symbols-scopes-types.md` 与 `agent-context-pack.json` 中的方法区间和调用行（apply 调用=12 行、println=13 行）；全部 6 个方法行号经校验与源码一致
- 替换 2 个失效外链：CodeQL 旧路径 → GitHub 官方 code scanning 文档（部分位置）；CodeScene 已删博文 → 官方 code health 页
- 删除重复内容：`build-change-impact-analysis.md` 重复的影响面伪代码块；`collect-source-structure.md` “采集输出最小 JSON” 与“方法节点样例”职责说明合并、行号改对、`types.lines` 补真实文件名

## 2026-07-22 — 其余偏清单章叙述加厚（未推送）

- 对 part1–part6 中仍偏清单的章节补充论述段落与工程判断
- 保持结构门槛与案例金标不变，目标是提升技术书读感

## 2026-07-22 — 叙述纵深与 PR-42 跟做主线（未推送）

- 加厚架构/AI 动机/基础设施/符号/影响面/验证报告等章的论述
- 影响面与验证报告增加可跟做剧本；sample-case 增加 90 分钟全书主线
- 目标：降低手册模板感，增强技术书可读性（仍不推送远端）


## 2026-07-22 — 本地 RC 加厚轮次（未推送）

- 终审：本地 DoD A/B/C(构建) 通过；标记本地 RC Ready；公开同步 E006 仍阻塞宣布

- 附录进入 SUMMARY（术语表/案例/勘误/资料卡）；部署说明改为本地优先；强化 mini-shop 跟做入口

- 28 章补交叉导航；关键章补 FAQ/检查清单，满足 DoD 交叉引用要求

- 无图章补 Mermaid；28 章关键要点复盘去模板化；UI 章补资料卡回指

- 资料卡字段补齐；证据章方法步骤；验证报告实体 ID 对齐；DoD §12 本地项勾选（E006 除外）

- part1 三章补失败模式/事实速查/五层产物映射；矩阵与 image-plan 状态与现网文件对齐

- part1 三章补失败模式/事实速查/五层产物映射；矩阵与 image-plan 状态与现网文件对齐


- 针对薄弱章补充 schema、工作示例、报告模板、失败模式与验收契约
- 重点加厚：符号表、CFG/DFG、图谱模型、静态/动态/变更分析、可视化 UI、AI 重构/Review、实践篇多章
- `npm run build` 通过；SUMMARY/内链检查通过；artifacts 与 PR-42 实体一致
- **未推送远端**；公开站点同步（E006）仍待你明确授权后再做

## 当前版本

- 版本名：RC 筹备稿（Beta 内容可读稿之上）
- 目录状态：已冻结
- 目标：出版级终稿（见 `definition-of-done.md`）

## 近期变更

### 2026-07-22（RC 推进）

- 确认公开站点 `code-visualization.shawnxie.top` 仍为旧版内容，RC 发布同步仍开放
- 语言机械审校与审校记录补充

### 2026-07-22（RC 推进 earlier）


- 建立并扩充术语表、图示清单、资料卡（18）、审校清单、changelog
- 28 章统一补齐练习，并增强权威参考资料与资料卡回指
- 样章与关键章继续加厚；大纲保持冻结

### 2026-07-22


- 完成定义升级为出版级终稿（RC）
- 增加内容丰富度与权威引用硬门槛
- 冻结大纲，补充 part5/part6 职责边界
- 建立术语表、图示清单骨架
- 建立贯穿案例 `mini-shop` 与 artifacts
- 28 章达到 Beta 可读结构

## 反馈与勘误

如发现事实错误、断链、术语不一致或案例数字冲突，请通过 README 中的联系方式反馈，并尽量提供：

1. 章节路径
2. 问题描述
3. 建议来源（官方文档/论文链接更佳）

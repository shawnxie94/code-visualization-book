# 章节质量矩阵

本文档用于管理 28 个章节的内容优化状态。它不是目录本身，而是后续 AI 写作、资料补强、样章打磨和一致性审校的工作表。

## 状态说明

状态以 [`definition-of-done.md`](definition-of-done.md) 的出版级终稿（RC）为准。

| 状态 | 含义 |
| --- | --- |
| 草稿 | 有提纲或初稿，结构/例子不足 |
| 可读 | 主线清楚，可内部阅读；**未达出版定稿** |
| 样章定稿 | 三类样章达到出版模板标准 |
| 定稿 | 达到 DoD 单章定稿标准（结构、深度、练习、图示、引用） |
| 需返工 | 审校发现问题，必须修改 |

> 说明：截至本地 RC 轮次，28 章均为「定稿」或「样章定稿」。后续仅勘误与公开同步（E006），不再以“从可读升定稿”为主线。

## 当前推进进度

| 事项 | 状态 | 说明 |
| --- | --- | --- |
| 全书定位 / DoD(RC) | 已完成 | 出版级终稿标准 |
| 大纲冻结 | 已完成 | 主线不再大改 |
| 术语表 / 图示清单 / 资料卡 | 已完成 | glossary + image-plan + 18 cards |
| 关键精确图 | 已完成 | 13 张 SVG（FIG-01..13 关键集） |
| 28 章定稿结构 | 已完成 | 练习+引用+例子+边界 |
| 语言审校 | 已完成一轮 | 机械+全书抽检润色；记录见 language-review-notes |
| 公开站点同步 | 进行中 | 需提交推送后验证 |
| 出版级终稿（RC） | 进行中 | 取决于公开站点同步验证 |

## 章节矩阵

| 章 | 章节 | 类型 | 核心问题 | 必讲概念 | 示例要求 | 图示要求 | 参考资料方向 | 当前状态 | 下一步动作 | 需要你提供的信息 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 01 | 为什么需要代码可视化 | 目标与边界 | 为什么复杂代码库不能只靠搜索、文档和经验理解 | 复杂性、不可见关系、变更影响、AI 放大验证压力 | 一个跨模块改动难以判断影响的例子 | 可选：复杂系统关系示意图 | 大型代码库、AI Coding 风险、软件维护成本 | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 02 | 代码可视化到底可视化什么 | 目标与边界 | 代码可视化的对象到底是什么 | 结构、关系、行为、演进、组织 | 一个功能从入口到代码、测试、Owner 的完整事实链 | Mermaid：五类事实汇聚图 | 软件目录、服务目录、代码图谱 | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 03 | 从图形展示到软件理解系统 | 目标与边界 | 为什么代码可视化应升级为系统能力 | 数据采集、程序分析、图谱建模、查询、工程集成 | 一个 PR 分析从数据采集到报告输出的链路 | 已有 Mermaid，可后续转架构图 | OpenTelemetry、Backstage、CodeQL | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 04 | 编译器视角下的代码结构 | 原理 | 为什么理解代码需要借用编译器视角 | Lexer、Parser、语义分析、IR | 一段代码如何经过编译器阶段 | Mermaid：编译器流水线 | 编译器教材、LLVM、Tree-sitter | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 05 | 从字符到 AST | 原理 / 样章 | 为什么字符串搜索不够，AST 能提供什么结构事实 | Token、Lexer、Parser、AST、节点遍历、源码位置、局限 | 已接入 mini-shop 的 calculateTotal 例子 | 已有 Mermaid AST 图，可后续转教学插图 | ANTLR、Tree-sitter、JavaParser、TS Compiler API | 样章定稿 | 结构/丰富度/练习/引用/关键图已达标；持续勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 06 | 符号表、作用域与类型关系 | 原理 | 为什么有 AST 还不够，还需要名字、类型和引用关系 | 符号、定义、引用、作用域、类型、继承、实现 | 同名变量、接口实现、方法重写例子 | Mermaid：定义引用关系图 | IDE 跳转、LSP、TypeScript/Java 类型系统 | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 07 | IR、SSA、CFG 与 DFG | 原理 | 如何表达程序路径和数据传播 | IR、SSA、CFG、DFG、PDG、可达性、数据依赖 | 已有 price 函数、SQL 注入数据流例子 | 已有 CFG/DFG Mermaid | LLVM、CodeQL Data Flow、SSA 资料 | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 08 | 静态分析：不运行代码时能知道什么 | 程序分析 | 静态分析能提取哪些事实，边界在哪里 | 依赖、调用、继承、复杂度、架构规则、误报漏报 | 一个静态分析发现跨层调用的例子 | Mermaid：静态分析输入输出 | CodeQL、Semgrep、Sonar、ArchUnit | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 09 | 动态分析：运行起来之后才能知道什么 | 程序分析 | 运行时证据补足了哪些静态分析盲区 | Log、Trace、Span、Profile、Coverage、Runtime Call Graph | 一次请求 Trace 到方法调用的例子 | Mermaid：Trace 到代码图谱 | OpenTelemetry、APM、Coverage 工具 | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 10 | 变更分析：系统是如何演进的 | 程序分析 | Git/PR 历史如何成为代码理解事实 | Diff、Commit、PR、变更频率、变更耦合、缺陷历史、Owner | 一个热点文件和变更耦合例子 | Mermaid：变更数据进入图谱 | Git、CodeScene、GitHub PR | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 11 | 代码图谱：节点、边与属性 | 图谱 / 样章延展 | 代码事实如何被建模成统一图谱 | 节点、边、属性、多源融合、查询、存储选择 | 方法、测试、PR、Trace、Owner 的统一图例 | 已有 Mermaid，可转总览图 | Neo4j、CodeQL、Backstage、OpenTelemetry | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 12 | 可视化表达：从图到证据 | 图谱 | 如何从“好看的图”变成可验证证据 | 视图选择、过滤、聚合、下钻、证据链、误导风险 | 一个影响面报告从图到证据的例子 | Mermaid：证据链结构 | 可视化原则、PR 报告、Codecov | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 13 | 代码库理解与上下文构建 | 工程场景 | 新人或 Agent 如何快速建立代码库上下文 | 入口识别、模块地图、调用路径、相关文件、上下文包 | 一个 Issue 如何定位相关文件 | Mermaid：上下文包构建流程 | GitHub Copilot codebase exploration、VS Code workspace context | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 14 | 变更影响分析与验证 | 工程场景 / 样章 | 如何从 Diff 推导影响范围和验证策略 | 变更实体、反向调用、资源影响、相关测试、风险分级、报告 | 一个 PR 的影响面报告例子 | 已有 Mermaid，可补 PR mockup | Test Impact Analysis、Codecov、Launchable、CodeScene | 样章定稿 | 结构/丰富度/练习/引用/关键图已达标；持续勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 15 | 架构理解与遗留系统改造 | 工程场景 | 遗留系统改造前如何恢复系统边界 | 模块边界、服务依赖、资源依赖、热点、重构对比、架构守护 | 一个遗留模块拆分前后的结构对比 | Mermaid：改造前后依赖图 | Backstage、架构治理、变更热点分析 | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 16 | AI 写代码之后，为什么代码理解更重要 | AI 应用 | 为什么 AI 提升生成速度后，验证压力反而更突出 | Agent、上下文缺失、误改边界、测试遗漏、Review 变化 | 一个 AI 修改跨边界代码的风险例子 | 可选：生成速度与验证压力对照图 | SWE-bench、Copilot Agent、AI coding 研究 | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 17 | Agent 上下文工程 | AI 应用 / 样章 | 如何给 Agent 提供正确、充分、可验证的上下文 | 意图边界、语义检索、图谱查询、上下文包、查询轨迹 | 一个任务如何生成上下文包 | 已有 Mermaid，可补上下文包结构图 | GitHub Copilot、VS Code workspace context、RAG for code | 样章定稿 | 结构/丰富度/练习/引用/关键图已达标；持续勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 18 | 代码图谱如何服务 AI Agent | AI 应用 | 图谱如何成为 Agent 的上下文压缩和边界约束层 | 图谱查询、RAG 互补、MCP 风格接口、修改范围、审计 | find_callers / impact_analysis 查询例子 | Mermaid：Agent 查图流程 | MCP、代码 RAG、代码图谱工具 | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 19 | AI 生成代码的 Review 证据层 | AI 应用 | Reviewer 需要哪些证据判断 AI 改动是否可信 | 改动范围、影响面、测试覆盖、安全数据流、架构约束、运行时风险 | 一个 AI PR Review 检查清单 | Mermaid：证据层结构 | CodeQL、Codecov、CI 报告、PR review | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 20 | AI 辅助重构与系统迁移 | AI 应用 | AI 如何参与重构，同时避免破坏系统边界 | 小步改动、行为保持、架构约束、迁移验证、对比报告 | 一个模块迁移小步计划 | Mermaid：重构验证闭环 | 软件迁移、架构约束、测试策略 | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 21 | 从代码可视化到软件理解基础设施 | AI 应用 / 总结 | 如何把一次性图表升级为持续更新的软件事实库 | 知识图谱、Agent 查图、多源融合、持续更新、治理 AI 轨迹 | 一个组织级软件理解平台最小形态 | Mermaid：基础设施总览 | Backstage、OpenTelemetry、代码搜索、AI Agent 平台 | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 22 | 构建一个最小代码理解系统 | 实践 | 实践项目要跑通什么闭环 | collector、graph、analysis、ui、agent-api、report | 一个小型示例仓库 | 已有 Mermaid，可转系统架构图 | JavaParser、Tree-sitter、MCP、Mermaid | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 23 | 采集源码结构 | 实践 | 如何从源码中抽取基础结构事实 | 文件、类、方法、继承、候选调用、测试、稳定 ID | 解析一个小项目输出 JSON | Mermaid：采集流程 | JavaParser、Tree-sitter、AST parser | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 24 | 构建代码图谱 | 实践 | 如何把采集结果变成可查询图谱 | 节点表、边表、属性、方向、增量更新、校验 | nodes/edges JSON 或 SQLite schema | Mermaid：节点边模型 | Neo4j/SQLite/JSON graph | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 25 | 构建变更影响分析 | 实践 | 如何基于 Diff 和图谱输出影响面 | Diff、变更方法、反向调用、入口、测试、风险 | 一个 diff 输入到报告输出 | Mermaid：影响分析算法流程 | Git diff、覆盖率、调用图 | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 26 | 构建可视化界面 | 实践 | 如何让图谱和影响面被人理解 | 图谱视图、影响面视图、热点视图、节点详情、过滤、报告优先 | 一个最小 UI 页面结构 | 技术书籍风 mockup | D3/Cytoscape/React Flow/Mermaid | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 27 | 给 AI Agent 的查询接口 | 实践 | Agent 应该如何查询代码图谱 | find_symbol、find_callers、find_callees、impact_analysis、related_tests、architecture_rules | API 请求和响应 JSON | Mermaid：Agent API 调用链 | MCP、tool calling、JSON schema | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |
| 28 | AI 修改后的验证报告 | 实践 | AI 修改后应该交付什么验证证据 | 改动摘要、影响面、测试建议、风险节点、架构安全检查、查询轨迹 | 一份完整验证报告样例 | 技术书籍风 PR 报告 mockup | PR comment、CI report、Codecov、CodeQL | 定稿 | 结构/练习/引用/案例一致已达标；后续仅勘误 | 使用 mini-shop / PR-42 贯穿，无需真实案例 |

## 样章优先级

第一批优先打磨：

1. `part2/source-to-ast.md`
2. `part4/change-impact-verification.md`
3. `part5/agent-context-engineering.md`

第二批跟进章节：

1. `part3/code-graph-model.md`
2. `part5/code-graph-for-ai-agent.md`
3. `part6/query-interface-for-ai-agent.md`
4. `part6/ai-change-verification-report.md`

原因：

1. 第一批决定三类章节的写作标准。
2. 第二批决定全书从图谱走向 Agent 和实践系统的主线是否顺畅。

## 已确认的数据

以下数据已经确认，后续样章打磨和章节扩写按这些取舍推进：

1. 目标用户更偏 AI Coding 工具建设者。
2. 书籍风格采用技术科普、工程方法论、工具建设手册三者结合。
3. 实践项目语言和技术栈允许多种选择，但以适合讲解和复现为优先。
4. 不加入真实案例，使用模拟案例说明。
5. 发布形式以 GitHub Pages 为主。
6. 图片风格采用技术书籍风。

## 下一步动作

本地内容门槛已完成一轮定稿升格与加厚。剩余动作：

1. 持续勘误（病句、链接、数字一致性）。
2. 在作者明确授权后推送，并验证公开站点同步本仓库 RC 文稿（E006）。
3. 通过 E006 后，按 `definition-of-done.md` §12 宣布出版级终稿。
4. Release+（PDF/纸书/更多语言案例）不纳入当前 RC。

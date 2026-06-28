# AI 时代代码可视化与代码理解调研简报

调研日期：2026-06-28

本文档用于回答两个问题：

1. 现在最新的代码可视化应用场景有哪些？
2. 在 AI 编程和 Agent 时代，代码理解是否变得更重要？

## 1. 核心结论

代码可视化正在从“辅助人阅读代码的图形界面”升级为“软件系统的结构化理解层”。过去它主要服务于阅读、架构图、调用图、质量报表和性能分析；现在它还承担三类新职责：

1. 为 AI 编程助手提供高质量上下文，降低只靠全文检索和文件遍历造成的误解。
2. 为人类 Review、治理和验收 AI 生成代码提供可验证证据。
3. 把静态代码、运行时链路、测试覆盖、变更历史、架构所有权和业务语义连接起来，形成面向研发决策的代码知识地图。

所以，AI 时代代码理解不是变弱了，而是更重要了。原因很直接：AI 让代码产出速度上升，但也把瓶颈从“写代码”推向“理解、选择上下文、验证影响面、审查质量和治理风险”。

## 2. 研究问题

本轮调研围绕以下问题展开：

1. 代码可视化现在主要服务哪些工程场景？
2. AI 编程工具和 Agent 对代码上下文提出了什么新要求？
3. 代码图、知识图谱、RAG、MCP 等方向是否正在形成新趋势？
4. 代码理解能力对 AI 生成代码的质量、Review 和治理有什么影响？
5. 这些变化应该如何反映到《Code Visualization》的新目录和资料收集框架中？

## 3. 最新应用场景

### 3.1 代码库探索与新人上手

传统代码阅读工具强调符号跳转、引用查找、调用图和类图。新的变化是：代码库探索开始和 AI Chat、Agent、语义搜索结合。

GitHub Copilot 文档已经把“探索代码库”作为独立教程，覆盖理解文件、符号、仓库上下文等任务；VS Code Copilot 文档也明确提到 Agent 会结合语义搜索、文本搜索、grep 等工具收集工作区上下文。

应用形态：

- 给新成员解释模块职责、入口和调用链。
- 对陌生仓库生成架构地图和关键路径。
- 在大型仓库中定位与需求相关的文件、符号、测试和配置。
- 将“问代码库”变成日常开发入口。

可采集数据：

- 文件树、模块边界、符号定义、引用关系、调用关系、路由、配置、README、提交历史。

代表资料：

- [Using GitHub Copilot to explore a codebase](https://docs.github.com/en/copilot/tutorials/explore-a-codebase)
- [How Copilot understands your workspace](https://code.visualstudio.com/docs/agents/reference/workspace-context)

### 3.2 AI Agent 的代码上下文层

2025 到 2026 年，代码可视化和代码图的一个新方向是服务 AI Agent，而不只是服务人。GitHub Copilot cloud agent 可以研究仓库、计划修改、创建 PR；SWE-bench 这类基准也把真实仓库和真实 Issue 作为评价对象。

这说明“仓库级代码理解”已经变成 AI 编程能力的核心评价维度。单文件补全不够，Agent 需要理解跨文件依赖、测试、配置、框架约定和历史上下文。

应用形态：

- Repo Agent 执行 Issue、生成 PR、跑测试。
- MCP 工具把代码结构暴露给 Claude Code、Cursor、Codex、Copilot 等 Agent。
- Code Graph / Knowledge Graph 作为 Agent 的长期结构化记忆。
- 通过影响面查询约束 AI 修改范围，减少误改。

可采集数据：

- AST、符号、调用图、导入图、HTTP 路由、测试映射、Git 历史、依赖包、文档、架构约定。

代表资料：

- [About GitHub Copilot cloud agent](https://docs.github.com/copilot/concepts/agents/cloud-agent/about-cloud-agent)
- [SWE-bench GitHub repository](https://github.com/swe-bench/SWE-bench)
- [SWE-bench Verified](https://www.swebench.com/verified.html)
- [Retrieval-Augmented Code Generation: A Survey with Focus on Repository-Level Approaches](https://arxiv.org/html/2510.04905v1)
- [Codebase-Memory: Tree-Sitter-Based Knowledge Graphs for LLM Code Exploration via MCP](https://arxiv.org/html/2603.27277v1)

### 3.3 变更影响分析与 PR 风险评估

代码可视化越来越靠近 PR 工作流：一个变更影响哪些模块、调用链、测试、覆盖率、服务依赖、用户路径，应该在 Review 前就被可视化展示。

GitHub 在 2026 年推出 PR 中直接展示代码覆盖率的 public preview，说明“把质量和风险信号嵌入 Review 界面”是明确趋势。CodeScene、Codecov、Azure Test Impact Analysis、Launchable 也都在围绕变更、覆盖、测试选择和风险做工程化产品。

应用形态：

- PR 影响文件、影响调用链、影响服务、影响测试。
- 基于覆盖率变化提示 Review 风险。
- 基于历史变更耦合识别隐性依赖。
- 基于测试影响分析选择最小相关测试集。

可采集数据：

- Git diff、调用图、依赖图、测试覆盖、历史失败、测试耗时、生产流量、变更频率、代码健康度。

代表资料：

- [Code coverage in pull requests is now in public preview](https://github.blog/changelog/2026-05-26-code-coverage-in-pull-requests-is-now-in-public-preview/)
- [Azure Pipelines Test Impact Analysis](https://learn.microsoft.com/en-us/azure/devops/pipelines/test/test-impact-analysis?view=azure-devops)
- [Launchable Predictive Test Selection](https://help.launchableinc.com/features/predictive-test-selection/)
- [CodeScene Change Coupling](https://codescene.io/docs/guides/technical/change-coupling.html)
- [CodeScene Hotspots](https://codescene.io/docs/guides/technical/hotspots.html)

### 3.4 架构治理、服务目录与所有权地图

现代系统不只是单个代码库，更多是多服务、多仓库、多团队、多 API 的系统。代码可视化在这里变成“软件目录 + 依赖地图 + 所有权地图”。

Backstage 的 Catalog Graph 用于展示实体之间的关系，例如 ownership、grouping、API relationships；Atlassian Compass 支持 component dependency map，用于查看上下游依赖和组件关系。

应用形态：

- 服务目录和组件关系图。
- 服务、API、资源、团队、Owner 的关系可视化。
- 架构边界、依赖方向、循环依赖治理。
- 事故响应时定位服务 owner 和上下游影响。

可采集数据：

- 服务目录、组件元数据、API 描述、owner、依赖声明、运行时调用、部署环境、告警和 SLO。

代表资料：

- [Backstage Catalog Graph plugin](https://github.com/backstage/backstage/blob/master/plugins/catalog-graph/README.md)
- [Backstage Creating the Catalog Graph](https://backstage.io/docs/features/software-catalog/creating-the-catalog-graph/)
- [Atlassian Compass Software Catalog](https://www.atlassian.com/software/compass/software-catalog)
- [Atlassian Compass component dependencies](https://support.atlassian.com/compass/docs/add-component-dependencies/)

### 3.5 安全分析与数据流可视化

安全场景中的代码可视化更强调“数据如何流动”。CodeQL 文档明确把数据流分析用于计算变量值如何传播、定位不安全使用、危险参数和敏感数据泄漏。

在 AI 生成代码增加后，安全分析的重要性继续上升，因为 AI 可能生成看似合理但缺少边界校验、权限校验或安全上下文的代码。

应用形态：

- 污点传播路径可视化。
- Source 到 Sink 的漏洞链路解释。
- 依赖包、供应链和可达漏洞分析。
- AI 生成代码的安全 Guardrail。

可采集数据：

- AST、类型、控制流、数据流、污点源、危险 Sink、依赖包、权限模型、框架规则。

代表资料：

- [CodeQL About data flow analysis](https://codeql.github.com/docs/writing-codeql-queries/about-data-flow-analysis/)
- [About CodeQL](https://codeql.github.com/docs/codeql-overview/about-codeql/)
- [Semgrep Code](https://semgrep.dev/products/semgrep-code)

### 3.6 性能分析、链路追踪与运行时可视化

动态代码可视化已经不局限于火焰图。OpenTelemetry 把 Trace 建模为由 Span 组成的 DAG，用于观察请求在分布式系统中的传播路径。对微服务、云原生和复杂前后端系统来说，这类运行时图谱是代码理解的重要补充。

应用形态：

- 火焰图定位 CPU 热点。
- Trace Waterfall 展示请求链路和耗时。
- 服务依赖图显示运行时调用关系。
- 将运行时异常映射回代码、Owner 和 PR。

可采集数据：

- Trace、Span、日志、指标、Profile、异常堆栈、资源消耗、服务元数据。

代表资料：

- [OpenTelemetry traces](https://opentelemetry.io/docs/concepts/signals/traces/)
- [OpenTelemetry overview](https://opentelemetry.io/docs/specs/otel/overview/)
- [OpenTelemetry observability primer](https://opentelemetry.io/docs/concepts/observability-primer/)

### 3.7 技术债、热点和演进可视化

静态分析只看“代码长什么样”，演进分析还看“代码怎么变”。CodeScene 这类工具把变更频率、代码健康度、变更耦合、团队协作等信号结合起来，用来识别更值得优先治理的热点。

应用形态：

- 高频变更 + 低健康度文件的热点图。
- 经常一起变更的模块耦合图。
- 团队知识分布和代码所有权。
- AI 时代的代码健康基线和质量门禁。

可采集数据：

- Git history、复杂度、代码气味、作者、团队、变更耦合、缺陷记录、PR Review 结果。

代表资料：

- [CodeScene Code Health](https://codescene.com/product/code-health)
- [CodeScene Scale AI Coding Safely](https://codescene.com/use-cases/scale-ai-coding-safely)

### 3.8 旧系统现代化与自动重构

AI 让自动重构、迁移和遗留系统改造变得更可行，但前提是必须先理解旧系统边界、依赖、隐性调用和业务规则。代码可视化在这里更像“迁移前地图”和“迁移后验收依据”。

应用形态：

- 从遗留代码中恢复模块边界和业务能力地图。
- 识别重构顺序、风险热点和测试空洞。
- 迁移前后依赖、接口、行为路径对比。
- 让 AI Agent 在限定影响面内做小步重构。

可采集数据：

- 模块依赖、数据库访问、接口调用、配置、批处理任务、运行时流量、测试覆盖、缺陷历史。

## 4. AI 时代为什么代码理解更重要

### 4.1 代码生成变快，但验证变贵

Stack Overflow 2025 调查显示，开发者对 AI 工具准确性的信任低于不信任：信任约 33%，不信任约 46%，高度信任只有约 3%。DORA 2025 也观察到类似的“信任悖论”：很多人认为 AI 有生产力价值，但并不完全信任 AI 输出。

这意味着 AI 时代的核心矛盾不是“能不能生成代码”，而是“能不能理解、验证、治理这些代码”。

代表资料：

- [Stack Overflow Developer Survey 2025 - AI](https://survey.stackoverflow.co/2025/ai)
- [DORA 2025 State of AI-assisted Software Development](https://dora.dev/dora-report-2025/)
- [Google: How are developers using AI? Inside our 2025 DORA report](https://blog.google/innovation-and-ai/technology/developers-tools/dora-report-2025/)

### 4.2 AI Agent 需要仓库级上下文，而不是单文件上下文

SWE-bench、SWE-bench Verified 和后续仓库级代码生成研究都说明：真实软件工程任务往往要求模型理解整个代码库和 Issue 背景，再跨多个文件生成补丁并通过测试。RAG survey 也总结了仓库级代码生成的关键挑战，包括跨文件依赖、全局语义一致性和跨文件推理。

因此，代码理解从“人脑看懂代码”升级为“系统能为 AI 提供正确上下文”。

### 4.3 代码图成为 AI 上下文压缩和检索结构

近期大量工具和论文围绕 Tree-sitter、AST、调用图、知识图谱、MCP 展开。Codebase-Memory 这类研究明确指出，LLM Agent 只靠反复读文件和 grep 会消耗大量 token，而且缺少结构理解；它提出把代码库构造成持久知识图谱，通过 MCP 给 Agent 查询。

这对本书非常关键：代码可视化不再只是最终 UI，也可以是 AI 写代码之前的“结构化上下文层”。

### 4.4 人类 Review 的重点从风格变成影响面和系统一致性

当 AI 能快速生成大量代码，Review 的主要工作会转向：

- 是否改错位置。
- 是否破坏模块边界。
- 是否遗漏测试。
- 是否引入安全或性能风险。
- 是否和历史架构决策冲突。
- 是否扩大技术债。

这些问题无法只靠 LLM 自评解决，需要调用图、依赖图、覆盖率、Trace、数据流、变更历史和架构规则共同支撑。

### 4.5 AI 放大已有工程体系，而不是替代工程体系

DORA 2025 的核心结论是 AI 是组织能力的放大器，会放大强组织的优势，也会放大弱组织的问题。映射到代码可视化领域，就是：如果团队本来没有清晰边界、测试、架构规则、代码健康指标和上下文管理，AI 只会更快地产生需要治理的代码。

## 5. 对本书内容的建议调整

### 5.1 导论需要升级

原导论可以从“代码可视化帮助理解和分析代码”升级为：

> 代码可视化是将代码、运行时、变更历史和工程语义转化为可观察、可查询、可验证的结构化表达，用于支持人和 AI 共同完成理解、修改、验证和治理软件系统。

### 5.2 增加“AI 时代代码理解”主线

建议新增一章或一个独立小节：

- AI 编程为什么需要仓库级代码理解。
- 代码图如何服务 RAG 和 Agent。
- AI 生成代码为什么更需要影响面分析和质量门禁。
- 代码可视化如何成为人类 Review 的证据层。

### 5.3 资料收集框架需要加入 AI 上下文层

在原数据收集框架中新增一类：

| 数据层 | 收集对象 | 用途 |
| --- | --- | --- |
| AI 上下文层 | 向量索引、符号图、调用图、代码知识图谱、MCP 工具、Agent 轨迹、Prompt、Review 反馈 | 支撑 Agent 检索、生成、修改、验证和人类审查 |

### 5.4 实践部分可增加一个“小型代码图 + Agent 查询”实验

建议实践项目从单纯的 CallGraph 可视化扩展为：

1. 用 Tree-sitter 或 JavaParser 提取符号和调用关系。
2. 存成轻量图结构，例如 JSON、SQLite 或图数据库。
3. 提供基本查询：谁调用它、它调用谁、改它影响谁、相关测试有哪些。
4. 前端做可视化。
5. 通过 Prompt 或 MCP 风格接口让 AI 查询这张图。

这样可以让这本书和 AI 时代的软件工程实践自然连接起来。

## 6. 章节候选标题

可以考虑新增或改名以下章节：

1. AI 时代，为什么代码理解更重要
2. 从代码可视化到代码上下文工程
3. 代码图：连接人类理解与 AI Agent 的中间层
4. 仓库级代码理解：从 AST、调用图到知识图谱
5. AI 生成代码的影响面分析与 Review 证据
6. 软件系统的静态图谱与动态图谱

## 7. 后续调研建议

下一轮可以分三条线继续深入：

1. 工具线：系统比较 Sourcegraph、CodeScene、Backstage、Compass、CodeQL、Semgrep、Codecov、Launchable、Graph-Code/CodeGraph 类 MCP 工具。
2. 论文线：系统整理仓库级代码生成、Code RAG、代码知识图谱、SWE-bench 系列、程序分析与 LLM 结合论文。
3. 实践线：选一个小型 Java 项目，构建 AST/CallGraph/影响面图谱，验证它能否提升 AI 改代码的准确性。

## 8. 初步判断

这本书继续写是有价值的，而且主题比之前更重要。过去“代码可视化”容易被理解为开发辅助工具或图形化展示；现在它可以重新定位为：

> AI 时代的软件理解基础设施。

这个定位更宽，也更贴近当下趋势。它可以同时覆盖程序分析、可视化、人机协作、AI Agent、研发效能、测试治理、安全治理和架构治理。


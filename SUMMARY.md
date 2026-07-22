# 目录

* [前言](README.md)
* 第一篇：代码可视化的目标与边界
    * [为什么需要代码可视化](part1/why-code-visualization.md)
    * [代码可视化到底可视化什么](part1/what-to-visualize.md)
    * [从图形展示到软件理解系统](part1/software-understanding-system.md)
* 第二篇：源码结构化原理
    * [编译器视角下的代码结构](part2/compiler-view.md)
    * [从字符到 AST](part2/source-to-ast.md)
    * [符号表、作用域与类型关系](part2/symbols-scopes-types.md)
    * [IR、SSA、CFG 与 DFG](part2/ir-ssa-cfg-dfg.md)
* 第三篇：程序分析与代码图谱
    * [静态分析：不运行代码时能知道什么](part3/static-analysis.md)
    * [动态分析：运行起来之后才能知道什么](part3/dynamic-analysis.md)
    * [变更分析：系统是如何演进的](part3/change-analysis.md)
    * [代码图谱：节点、边与属性](part3/code-graph-model.md)
    * [可视化表达：从图到证据](part3/visualization-as-evidence.md)
* 第四篇：三个核心工程场景
    * [代码库理解与上下文构建](part4/codebase-understanding.md)
    * [变更影响分析与验证](part4/change-impact-verification.md)
    * [架构理解与遗留系统改造](part4/architecture-and-legacy-modernization.md)
* 第五篇：AI 时代的新应用
    * [为什么 AI 时代更需要代码理解](part5/why-code-understanding-matters-in-ai-era.md)
    * [Agent 上下文工程](part5/agent-context-engineering.md)
    * [代码图谱如何服务 AI Agent](part5/code-graph-for-ai-agent.md)
    * [AI 生成代码的 Review 证据层](part5/ai-code-review-evidence.md)
    * [AI 辅助重构与系统迁移](part5/ai-assisted-refactoring-and-migration.md)
    * [从代码可视化到软件理解基础设施](part5/software-understanding-infrastructure.md)
* 第六篇：实践项目
    * [构建一个最小代码理解系统](part6/mini-code-understanding-system.md)
    * [采集源码结构](part6/collect-source-structure.md)
    * [构建代码图谱](part6/build-code-graph.md)
    * [构建变更影响分析](part6/build-change-impact-analysis.md)
    * [构建可视化界面](part6/build-visualization-ui.md)
    * [给 AI Agent 的查询接口](part6/query-interface-for-ai-agent.md)
    * [AI 修改后的验证报告](part6/ai-change-verification-report.md)

* 附录
    * [术语表](docs/glossary.md)
    * [贯穿案例：mini-shop](docs/sample-case/README.md)
    * [版本与勘误](docs/changelog.md)
    * [资料卡索引](docs/research-cards/README.md)

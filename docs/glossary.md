# 术语表

本术语表服务《Code Visualization》出版级终稿。正文关键术语应与本表一致。

## 使用约定

1. 同一概念全书只用一个主术语；别名在“近义/勿混”中标注。
2. 中英文并存时，正文主用中文，必要时括注英文或缩写。
3. 缩写首次出现写全称，其后可用缩写。
4. 若章节使用近义说法，应能映射回本表主术语。

## 核心术语

| 术语 | 英文/缩写 | 定义 | 近义/勿混 | 主要章节 |
| --- | --- | --- | --- | --- |
| 软件理解系统 | Software Understanding System | 采集、分析、建模、查询代码及相关事实，并服务开发/Review/Agent 决策的系统能力 | 不等于单张架构图或一次性可视化 | part1, part5, part6 |
| 代码可视化 | Code Visualization | 以图、路径、矩阵、报告、查询结果表达代码事实的方法与界面层 | 不等于装饰性画图 | part1, part3 |
| 代码图谱 | Code Graph | 以节点、边、属性组织代码与相关事实的可查询模型 | 不等于单一调用图视图 | part3, part6 |
| 结构事实 | Structural Fact | 从源码结构得到的实体与关系（文件/类/方法/候选调用等） | 不等于运行时事实 | part2, part3 |
| 行为事实 | Behavioral Fact | 从执行中得到的事实（Trace、Coverage、Profile 等） | 不等于静态可能路径 | part3 |
| 演进事实 | Evolutionary Fact | 从 Diff/Commit/PR/缺陷历史得到的变化事实 | 不等于影响面报告本身 | part3, part4 |
| 组织事实 | Organizational Fact | Owner、团队、服务目录、架构规则等协作与治理信息 | 不等于代码结构 | part1, part4 |
| AST | Abstract Syntax Tree | 抽象语法树，表达语法结构而非文本外观 | 不等于 CST 的全部细节，也不等于符号表 | part2 |
| Token | Token | 词法分析得到的最小语法单元 | 不等于 AST 节点 | part2 |
| 词法分析 | Lexical Analysis / Lexer | 将字符流切分为 Token 的过程 | 不等于语法分析 | part2 |
| 语法分析 | Syntax Analysis / Parser | 将 Token 组织成树结构的过程 | 不等于语义分析 | part2 |
| 符号表 | Symbol Table | 名称定义、作用域与绑定信息的集合 | 不等于 AST 本身 | part2 |
| 作用域 | Scope | 名称可见与绑定生效的程序区域 | 不等于文件边界 alone | part2 |
| 定义-引用 | Definition-Reference | 符号定义与其使用点之间的解析关系 | 不等于文本同名匹配 | part2 |
| IR | Intermediate Representation | 便于分析/优化的中间表示 | 不等于源码 AST | part2 |
| SSA | Static Single Assignment | 每个变量赋值唯一命名的 IR 形式 | 不等于业务语义正确性 | part2 |
| CFG | Control Flow Graph | 控制流图，描述可能执行路径 | 不等于调用图 | part2, part3 |
| DFG | Data Flow Graph | 数据流图，描述值的产生与使用传播 | 不等于 CFG | part2, part3 |
| 调用图 | Call Graph | 方法/函数间调用关系图 | 需标注静态/动态与置信度 | part3, part4 |
| 静态分析 | Static Analysis | 不执行程序，从源码/字节码/IR 推导事实 | 不等于测试或证明无缺陷 | part3 |
| 动态分析 | Dynamic Analysis | 基于运行/测试时信号获得事实 | 未见不等于不可能 | part3 |
| 变更分析 | Change Analysis | 将版本历史映射为可查询演进事实 | 不等于影响面分析全过程 | part3 |
| 影响面分析 | Change Impact Analysis | 从变更实体推导影响路径、相关测试与风险 | 不等于只看 diff 行 | part4, part6 |
| 变更实体 | Changed Entity | Diff 映射到的类/方法等稳定代码实体 | 不等于文件路径 alone | part4, part6 |
| 证据层 | Evidence Layer | 可审计、可回跳源码/数据的验证信息集合 | 不等于模型自然语言解释 | part3, part5 |
| 置信度 | Confidence | 对某条边/结论确定性的标注（如 high/medium/low） | 不等于业务优先级 | part3, part6 |
| Agent 上下文 | Agent Context | 为完成任务提供给 AI Agent 的结构化相关信息与约束 | 不等于更大上下文窗口 | part5 |
| 上下文包 | Context Pack | 可序列化任务上下文（符号、调用方、测试、规则、轨迹等） | 不等于 prompt 原文 | part5, part6 |
| 查询轨迹 | Query Trace | 工具调用与结果摘要的可审计记录 | 不等于最终补丁 | part5, part6 |
| 验证报告 | Verification Report | 修改后的影响面、测试、规则、风险与轨迹汇总 | 不等于单独 CI 绿勾 | part5, part6 |
| 架构规则 | Architecture Rule | 模块依赖/分层等可检查约束 | 不等于文档中的期望架构 alone | part4, part5 |
| 事实架构 | As-is Architecture | 由代码与关系数据反映的真实结构 | 不等于宣称架构 | part4 |
| 宣称架构 | To-be / Documented Architecture | 文档或目标中的架构意图 | 不等于代码现状 | part4 |
| mini-shop | — | 全书贯穿的模拟订单计价示例仓库 | 非真实业务系统 | 全书 |
| PR-42 | — | mini-shop 中 VIP 折扣从 0.9 调整为 0.85 的模拟变更 | 教学案例，非真实 PR | part4-part6 |

## 缩写速查

| 缩写 | 全称 |
| --- | --- |
| AST | Abstract Syntax Tree |
| CST | Concrete Syntax Tree |
| IR | Intermediate Representation |
| SSA | Static Single Assignment |
| CFG | Control Flow Graph |
| DFG | Data Flow Graph |
| PDG | Program Dependence Graph |
| LSP | Language Server Protocol |
| MCP | Model Context Protocol |
| OTel | OpenTelemetry |
| PR | Pull Request |
| CI | Continuous Integration |
| ADR | Architecture Decision Record |
| CPG | Code Property Graph |
| context rot | 上下文腐烂：输入 token 增多导致模型对窗口内信息的准确回忆下降的现象；本书作为需管理的风险引用（证据存在反证） |
| compaction | 压缩：长任务中将接近上限的历史总结进新窗口，保留决策与未决问题 |
| structured note-taking | 结构化笔记 / agentic memory：Agent 把状态写入外部笔记（如 NOTES.md）按需重新读入，实现跨窗口记忆 |
| just-in-time context | 即时上下文：只带轻量引用（路径/ID/查询），运行时用 glob/grep/图谱查询按需加载真实内容 |
| sub-agent 架构 | 主 Agent 协调、子 Agent 各自探索并只回传浓缩摘要的上下文管理方式 |

## 模块与产物命名（实践一致）

| 名称 | 含义 |
| --- | --- |
| collector | 源码扫描与 AST 抽取模块 |
| graph | 节点/边存储与查询模块 |
| analysis | 影响面与测试推荐模块 |
| api | Agent 查询接口 |
| report | 验证报告生成 |
| artifacts | `examples/mini-shop/artifacts/` 标准样例产物 |

## 维护规则

1. 新增术语先改本表，再进正文。
2. 审校时抽查正文是否漂回近义混用。
3. 与 `docs/research-cards/` 中的标准术语保持一致。

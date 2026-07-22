# 图示清单与计划

本清单服务出版级终稿（RC）。目标：关键图可出版、风格统一、术语一致。

> 状态：骨架已建立。Mermaid 可用作草稿；关键技术图需升级到 SVG/PNG。

## 图示分层

| 层级 | 用途 | 形式 |
| --- | --- | --- |
| L1 流程/结构草图 | 教学推进、关系概览 | Mermaid（可保留） |
| L2 精确技术图 | AST/CFG/DFG/图谱/报告结构 | SVG/PNG |
| L3 概念插画 | 篇首、封面、PR mockup | AI 插画（不替代 L2） |

## 全书关键图（优先完成）

| ID | 主题 | 所在章节 | 类型 | 当前状态 | RC 目标 |
| --- | --- | --- | --- | --- | --- |
| FIG-01 | 全书主线链路 | README / part1 | L2 | SVG：`imgs/fig-01-book-pipeline.svg` | 已完成 |
| FIG-02 | 软件理解系统五层 | part1/software-understanding-system | L2 | SVG 已生成：imgs/fig-02-understanding-system.svg | 架构精图 |
| FIG-03 | 源码→Token→AST | part2/source-to-ast | L2 | SVG 已生成：imgs/fig-03-source-to-ast.svg | 精确教学图 |
| FIG-04 | 定义-引用/符号关系 | part2/symbols-scopes-types | L2 | SVG：`imgs/fig-04-def-ref.svg` | 已完成 |
| FIG-05 | CFG/DFG 示例 | part2/ir-ssa-cfg-dfg | L2 | SVG：`imgs/fig-05-cfg-dfg.svg` | 已完成 |
| FIG-06 | 静态/动态/变更融合 | part3/* | L2 | SVG：`imgs/fig-06-fact-fusion.svg` | 已完成 |
| FIG-07 | mini-shop 代码图谱 | part3/code-graph-model | L2 | SVG 已生成：imgs/fig-07-mini-shop-graph.svg | 图谱总览图 |
| FIG-08 | 从图到证据链 | part3/visualization-as-evidence | L2 | SVG：`imgs/fig-08-evidence-pack.svg` | 已完成 |
| FIG-09 | PR-42 影响路径 | part4/change-impact-verification | L2 | SVG 已生成：imgs/fig-09-pr42-impact.svg | PR 报告图 |
| FIG-10 | Agent 上下文包结构 | part5/agent-context-engineering | L2 | SVG 已生成：imgs/fig-10-agent-context.svg | 信息结构图 |
| FIG-11 | Review 证据层 | part5/ai-code-review-evidence | L2 | SVG：`imgs/fig-11-review-evidence.svg` | 已完成 |
| FIG-12 | 最小系统模块图 | part6/mini-code-understanding-system | L2 | SVG 已生成：imgs/fig-12-mini-system.svg | 架构精图 |
| FIG-13 | 验证报告信息结构 | part6/ai-change-verification-report | L2 | SVG：`imgs/fig-13-verification-report.svg` | 已完成 |

## 风格规范

1. 技术书籍风：克制、清晰、信息优先。
2. 同一实体命名与术语表一致（如 `DiscountPolicy.apply`）。
3. 颜色只用于编码信息，不用于装饰炫光。
4. 每张图在正文中有解读，不只是贴图。
5. AI 插画不得承担精确技术表达。

## 生产流程

1. 正文稳定后锁定图中标签与实体 ID。
2. Mermaid 定稿语义。
3. 关键图转 SVG/PNG。
4. 更新本清单状态与文件路径。
5. 构建抽检渲染结果。

## 输出目录建议

```text
imgs/
  fig-03-source-to-ast.svg
  fig-07-mini-shop-graph.svg
  fig-09-pr42-impact.svg
  ...
```

## 已生成精确图

| 文件 | 对应 |
| --- | --- |
| `imgs/fig-01-book-pipeline.svg` | FIG-01 |
| `imgs/fig-02-understanding-system.svg` | FIG-02 |
| `imgs/fig-03-source-to-ast.svg` | FIG-03 |
| `imgs/fig-04-def-ref.svg` | FIG-04 |
| `imgs/fig-05-cfg-dfg.svg` | FIG-05 |
| `imgs/fig-06-fact-fusion.svg` | FIG-06 |
| `imgs/fig-07-mini-shop-graph.svg` | FIG-07 |
| `imgs/fig-08-evidence-pack.svg` | FIG-08 |
| `imgs/fig-09-pr42-impact.svg` | FIG-09 |
| `imgs/fig-10-agent-context.svg` | FIG-10 |
| `imgs/fig-11-review-evidence.svg` | FIG-11 |
| `imgs/fig-12-mini-system.svg` | FIG-12 |
| `imgs/fig-13-verification-report.svg` | FIG-13 |

说明：13 张关键精确技术图已齐并可在正文嵌入。可继续由设计师做视觉精修，但不阻塞 RC 图示门槛。

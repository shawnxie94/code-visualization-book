# 构建可视化界面

## 本章要解决的问题

如何用最小界面把图谱和影响面变成可操作证据？

## 读者读完应获得什么

1. 能设计三个核心视图：图谱、影响面、热点/详情。
2. 能定义节点下钻与源码回跳交互。
3. 能用静态 HTML/Mermaid 先交付可用版本。

## 本章不讲什么

- 不追求复杂力导向大屏。
- 不先做设计系统。

---

## 设计原则

1. 默认展示任务子图
2. 每个节点可回源码
3. 边显示来源/置信度
4. 先摘要后细节
5. 报告与图共用数据

## 三个视图

### 1. 图谱视图

- 模块过滤：order/pricing/payment
- 显示 contains 与 calls
- 搜索符号

### 2. 影响面视图

输入 PR/Diff 结果后展示：

- 变更实体高亮
- 影响路径列表
- 相关测试表
- 风险标签

### 3. 详情视图

点击 `DiscountPolicy.apply` 显示：

```text
id / file / lines
callers
callees
tests
recent changes (如有)
```

## 最小实现路径

阶段 A：

- 读取 `code-graph.json`
- Mermaid 渲染调用链
- Markdown 渲染影响报告

阶段 B：

- 增加交互过滤与搜索
- 节点点击展示 JSON 详情

阶段 C（可选）：

- React Flow / Cytoscape 增强

## 页面信息架构

```text
[仓库][PR选择][符号搜索]
-------------------------
| 子图 | 路径/测试/风险 |
| | 详情/源码片段 |
-------------------------
| 查询轨迹 / 报告导出 |
```

## 数据到视图的绑定契约

UI 不应各自拼装字符串，而应消费统一产物：

| 视图 | 数据来源 | 关键字段 |
| --- | --- | --- |
| 图谱视图 | `code-graph.json` | `nodes[]`, `edges[]` |
| 影响面视图 | `impact-report-pr-42.json` | `changed_entities`, `impact_paths`, `related_tests`, `risk` |
| 详情/报告 | `verification-report-pr-42.md` + graph | 路径、测试、规则、轨迹 |

### 图谱视图伪代码

```text
load graph
filter nodes by module in {order, pricing, payment}
keep edges type in {contains, calls}
render task subgraph
on node click -> open detail(node.id)
```

### 影响面视图伪代码

```text
load impact report for PR-42
highlight changed_entities on graph
list impact_paths as ordered chains
table related_tests with status hint
badge risk.level + risk.reasons
```

### 详情面板最小 JSON

点击 `method:DiscountPolicy#apply` 时展示：

```json
{
  "id": "method:DiscountPolicy#apply",
  "file": "src/main/java/com/minishop/pricing/DiscountPolicy.java",
  "lines": [3, 10],
  "callers": ["method:PricingService#calculateTotal"],
  "callees": [],
  "tests": [
    "test:PricingServiceTest#shouldApplyVipDiscount",
    "test:OrderServiceTest#shouldCreateVipOrderWithDiscount"
  ],
  "edge_meta": {"source": "static", "confidence": "high"}
}
```

## 工作示例：用静态页交付 PR-42

最小可交付不必上框架：

1. `index.html` 左栏渲染 Mermaid 调用链
2. 右栏 `fetch`/`embed` `impact-report-pr-42.json` 生成路径列表
3. 底部链到 `verification-report-pr-42.md`
4. 节点 `id` 显示为可复制文本，便于 Agent/人对照

验收时只问一件事：Reviewer 能否在 60 秒内回答“改了谁、影响谁、测什么、风险为何是 medium”。

## 反模式

| 反模式 | 问题 | 纠正 |
| --- | --- | --- |
| 默认全仓力导向大图 | 认知过载，找不到变更 | 默认任务子图 |
| 图与报告各算各的 | 数字不一致，失去证据 | 同源 JSON |
| 只有漂亮图无可回跳路径 | 不能进入 Review | 节点回源码/文件行 |
| 隐藏低置信边 | 假安全感 | 显示 source/confidence |


## 验收

- 能看到 `apply -> calculateTotal -> createOrder`
- 能打开 `PR-42` 报告
- 能从节点定位到文件路径
- 不出现无过滤全图爆炸

## 局限

- 最小 UI 不支持复杂协作与权限。
- 大图交互需要额外性能优化。
- 可视化不能补齐采集阶段缺失的事实。

## 小结

1. UI 服务证据，不服务装饰。
2. 三视图足够支撑理解与验证。
3. Mermaid+报告可先交付价值。
4. 交互增强不应破坏数据同源。

## 可访问性与可解释性

技术图若无法解释，就不是证据。UI 文案建议强制出现：

- “为什么看到这些节点”（查询条件）
- “边的来源与置信度”
- “点击可回源码”
- “证据更新时间”

缺这四项，界面再漂亮也难以进入严肃 Review 流程。

## 工作示例：PR 阅读 60 秒路径

1. 打开影响面视图，看变更实体高亮
2. 展开第一条路径到入口
3. 看相关测试表是否红/需更新
4. 点开规则检查
5. 需要时再下钻图谱邻域

如果用户必须先拖拽全图 5 分钟才能找到变更点，UI 就算失败。

## 常见问题：可视化界面

### 为什么三视图就够？

覆盖理解、验证、详情，足够闭环。

### 必须用 WebGL 大图吗？

否。先证据后炫技。

### 如何验证 UI 成功？

用户能在 60 秒完成 PR 关键路径阅读。

## 本章检查清单

1. 默认子图
2. 节点回源码
3. 来源置信度
4. 报告同源数据

## 关键要点复盘

围绕「构建可视化界面」，读者离开本章前应能做到：

1. 用自己的话解释核心概念与边界
2. 在 `mini-shop` / `PR-42` 上指出对应实体、路径或产物
3. 说明它如何服务人或 AI 的具体决策
4. 列出至少两个局限或失败模式
5. 知道下一章将把它连接到哪一层能力

若任一做不到，请先复习本章例子与练习，再继续向后读。

## 最小 HTML 骨架（示意）

```html
<section id="graph">
  <!-- Mermaid: apply -> calculateTotal -> createOrder -->
</section>
<section id="impact">
  <h2>PR-42 Impact</h2>
  <ul id="paths"></ul>
  <table id="tests"></table>
  <div id="risk"></div>
</section>
<section id="detail">
  <pre id="node-json"></pre>
  <a id="source-link" href="#">Open source path</a>
</section>
```

交互最低要求：

1. 点击路径高亮对应节点
2. 点击节点填充 `node-json`
3. 风险 `medium` 用非绿色标签（避免“全绿即安全”误导）


## 练习

1. 画一个三栏信息架构草图：子图 / 路径与测试 / 详情。
2. 说明为何默认不能渲染全仓大图。
3. 为节点详情列出必须字段。

## 延伸阅读与参考资料

- [Mermaid docs](https://mermaid.js.org/)
- [Cytoscape.js](https://js.cytoscape.org/)
- [React Flow](https://reactflow.dev/)
- [NNG cognitive load](https://www.nngroup.com/articles/minimize-cognitive-load/)
- [OpenTelemetry UI 直觉](https://opentelemetry.io/docs/concepts/signals/traces/)
- 样例数据：[`code-graph.json`](../examples/mini-shop/artifacts/code-graph.json)、[`verification-report-pr-42.md`](../examples/mini-shop/artifacts/verification-report-pr-42.md)

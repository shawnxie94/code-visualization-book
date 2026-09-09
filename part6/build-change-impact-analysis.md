# 构建变更影响分析

## 本章要解决的问题

如何基于 Diff 和图谱自动输出影响面报告？

## 读者读完应获得什么

1. 能实现 Diff -> 实体 -> 反向路径 -> 测试 -> 风险 的算法骨架。
2. 能对 `PR-42` 产出与样例一致的关键字段。
3. 能解释不确定结果如何表示。

## 本章不讲什么

- 不追求研究级指针分析精度。

---

## 算法骨架

```text
1. parse_diff(diff) -> changed_lines_by_file
2. map_lines_to_entities(graph, changed_lines) -> changed_entities
3. for e in changed_entities:
 reverse_dfs(callers) within depth N
4. collect entry points / external resources
5. collect related tests
6. score risk
7. emit report json/md
```

## Diff 映射：把“改了哪几行”升级为“改了哪个实体”

对 `pr-42.diff`，变更行（`return amount * 0.9` → `0.85`）落在 `DiscountPolicy.apply` 方法区间，故：

```text
changed_entities = [method:DiscountPolicy#apply]
```

这一步看起来平凡（就是一个区间查找），但它决定了影响面分析的全部下游。如果把粒度停在文件或行：

```text
文件级：改动了 DiscountPolicy.java → 影响面无限扩散到整个 pricing 模块
行级：改动第 6 行 → 无法告诉下游这是方法体、字段还是注释
实体级：改动 DiscountPolicy#apply → 精确锚定可反向遍历的起点
```

所以“diff 映射”的实质是：**把 diff 的坐标语言（文件+行）翻译成图谱的语言（实体 ID）**。一旦实体定错（比如映射到类而不是方法），反向路径和测试推荐就会集体跑偏——这就是“优先怀疑映射粗细，而不是先调风险权重”的原因。

## 反向路径：为什么选“谁调用我”而不是“我调用谁”

影响面关注的是**改动向上传播**：`apply` 改了，谁依赖它的结果？因此遍历方向是 `inbound（caller）`，而不是 `outbound（callee）`。

```text
apply
 ^ calculateTotal
 ^ createOrder
 ^ create
```

伪代码：

```python
def find_callers(graph, method_id, depth=5):
 result = []
 stack = [(method_id, [])]
 while stack:
 cur, path = stack.pop()
 if len(path) > depth: continue
 for edge in inbound_calls(graph, cur):
 nxt = edge.from
 result.append(path + [nxt])
 stack.append((nxt, path + [nxt]))
 return result
```

深度要设上限（默认 5），因为真实调用图里反向遍历会指数膨胀。裁剪不是“偷懒”，而是**把路径总量压回可审规模**：报告写 `paths_shown=3, paths_total=42`，而不是伪称“完整枚举”。

## 测试关联

```text
tests_edge.to in affected_methods
或 test 方法体候选调用命中 affected_methods
```

测试只有命中受影响方法才算相关；否则“测了但没测到变更路径”，绿测反而是绿灯假象。

## 风险规则：分数必须能解释，否则就是新的黑盒

```text
if touches_money_path: +2
if has_failing_or_stale_tests: +2
if crosses_modules: +1
if breaks_architecture_rule: +3
```

`PR-42` 应为 medium，并给出 reasons（触碰金额路径 +2、测试断言过期 +2、跨模块到 payment +1）。

风险规则的可解释性是硬要求，不是加分项：如果只给一个数字，Reviewer 无法判断“该不该信”；给 reasons 后，争论就从“你凭什么叫 medium”变成“这条 reason 是否成立”。规则还**必须是可配置的**——不同团队的金额路径、测试策略、架构红线都不一样。一个写死在代码里的风险模型，会比没有模型更有害（它给人虚假的确定性）。

## 输出契约

与 [`impact-report-pr-42.json`](../examples/mini-shop/artifacts/impact-report-pr-42.json) 对齐：

```json
{
 "changed_entities": [],
 "impact_paths": [],
 "related_tests": [],
 "risk": {"level": "medium", "reasons": []},
 "recommended_actions": []
}
```

## 验收

- 输入 `artifacts/pr-42.diff`
- 识别 `DiscountPolicy.apply`
- 路径覆盖 `OrderController.create`
- 测试包含 pricing 与 order
- 生成 JSON + Markdown

## 影响面实现流程

```mermaid
flowchart TD
 Diff[pr-42.diff] --> Ent[变更实体]
 Graph[code-graph.json] --> Ent
 Ent --> Rev[反向调用路径]
 Rev --> Tests[相关测试]
 Tests --> Risk[风险分级]
 Risk --> Report[impact-report JSON/MD]
```

输出必须与 `examples/mini-shop/artifacts/impact-report-pr-42.json` 字段兼容。


影响面实现章的任务，是把 part4 的方法落成可运行算法：diff → 实体 → 反向路径 → 测试 → 风险 → 报告。重点不在炫技式路径枚举，而在输出字段与金标 artifacts 对齐，让 UI、Agent、验证报告都能消费同一 JSON。

当你的结果与 `impact-report-pr-42.json` 不一致时，优先怀疑映射粗细（文件级 vs 方法级）和边方向（caller/callee 反了），而不是先调风险分数权重。


## 局限

- 静态反向调用无法覆盖所有动态入口。
- 风险规则需要按团队校准。
- 深度过大时路径爆炸，需要裁剪与汇总。

## 小结

1. 影响面是可实现的确定性流水线：diff → 实体 → 反向路径 → 测试 → 风险 → 报告。
2. 关键在实体映射与调用反向遍历：实体定错，下游全部跑偏。
3. 风险分数必须可解释：reasons + 可配置，否则就是新黑盒。
4. 输出要直接服务 PR 与 Agent 验证：字段与金标 `impact-report-pr-42.json` 对齐。

## 端到端伪代码（可实现）

```python
def analyze(pr_diff, graph):
 changed = map_diff_to_entities(pr_diff, graph)
 impacted = set(changed)
 for e in changed:
 impacted |= reverse_callers(graph, e, depth=5)
 tests = related_tests(graph, impacted)
 risk = score_risk(changed, impacted, tests, graph.rules)
 return Report(changed, paths(impacted), tests, risk)
```

用 `mini-shop` 的 `pr-42.diff` 做金标测试：
`changed={DiscountPolicy.apply}` 且路径包含 `OrderController.create`。

## 工作示例：深度裁剪

反向调用 depth=5 在小仓足够；大仓需要：

1. 按模块裁剪
2. 优先测试入口/API 入口
3. 汇总为“前 N 条关键路径 + 其余计数”

报告应写：`paths_shown=3, paths_total=42`，避免伪称“完整枚举”。

## 常见问题：实现影响面

### depth 设多少？

小仓 5 足够；大仓需裁剪与汇总。

### 如何测算法正确？

用 PR-42 金标：实体、路径、测试、风险字段。

### 风险分数如何避免黑盒？

每条 reason 必须可解释、可配置。

## 本章检查清单

1. diff 映射
2. 反向路径
3. 测试关联
4. 风险解释
5. 报告契约

## 关键要点复盘

围绕「构建变更影响分析」，读者离开本章前应能做到：

1. 实现 diff→实体→路径→测试→风险
2. 对照 artifacts 金标字段
3. 输出 recommended_actions
4. 处理低置信边扩展
5. 衔接到可视化 UI

若任一做不到，请先复习本章例子与练习，再继续向后读。

## 练习

1. 实现（伪代码）diff 行到方法实体的映射。
2. 对 PR-42 跑一遍反向路径，核对是否到达 `OrderController.create`。
3. 给风险规则打分并解释 reasons。

## 本章导航

- 上一章：[构建代码图谱](build-code-graph.md)
- 下一章：[构建可视化界面](build-visualization-ui.md)
- 相关章：[代码图谱：节点、边与属性](../part3/code-graph-model.md)；[Agent 上下文工程](../part5/agent-context-engineering.md)

## 延伸阅读与参考资料

- [git diff](https://git-scm.com/docs/git-diff)。资料卡：`../docs/research-cards/rc-git-diff.md`
- [Test Impact Analysis](https://learn.microsoft.com/en-us/azure/devops/pipelines/test/test-impact-analysis)。资料卡：`../docs/research-cards/rc-test-impact.md`
- [GitHub checks](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/collaborating-on-repositories-with-code-quality-features/about-status-checks)
- [CodeQL](https://codeql.github.com/docs/)
- [SARIF](https://docs.oasis-open.org/sarif/sarif/v2.1.0/sarif-v2.1.0.html)。资料卡：`../docs/research-cards/rc-sarif.md`
- 样例：[`impact-report-pr-42.json`](../examples/mini-shop/artifacts/impact-report-pr-42.json)

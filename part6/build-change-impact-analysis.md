# 构建变更影响分析

变更影响分析把 Git Diff 和代码图谱连接起来。目标是从一次修改推导可能受影响的入口、调用方和测试，并输出可验证报告。

在最小系统中，影响面分析不追求完全精确，而是要跑通基本链路：定位变更实体、反向追踪调用方、关联测试、生成报告。

## 输入 Git Diff

输入可以来自：

- 当前工作区 Diff。
- 两个 Git commit 之间的 Diff。
- 一个 PR 的变更文件。
- 用户手动指定的变更文件和行号。

最小实现可以先支持 Git 命令输出：

```text
git diff --name-only
git diff --unified=0
```

系统需要拿到变更文件、变更行号和变更类型，例如新增、修改、删除。

## 定位变更实体

有了文件和行号后，需要映射到图谱实体。

规则可以从简单开始：

- 如果变更行落在某个方法范围内，标记该方法变更。
- 如果变更行落在类字段或注解上，标记该类变更。
- 如果变更文件是测试文件，标记测试变更。
- 如果变更文件是配置，标记配置变更。

对于删除代码，行号映射会更复杂。第一版可以通过变更前图谱定位，后续再处理精细删除场景。

## 反向追踪调用方

定位变更方法后，沿 `calls` 边反向追踪调用方。

基本算法：

```text
queue = changed_methods
visited = set()

while queue not empty:
  current = queue.pop()
  callers = reverse_edges(current, type="calls")
  for caller in callers:
    if caller not visited:
      record_path(caller -> current)
      queue.add(caller)
```

追踪时需要设置限制：

- 最大深度。
- 只追踪项目内方法。
- 到入口方法停止。
- 到模块边界停止。
- 忽略低置信度边，或单独标记。

否则影响面可能无限扩散，报告不可读。

## 识别入口

入口节点可以来自前面的源码采集：

- Controller 方法。
- 路由方法。
- 消息消费者。
- 定时任务。
- 命令行任务。
- 测试入口。

如果反向追踪到入口，说明变更可能影响一个外部可触发路径。报告中应该优先展示从变更方法到入口的路径。

## 关联测试

相关测试可以通过多种方式找到：

- `covers` 边：Coverage 数据直接表明测试覆盖目标方法。
- 调用边：测试方法调用了目标方法或上游入口。
- 命名约定：`OrderServiceTest` 对应 `OrderService`。
- 同目录或同模块。
- 历史共同变更。

最小系统可以先用命名约定和调用关系，后续再接入 Coverage。

测试推荐最好给出依据。例如：

```text
OrderServiceTest.cancel_shouldReleaseStock
原因：测试方法调用了 OrderService.cancel
```

## 风险提示

影响面报告可以加入基础风险提示：

- 影响入口数量多。
- 调用路径深。
- 变更方法无相关测试。
- 变更文件复杂度高。
- 变更文件近期频繁修改。
- 调用边置信度低。

第一版风险规则可以很简单，但要明确可解释。

## 输出影响面报告

报告建议包含：

```text
变更摘要
  - 变更文件
  - 变更实体

影响路径
  - 从入口到变更方法的关键路径

相关测试
  - 推荐测试
  - 推荐依据

风险提示
  - 未覆盖路径
  - 低置信度边
  - 架构边界变化

不确定性
  - 未解析调用
  - 外部依赖
```

报告可以输出 Markdown 给人读，也可以输出 JSON 给 Agent 或 CI 使用。

## 示例输出

```markdown
## 变更实体

- `OrderService.cancel(Long)`

## 影响路径

- `OrderController.cancel` -> `OrderService.cancel`
- `OrderJob.retryCancel` -> `OrderService.cancel`

## 建议测试

- `OrderServiceTest.cancel_shouldReleaseStock`
- `OrderControllerTest.cancel_shouldReturnSuccess`

## 风险

- 影响订单核心路径
- 库存回滚调用缺少集成测试覆盖
```

## 小结

变更影响分析是连接代码图谱和工程验证的关键能力。它从 Diff 出发，把变更映射到代码实体，再沿图谱追踪入口和测试。

下一章会把这些结果展示出来，构建可视化界面。

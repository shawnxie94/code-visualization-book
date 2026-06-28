# 给 AI Agent 的查询接口

Agent 查询接口让 AI 能以结构化方式访问代码图谱，而不是盲目读取整个仓库。它是实践项目连接 AI 时代应用的关键一步。

这一章设计一组最小工具接口。它们可以先实现为 CLI 命令、HTTP API 或本地函数，后续再封装为 MCP 风格工具。

## 接口设计原则

接口应该遵循几个原则：

- 输入明确。
- 输出结构化。
- 返回证据来源。
- 标注不确定性。
- 不直接让 Agent 修改图谱事实。
- 查询轨迹可记录。

Agent 工具不是给 AI 一个无限权限的黑盒，而是给它一组可审计的工程查询能力。

## find_symbol

用途：根据名称查找类、方法、文件或配置项。

输入示例：

```json
{
  "query": "OrderService.cancel"
}
```

输出应包含：

- 节点 ID。
- 类型。
- 全限定名称。
- 文件路径。
- 起止行号。
- 匹配置信度。

如果存在多个候选，应全部返回，并让 Agent 或用户继续选择。

## find_callers

用途：查询某个方法或接口的调用方。

输入示例：

```json
{
  "symbol_id": "method:com.example.OrderService#cancel(java.lang.Long)",
  "max_depth": 3
}
```

输出应包含调用方列表和路径。对于低置信度调用边，要明确标注。

这个接口用于修改前影响面判断。Agent 不应该在不知道调用方的情况下修改公共方法。

## find_callees

用途：查询某个方法调用了哪些下游方法、服务或资源。

它帮助 Agent 理解实现依赖。例如修改订单取消逻辑时，Agent 需要知道它是否调用库存回滚、支付退款、消息通知等下游逻辑。

输出可以按关系类型分组：

- 方法调用。
- 数据库读写。
- 外部服务。
- 消息发送。
- 配置读取。

## impact_analysis

用途：输入变更文件、行号或方法，返回可能影响范围。

输入示例：

```json
{
  "changed_files": [
    {
      "path": "src/main/java/com/example/OrderService.java",
      "lines": [32, 48]
    }
  ]
}
```

输出应包含：

- 变更实体。
- 影响路径。
- 受影响入口。
- 相关测试。
- 风险提示。
- 不确定性。

这是最重要的 Agent 工具之一。它可以在 Agent 修改前用于规划，也可以在修改后用于生成报告。

## related_tests

用途：根据代码实体返回相关测试。

数据来源可以包括：

- Coverage。
- 调用关系。
- 命名约定。
- 同模块目录。
- 历史共同变更。

输出应说明推荐依据。例如：

```json
{
  "test": "OrderServiceTest.cancel_shouldReleaseStock",
  "reason": "covers target method",
  "evidence": "coverage"
}
```

## architecture_rules

用途：返回目标模块或文件相关的架构约束。

示例输出：

```json
{
  "module": "order",
  "rules": [
    "order module must not depend on payment infrastructure directly",
    "database writes must go through repository layer"
  ]
}
```

这个接口用于防止 Agent 写出能跑但破坏系统边界的代码。

## 查询轨迹

所有 Agent 查询都应该记录：

- 查询时间。
- 工具名称。
- 输入参数。
- 返回结果摘要。
- 是否用于最终报告。

查询轨迹可以进入 AI 修改后的验证报告。Reviewer 可以看到 Agent 是否查过调用方、测试和架构约束。

## 错误处理

接口必须清楚表达失败原因：

- 符号不存在。
- 候选过多。
- 图谱过期。
- 调用关系不确定。
- 测试覆盖数据缺失。

不要让 Agent 把空结果误解为“没有影响”。空结果可能意味着数据缺失，而不是风险不存在。

## 小结

Agent 查询接口把代码图谱变成 AI 可用的工程工具。它让 Agent 能查询符号、调用、影响面、测试和架构规则，也让 Reviewer 能审计 Agent 是否做了必要检查。

下一章会把这些查询结果汇总为 AI 修改后的验证报告。

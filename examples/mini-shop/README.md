# mini-shop

全书贯穿使用的模拟代码库。业务刻意简化，重点是跨模块调用、测试关联和变更影响。

更完整的任务表与案例契约见：[`docs/sample-case/README.md`](../../docs/sample-case/README.md)。

## 模块

- `order`：订单入口与编排
- `pricing`：计价与折扣
- `payment`：支付客户端

## 关键路径

```text
OrderController.create
  -> OrderService.createOrder
    -> PricingService.calculateTotal
      -> DiscountPolicy.apply
    -> PaymentClient.charge
    -> OrderRepository.save
```

## PR-42 金标数字

| 项 | 值 |
| --- | --- |
| 变更实体 | `method:DiscountPolicy#apply` |
| VIP 折扣 | `0.9` → `0.85` |
| 示例订单 | qty=2, unitPrice=100 |
| 断言 | `180.0` → `170.0` |
| 风险 | medium（金额语义 + 测试需更新） |

源码中的 `DiscountPolicy` 当前保持 **改前** 状态（`0.9` / 测试 `180.0`），以便读者自己应用 `artifacts/pr-42.diff` 并对照报告。

## 产物（artifacts）

| 文件 | 用途 |
| --- | --- |
| [`artifacts/code-graph.json`](artifacts/code-graph.json) | 代码图谱 |
| [`artifacts/pr-42.diff`](artifacts/pr-42.diff) | 变更输入 |
| [`artifacts/impact-report-pr-42.json`](artifacts/impact-report-pr-42.json) | 影响面报告 |
| [`artifacts/agent-context-pack.json`](artifacts/agent-context-pack.json) | Agent 上下文包 |
| [`artifacts/verification-report-pr-42.md`](artifacts/verification-report-pr-42.md) | 验证报告 |

## 本地用途

本仓库不是可运行生产系统，只用于书中讲解与跟做：

1. AST / 符号 / 调用关系提取
2. 变更影响面分析
3. Agent 上下文构建
4. 验证报告样例

## 建议跟做顺序

1. 阅读源码关键路径与测试断言
2. 打开 `pr-42.diff`，映射到变更实体
3. 对照 `impact-report-pr-42.json` 与路径/测试
4. 阅读 `agent-context-pack.json` 与 `verification-report-pr-42.md`
5. 回到第六篇各章，按模块实现最小闭环

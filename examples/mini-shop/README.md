# mini-shop

全书贯穿使用的模拟代码库。业务刻意简化，重点是跨模块调用、测试关联和变更影响。

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

## 本地用途

本仓库不是可运行生产系统，只用于书中讲解：

1. AST / 符号 / 调用关系提取
2. 变更影响面分析
3. Agent 上下文构建
4. 验证报告样例

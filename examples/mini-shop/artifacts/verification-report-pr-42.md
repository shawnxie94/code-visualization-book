# 验证报告：PR-42 VIP 折扣调整

## 改动摘要
- 目标：VIP 折扣 `0.9` -> `0.85`
- 变更实体：`DiscountPolicy.apply`
- 文件：`src/main/java/com/minishop/pricing/DiscountPolicy.java`

## 影响路径
1. `DiscountPolicy.apply`
2. `PricingService.calculateTotal`
3. `OrderService.createOrder`
4. `OrderController.create`

支付金额依赖折后总价，因此 `PaymentClient.charge` 的入参会间接受影响。

## 相关测试
| 测试 | 原断言 | 更新后断言 | 状态 |
| --- | --- | --- | --- |
| `PricingServiceTest.shouldApplyVipDiscount` | 180.0 | 170.0 | 需更新 |
| `OrderServiceTest.shouldCreateVipOrderWithDiscount` | 180.0 | 170.0 | 需更新 |

## 风险分级
- 等级：中
- 原因：金额计算变更、入口调用链受影响、测试断言过期

## 架构约束检查
- `pricing` 未新增对 `payment` 的依赖：通过

## 查询轨迹
1. `find_symbol(DiscountPolicy.apply)`
2. `find_callers(method:DiscountPolicy#apply)`
3. `related_tests(method:DiscountPolicy#apply)`
4. `architecture_rules(module=pricing)`

## 建议
1. 同步更新测试期望值
2. 运行 pricing 与 order 测试
3. 在 PR 中附上本影响面与查询轨迹，供 Reviewer 审计

# 采集源码结构

源码结构采集是实践项目的第一步。目标是把文件、类、方法和调用表达式提取成结构化数据，为后续代码图谱提供节点和边。

这一章不要求一次性解决所有语义问题。我们先完成一个可工作的最小采集器：扫描源码文件，解析 AST，提取类、方法、继承关系和候选调用关系。

## 输入和输出

输入：

- 一个源码仓库路径。
- 源码目录，例如 `src/main/java`。
- 测试目录，例如 `src/test/java`。
- 可选的依赖和构建配置。

输出：

- 文件节点。
- 类或接口节点。
- 方法节点。
- 测试节点。
- 包含关系。
- 继承和实现关系。
- 候选调用关系。

这些输出可以先保存为 JSON，后续再导入图谱存储。

## 扫描源码文件

第一步是找到目标语言文件。对 Java 项目来说，可以扫描 `.java` 文件，并区分生产代码和测试代码。

需要记录：

- 文件路径。
- 文件所属源码集，例如 main 或 test。
- 包名。
- 最后修改时间或 Git 信息。

文件节点是所有后续实体的上层容器。每个类、方法都应该能追溯回源文件和行号。

## 解析 AST

每个源码文件需要解析为 AST。解析阶段要尽量保留源码位置，包括节点的起止行号和列号。

如果解析失败，系统不应该直接中断全量任务。更好的做法是记录失败文件、失败原因，并继续分析其他文件。真实仓库里经常存在生成代码、不完整代码或版本不兼容语法。

AST 解析结果可以用于提取：

- 类声明。
- 接口声明。
- 枚举声明。
- 字段声明。
- 方法声明。
- 构造函数。
- 注解。
- 方法调用表达式。

## 提取类和接口

类和接口节点至少需要记录：

```text
id
type: class | interface | enum
name
qualified_name
package
file_path
start_line
end_line
modifiers
annotations
```

`qualified_name` 很重要，因为简单名称可能重复。比如不同包下都可能有 `UserService`。

类节点还应该建立到文件节点的 `contains` 边。

## 提取方法

方法节点至少记录：

```text
id
type: method
name
signature
qualified_name
owner_class
return_type
parameters
file_path
start_line
end_line
annotations
```

方法签名要包含参数类型，否则重载方法无法区分。

方法节点要建立两类边：

- 文件或类 `contains` 方法。
- 方法可能调用其他方法。

## 提取继承和实现关系

从类声明中可以提取：

- `extends`：继承父类。
- `implements`：实现接口。

如果目标类型在当前项目中能找到定义，就建立指向目标类或接口的边。如果找不到，也可以先记录外部类型引用，后续再决定是否纳入依赖图。

继承和实现关系对调用图、影响面分析和 Agent 上下文都很重要。修改接口方法时，系统需要知道有哪些实现类受影响。

## 提取候选调用关系

方法体里的调用表达式可以被提取为候选调用。

需要记录：

```text
caller_method
callee_name
receiver_expression
argument_count
source_position
raw_text
```

如果暂时没有完整类型解析，可以先保存候选调用。后续通过类型信息、导入关系、方法签名和框架规则逐步解析到目标方法。

不要一开始就追求完美调用图。最小系统可以先支持同类方法调用、简单成员调用和明确静态调用，再逐步扩展。

## 处理测试代码

测试代码不是附属物，而是验证图谱的重要节点。

对测试文件，可以提取：

- 测试类。
- 测试方法。
- 测试注解。
- 测试调用的业务方法。
- 测试文件与生产文件的命名关系。

即使没有 Coverage，测试命名和目录也可以提供候选关联。后续影响面分析可以先给出“候选相关测试”，再用 Coverage 提升准确度。

## 稳定 ID 设计

图谱中的节点需要稳定 ID。可以考虑：

```text
repository + qualified_name + signature
```

对于方法：

```text
com.example.OrderService#cancel(java.lang.Long)
```

对于文件：

```text
file:src/main/java/com/example/OrderService.java
```

稳定 ID 能帮助增量更新、版本对比和报告跳转。

## 输出示例

节点示例：

```json
{
  "id": "method:com.example.OrderService#cancel(java.lang.Long)",
  "type": "method",
  "name": "cancel",
  "file_path": "src/main/java/com/example/OrderService.java",
  "start_line": 32,
  "end_line": 48
}
```

边示例：

```json
{
  "source": "class:com.example.OrderService",
  "target": "method:com.example.OrderService#cancel(java.lang.Long)",
  "type": "contains"
}
```

## 小结

源码结构采集的目标，是把代码从文本转换成节点和边。第一版不需要完美语义解析，但必须保留源码位置、稳定 ID 和关系来源。

下一章会把这些采集结果组织成代码图谱。

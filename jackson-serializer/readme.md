## jackson 序列化反序列化  自定义字段解析处理

### JsonDeserializer / JsonSerializer  / ContextualDeserializer(接口)

1. ContextualDeserializer 接口
   创建序列化对象时，获取当前字段对应类型及注解等信息
   
2. JsonDeserializer / JsonSerializer  序列化/反序列化对象
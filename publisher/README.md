负责对外提供请求服务，考虑支持主动/被动两种信息获取模式

获取模式：
1. 支持http方式的接口访问
2. 支持基于websocket方式的接口访问
3. 支持基于rabbitmq方式的接口访问

publisher是负责内容广播的入口，数据的获取方式有：
1. 以id为标识，从数据源中获取数据
2. 以完整的数据表达方式，获取数据

用户可以显式地把需要访问的服务向publisher请求，也可以等待publisher的通知

计划通过camel实现

- publisher: service hook, DSL for configuration

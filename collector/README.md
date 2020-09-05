
使用camel实现的eip集成接口，支持各种接入
- input: flume, http in/springboot, streaming out, filter support

接入的数据主要做两个处理：
1. 发送到spark streaming
2. 按需写入到存储中

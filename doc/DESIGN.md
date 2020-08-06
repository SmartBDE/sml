## 系统

![](arch.png)

![](highview.png)

![](sml.png)

## 模块

- 负载均衡层，haproxy，提供负载均衡、限流/限入等安全功能
- 信息收集器，考虑采集格式的问题
  - 信息收集采用接口方式，便于接入各种第三方系统
  - 考虑为不同的语言和平台，提供SDK
- 入库存储，直接保存到mongodb/hbase/hadoop？
  - mongodb
    - TB级数据支持，且部署简单
    - 提供schemaless data/document/image的支持
    - 丰富的接口支持，接入hadoop生态
  - hbase (scan support?)
    - hbase的分布式部署支持PB级别数据
    - hbase的读写性能优越，且支持数据/文档/图像三种常见的文件格式
    - hbase的二级索引支持：lily，为文本处理增加了极大的可能性
- BI层，spark sql/hbase phoenix + zepplin/saiku ?
  - 接入各种现成的BI系统，适应常规的商业智能团队，采用 统计分析 + 平台实施 的团队组合模式
  - saiku 成熟的BI产品？http://www.spagobi.org/homepage/services/documentation/
  - zeppelin -> spark

## 周边

### 打包工具
### 命令行工具
对服务的使用进行支持
### 后台工具
管理工具增强
### 配置
### 文档
### 模板&案例
采用模板方式，组织在平台上的各种功能
### 测试工具
服务压测、评估、监控等


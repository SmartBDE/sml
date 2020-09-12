# Storage

storage: mongodb light solution/hbase heavy level data

存储层的抽象，对外屏蔽了底层存储的实现

计划支持的几种存储类型(支持水平扩展，支持实时更新，支持统计分析，可以选择的存储引擎不多)：
1. MongoDB
2. JDBC(分布式数据库)
3. ElasticSearch
4. HBase
5. Kudu
6. ...

数据需要以表格方式(DataSet)表示，通过spark对数据源的支持，对数据进行读取

storage以类库的方式存在，底层使用Spark组件生态实现，包含以下支持：
1. 结构化数据的读写（DateSet）
2. 文件数据的读取

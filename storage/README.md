storage: mongodb light solution/hbase heavy level data

存储层的抽象，对外屏蔽了底层存储的实现

计划支持的几种存储类型：
1. JDBC
2. MongoDB
3. HBase

数据需要以表格方式(DataSet)表示，通过spark对数据源的支持，对数据进行读取

storage以类库的方式存在，包含以下支持：
1. 结构化数据的读写（DateSet）
2. 文件数据的读取

底层使用Spring Data实现
存储层的抽象，对外屏蔽了底层存储的实现

计划支持的几种存储类型：
1. HBase
2. MongoDB
3. Geode
4. JDBC

数据需要以表格方式(DataSet)表示，通过spark对数据源的支持，对数据进行读取

- storage: mongodb light solution/hbase, pb level data

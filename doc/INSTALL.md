# 安装

## windows单机开发环境

### 安装包准备

- hadoop-2.8.1.tar.gz/hadoop-common-2.x.0-bin-master.zip
- hbase-1.3.1-bin.tar.gz
- apache-phoenix-4.11.0-HBase-1.3-bin.tar.gz
- apache-flume-1.7.0-bin.tar.gz
- zeppelin-0.7.2-bin-netinst.tgz
- spark-2.2.0-bin-without-hadoop.tgz

### 安装步骤

**environment**

- JAVA_HOME

**hadoop**

- hadoop-common-2.2.0-bin-master.zip 更新hadoop/bin目录（这个对版本有要求，2.8.1和2.2.0不对应，参考hadoop.dll更新说明获取对应版本）
- https://wiki.apache.org/hadoop/Hadoop2OnWindows
- 采用单节点伪分布式作为安装模式
- 运行 %HADOOP_PREFIX%\sbin\start-dfs.cmd
- 更新hadoop.dll https://stackoverflow.com/questions/33211599/hadoop-error-on-windows-java-lang-unsatisfiedlinkerror
- 测试 bin\hdfs dfs -put myfile.txt /, bin\hdfs dfs -ls /, start-yarn.cmd, stop-yarn.cmd

**hbase**

- https://hbase.apache.org/cygwin.html
- 配置使用hadoop hdfs作为hbase.rootdir
- 管理员权限运行cygwin-terminal ./bin/start-hbase.sh
- 测试是否正常运行 ./bin/hbase shell

**phoenix-4**

- https://phoenix.apache.org/installation.html
- 测试，下载客户端后运行 https://jaist.dl.sourceforge.net/project/squirrel-sql/1-stable/3.8.0/squirrel-sql-3.8.0-standard.jar
- jdbc:phoenix:localhost:2181:/hbase-unsecure

**zeppelin**

- 安装jdbc/hbase interpreter
- 配置jdbc/hbase，查看是否正确连接

**flume**

- https://flume.apache.org/FlumeUserGuide.html
- apache phoenix plugin: 需要把jar拷贝到flume/lib下

**spark**

- http://www.powerxing.com/spark-quick-start-guide/
- http://spark.apache.org/docs/latest/spark-standalone.html
- https://phoenix.apache.org/phoenix_spark.html


## linux 生产环境

### 环境设置

- 设置JAVA_HOME, HADOOP_HOME, SPARK_HOME


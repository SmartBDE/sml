# 安装

## linux

单机开发环境，单机运行环境

### 安装包准备

- hadoop-2.8.1.tar.gz
- hbase-1.3.1-bin.tar.gz
- apache-phoenix-4.11.0-HBase-1.3-bin.tar.gz
- apache-flume-1.7.0-bin.tar.gz
- zeppelin-0.7.2-bin-all.tgz
- spark-2.2.0-bin-without-hadoop.tgz

### 安装步骤

#### 环境设置

安装机器，fedora 23 x64

- 安装JDK（默认安装1.8.0_60）
- 安装python（提示安装python 2.7.11）

#### hadoop

- https://hadoop.apache.org/docs/r2.8.0/hadoop-project-dist/hadoop-common/SingleCluster.html
- 修改etc/hadoop/hadoop-env.sh，export JAVA_HOME
- 遵循pseudo-distributed mode进行配置，注意设置fs.default.name为hdfs://0.0.0.0:19000
- 启动服务 sbin/start-dfs.sh
- 测试服务 bin/hdfs dfs -put xxx /, bin/hdfs dfs -ls /
- 尝试连续两次sbin/start-dfs.sh后，服务测试成功？
- Directory /tmp/hadoop-hadoop/dfs/name is in an inconsistent state: storage directory does not exist or is not accessible. 运行：bin/hadoop namenode -format
- http://www.linuxidc.com/Linux/2012-03/56348.htm 需要把tmp目录，设置在非临时目录下

#### hbase

- http://hbase.apache.org/book.html#quickstart
- 安装模式 preudo-distributed local
- ./bin/start-hbash.sh 注意，需要配置conf/hbase-env.sh中JAVA_HOME和HBASE_CLASSPATH字段
- 测试是否正常创建目录 hadoop-2.8.1/bin/hadoop fs -ls /hbase
- 测试命令 ./bin/hbase shell
- 启动服务 ./bin/start-hbase.sh

#### phoenix

- https://phoenix.apache.org/installation.html
- 把jar拷贝到hbase/lib下，重启hbase
- sqlline.py localhost，输入!tables查看是否正常

#### zeppelin

- zeppelin for phoenix需要安装: yum install java-1.8.0-openjdk-devel
- cp ../apache-phoenix-4.11.0-HBase-1.3-bin/*client.jar interpreter/jdbc/
- cp ../apache-phoenix-4.11.0-HBase-1.3-bin/phoenix-core-4.11.0-HBase-1.3.jar interpreter/jdbc/
- https://zeppelin.apache.org/docs/0.7.0/interpreter/jdbc.html#thick-client-connection
- 采用thick client模式进行安装
- 配置jdbc/hbase，查看是否正确连接
- 启动服务 ./bin/zeppelin-daemon.sh start

#### flume

- http://flume.apache.org/FlumeUserGuide.html
- bin/flume-ng agent --conf conf --conf-file conf/example.conf --name a1 -Dflume.root.logger=INFO,console
- https://github.com/forcedotcom/phoenix/wiki/Apache-Flume-Plugin 文档太老
- http://flume.apache.org/FlumeDeveloperGuide.html 开发者指南，没有涉及如何配置plugin
- http://xingwu.me/2014/10/04/Implement-a-Flume-Deserializer-Plugin-to-Import-XML-Files/
- https://phoenix.apache.org/flume.html
- 拷贝phoenix-core-4.11.0-HBase-1.3.jar, phoenix-4.11.0-HBase-1.3-client.jar, phoenix-4.11.0-HBase-1.3-thin-client.jar到plugin.d/phoenix-sink/lib
- 拷贝phoenix-flume-4.11.0-HBase-1.3.jar到plugin.d/phoenix-sink/lib
- 填写配置文件，参考
  - https://git-wip-us.apache.org/repos/asf?p=phoenix.git;a=blob_plain;f=config/apache-access-logs.properties;hb=master
  - https://community.hortonworks.com/questions/103855/phoenix-flume-sink-problem-for-binary-001-fields-s.html
- phoenix依赖twill-discovery-api-0.12.0.jar, twill-zookeeper-0.12.0.jar 需要添加到flume/lib下
- flume默认的-Xmx是20m，增加到100m以便满足运行需要
- 运行命令  bin/flume-ng agent --conf conf --conf-file conf/phoenix.conf --name agent -Dflume.root.logger=INFO,console

```
# configurationforagent
agent.sources=netcat-source
agent.sinks=phoenix-sink
agent.channels=memoryChannel

# configurationforchannel
agent.channels.memoryChannel.type=memory
agent.channels.memoryChannel.transactionCapacity=100
agent.channels.memoryChannel.byteCapacityBufferPercentage=20

# configurationforsource
agent.sources.netcat-source.type=netcat
agent.sources.netcat-source.bind=localhost
agent.sources.netcat-source.port=33333

# configurationforsink
agent.sinks.phoenix-sink.type=org.apache.phoenix.flume.sink.PhoenixSink
agent.sinks.phoenix-sink.batchSize=100
agent.sinks.phoenix-sink.table=NETCAT_LOGS
agent.sinks.phoenix-sink.ddl=CREATE TABLE IF NOT EXISTS NETCAT_LOGS(ts TIMESTAMP,msg VARCHAR,f_host VARCHAR CONSTRAINT pk PRIMARY KEY(ts))
agent.sinks.phoenix-sink.zookeeperQuorum=localhost
agent.sinks.phoenix-sink.serializer=REGEX
agent.sinks.phoenix-sink.serializer.rowkeyType=timestamp
agent.sinks.phoenix-sink.serializer.regex=(.*) 
agent.sinks.phoenix-sink.serializer.columns=ts,msg
agent.sinks.phoenix-sink.serializer.headers=f_host

# configurationforbinding
agent.sources.netcat-source.channels=memoryChannel
agent.sinks.phoenix-sink.channel=memoryChannel
```

#### spark

- 搭建spark运行平台，计算请求提交到spark计算平台，获得计算结果后返回
- http://www.powerxing.com/spark-quick-start-guide/
- http://spark.apache.org/docs/latest/spark-standalone.html
- 配置conf/spark-env.sh，运行bin/run-example SparkPi, bin/spark-shell测试是否正常运行
- sbin/start-master.sh启动运算平台

#### development

如何开发一个应用

- https://phoenix.apache.org/phoenix_spark.html spark读取phoenix数据
- https://docs.spring.io/spring-hadoop/docs/current/reference/html/springandhadoop-spark.html 通过job的方式调用spark
- https://github.com/Zhuinden/spring-spark-example 在spring boot中直接调用spark
- https://deeplearning4j.org/cn/ 官方网站

## windows单机开发环境 (失败，待继续)

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
- http://www.cnblogs.com/raphael5200/p/5260198.html 需要配置环境
- 失败，在windows下发生错误，资料较少，决定调整到linux环境下搭建
- 测试，下载客户端后运行 https://jaist.dl.sourceforge.net/project/squirrel-sql/1-stable/3.8.0/squirrel-sql-3.8.0-standard.jar
- jdbc:phoenix:localhost:2181:/hbase-unsecure

**zeppelin**

- 安装jdbc/hbase interpreter
- 配置jdbc/hbase，查看是否正确连接
- hbase报错，提示找不到jruby class

**flume**

- https://flume.apache.org/FlumeUserGuide.html
- apache phoenix plugin: 需要把jar拷贝到flume/lib下
- bin/flume-ng agent --conf conf --conf-file conf/example.conf --name a1
- hbase因classpath找不到无法启动

**spark**

- http://www.powerxing.com/spark-quick-start-guide/
- http://spark.apache.org/docs/latest/spark-standalone.html
- https://phoenix.apache.org/phoenix_spark.html


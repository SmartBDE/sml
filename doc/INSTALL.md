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


## linux

单机开发环境c

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

#### hbase

- http://hbase.apache.org/book.html#quickstart
- 安装模式 preudo-distributed local
- ./bin/start-hbash.sh 注意，需要配置conf/hbase-env.sh中JAVA_HOME和HBASE_CLASSPATH字段
- 测试是否正常创建目录 hadoop-2.8.1/bin/hadoop fs -ls /hbase
- 测试命令 ./bin/hbase shell

#### phoenix

- https://phoenix.apache.org/installation.html
- 把jar拷贝到hbase/lib下，重启hbase
- sqlline.py localhost，输入!tables查看是否正常

#### 


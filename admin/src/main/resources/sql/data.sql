----------------------------------------
-- 测试数据
----------------------------------------
insert into person(name) values ('Jason');
insert into person(name) values ('Jacky');

----------------------------------------
-- 系统配置
----------------------------------------
insert into systems(name, address, start) values ('collector', "http://127.0.0.1:18000/", now());
insert into systems(name, address, start) values ('publisher', "http://127.0.0.1:20000/", now());
insert into systems(name, address, start) values ('pipeline', "http://127.0.0.1/", now());
insert into systems(name, address, start) values ('smlbatch', "http://127.0.0.1/", now());

insert into configs(cname, ckey, cvalue) values ('collector', 'from.host', '127.0.0.1');
insert into configs(cname, ckey, cvalue) values ('collector', 'from.port', '18000');
insert into configs(cname, ckey, cvalue) values ('collector', 'from.protocol', 'jetty:http');

insert into configs(cname, ckey, cvalue) values ('collector', 'to.host', '127.0.0.1');
insert into configs(cname, ckey, cvalue) values ('collector', 'to.port', '19000');
insert into configs(cname, ckey, cvalue) values ('collector', 'to.protocol', 'netty:tcp');

insert into configs(cname, ckey, cvalue) values ('publisher', 'from.host', '127.0.0.1');
insert into configs(cname, ckey, cvalue) values ('publisher', 'from.port', '20000');
insert into configs(cname, ckey, cvalue) values ('publisher', 'from.protocol', 'jetty:http');

insert into configs(cname, ckey, cvalue) values ('publisher', 'to.host', '/Volumes/Transcend/github/sml/publisher/?fileName=report.txt');
insert into configs(cname, ckey, cvalue) values ('publisher', 'to.port', '');
insert into configs(cname, ckey, cvalue) values ('publisher', 'to.protocol', 'file:');

----------------------------------------
-- 插件列表
----------------------------------------
insert into plugins(plugin, ckey, cvalue) values ('LogisticRegressionPredict.V1', 'modelpath', '../data/model/LogisticRegressionV1.model');
insert into plugins(plugin, ckey, cvalue) values ('LogisticRegressionPredict.V1', 'dimension', '10');
insert into plugins(plugin, ckey, cvalue) values ('LogisticRegressionPredict.V1', 'seed', '47');

insert into plugins(plugin, ckey, cvalue) values ('JdbcInput.V1', 'url', 'jdbc:mysql://127.0.0.1:33061/springtest?useUnicode=true&characterEncoding=utf-8&useSSL=false');
insert into plugins(plugin, ckey, cvalue) values ('JdbcInput.V1', 'user', 'springtest');
insert into plugins(plugin, ckey, cvalue) values ('JdbcInput.V1', 'pwd', '123456');
insert into plugins(plugin, ckey, cvalue) values ('JdbcInput.V1', 'table', 'textfile');

insert into plugins(plugin, ckey, cvalue) values ('FileOutput.V1', 'path', '../data/model/LogisticRegressionV1.model');

insert into plugins(plugin, ckey, cvalue) values ('StartLogger.V1', 'url', 'jdbc:mysql://127.0.0.1:33061/springtest?useUnicode=true&characterEncoding=utf-8&useSSL=false');
insert into plugins(plugin, ckey, cvalue) values ('StartLogger.V1', 'user', 'springtest');
insert into plugins(plugin, ckey, cvalue) values ('StartLogger.V1', 'pwd', '123456');
insert into plugins(plugin, ckey, cvalue) values ('StartLogger.V1', 'table', 'logs');

insert into plugins(plugin, ckey, cvalue) values ('StopLogger.V1', 'url', 'jdbc:mysql://127.0.0.1:33061/springtest?useUnicode=true&characterEncoding=utf-8&useSSL=false');
insert into plugins(plugin, ckey, cvalue) values ('StopLogger.V1', 'user', 'springtest');
insert into plugins(plugin, ckey, cvalue) values ('StopLogger.V1', 'pwd', '123456');
insert into plugins(plugin, ckey, cvalue) values ('StopLogger.V1', 'table', 'logs');

----------------------------------------
-- 插件对应类
----------------------------------------
insert into pluginclass(name, type, clazz) values ('GroovyScript', 'filter', 'me.smartbde.sml.commonutils.plugins.filter.GroovyScript');
insert into pluginclass(name, type, clazz) values ('JsonParser', 'filter', 'me.smartbde.sml.commonutils.plugins.filter.JsonParser');
insert into pluginclass(name, type, clazz) values ('LineParser', 'filter', 'me.smartbde.sml.commonutils.plugins.filter.LineParser');
insert into pluginclass(name, type, clazz) values ('LogisticRegressionPredict', 'sqlFilter', 'me.smartbde.sml.commonutils.plugins.filter.LogisticRegressionPredict');
insert into pluginclass(name, type, clazz) values ('LogisticRegressionTrain', 'filter', 'me.smartbde.sml.commonutils.plugins.filter.LogisticRegressionTrain');
insert into pluginclass(name, type, clazz) values ('SqlFilter', 'filter', 'me.smartbde.sml.commonutils.plugins.filter.SqlFilter');
insert into pluginclass(name, type, clazz) values ('StartLogger', 'filter', 'me.smartbde.sml.commonutils.plugins.filter.StartLogger');
insert into pluginclass(name, type, clazz) values ('StopLogger', 'filter', 'me.smartbde.sml.commonutils.plugins.filter.StopLogger');

insert into pluginclass(name, type, clazz) values ('JdbcInput', 'batchInput', 'me.smartbde.sml.commonutils.plugins.input.JdbcInput');
insert into pluginclass(name, type, clazz) values ('TcpStreamingInput', 'streamInput', 'me.smartbde.sml.commonutils.plugins.input.TcpStreamingInput');

insert into pluginclass(name, type, clazz) values ('FileOutput', 'output', 'me.smartbde.sml.commonutils.plugins.output.FileOutput');
insert into pluginclass(name, type, clazz) values ('JdbcOutput', 'output', 'me.smartbde.sml.commonutils.plugins.output.JdbcOutput');

insert into pluginclass(name, type, clazz) values ('MaskFilter', 'filter', 'me.smartbde.sml.commonutils.plugins.filter.MaskFilter');
insert into pluginclass(name, type, clazz) values ('Stdout', 'output', 'me.smartbde.sml.commonutils.plugins.output.Stdout');

----------------------------------------
-- 任务列表
----------------------------------------
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionTrain', 'input', 'JdbcInput.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionTrain', 'output', 'FileOutput.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionTrain', 'filter', 'StartLogger.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionTrain', 'filter', 'LogisticRegressionTrain.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionTrain', 'filter', 'StopLogger.V1');

insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict2', 'input', 'JdbcInput.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict2', 'output', 'FileOutput.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict2', 'filter', 'StartLogger.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict2', 'sqlFilter', 'LogisticRegressionPredict.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict2', 'filter', 'StopLogger.V1');

insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict', 'spark.host', '127.0.0.1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict', 'spark.port', '19000');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict', 'input', 'TcpStreamingInput.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict', 'output', 'Stdout.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict', 'filter', 'StartLogger.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict', 'sqlFilter', 'LogisticRegressionPredict.V1');
insert into jobs(name, type, plugin) values ('DemoLogisticRegressionPredict', 'filter', 'StopLogger.V1');

----------------------------------------
-- 激活的任务
----------------------------------------
insert into schedules(jobname, type, runat, nextid) values ('DemoLogisticRegressionTrain', -1, '', 3);
insert into schedules(jobname, type, runat) values ('DemoLogisticRegressionPredict2', -1, '');
insert into schedules(jobname, type, runat) values ('DemoLogisticRegressionPredict2', -2, '');
insert into streamings(jobname) values ('DemoLogisticRegressionPredict');
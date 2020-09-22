-----------------------------------------
-- 测试用的表person
-----------------------------------------
drop table if exists person;

create table person (
    id int unsigned auto_increment,
    name varchar(100),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----------------------------------------
-- 系统配置表
-----------------------------------------
drop table if exists configs;

create table configs (
    cname varchar(100),
    ckey varchar(100),
    cvalue varchar(100),
    CONSTRAINT sys_pk_configs PRIMARY KEY (cname, ckey)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----------------------------------------
-- 插件配置表
-- LogisticRegressionTrainV1 LogisticRegressionTrain 训练次数 300
-- LogisticRegressionTrainV1 LogisticRegressionTrain 维度 10
-- LogisticRegressionPredictV1 LogisticRegressionPredict path ../../../模型文件名
-- LogisticRegressionPredictV1 LogisticRegressionPredict 维度 10
-- FileInstance1 FileStorage file path ../../../
-- FileInstance2 FileStorage file path ../../../
-- LineParserInstance1 LineParser 分隔符 " "
-- LineParserInstance1 LineParser 字段名 "a b c"
-- sqlConvertInstance1 sqlConvert sql "select int(a), int(b) from table"
-----------------------------------------
drop table if exists plugins;

create table plugins (
    name varchar(100),
    plugin varchar(100),
    ckey varchar(100),
    cvalue varchar(500),
    CONSTRAINT sys_pk_configs PRIMARY KEY (name, plugin, ckey)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----------------------------------------
-- 算法配置表
-- LogisticRegression 随机种子 47
-----------------------------------------
drop table if exists algorithms;

create table algorithms (
    name varchar(100),
    ckey varchar(100),
    cvalue varchar(100),
    CONSTRAINT sys_pk_configs PRIMARY KEY (cname, ckey)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----------------------------------------
-- 任务配置表
-- DemoLogisticRegressionTrain input FileInstance1
-- DemoLogisticRegressionTrain output FileInstance2
-- DemoLogisticRegressionTrain filter LineParserInstance1
-- DemoLogisticRegressionTrain filter sqlConvertInstance1
-- DemoLogisticRegressionTrain filter LogisticRegressionTrainV1
-----------------------------------------
drop table if exists jobs;

create table jobs (
    name varchar(100),
    type varchar(10),
    plugin varchar(100),
    CONSTRAINT sys_pk_configs PRIMARY KEY (name, type, plugin)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

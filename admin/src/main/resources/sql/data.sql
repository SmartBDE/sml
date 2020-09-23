insert into person(name) values ('Jason');
insert into person(name) values ('Jacky');

insert into systems(name, http, start) values ('collector', "http://127.0.0.1:18000/", now());
insert into systems(name, http, start) values ('publisher', "http://127.0.0.1:21000/", now());

insert into configs(cname, ckey, cvalue) values ('collector', 'from.host', '127.0.0.1');
insert into configs(cname, ckey, cvalue) values ('collector', 'from.port', '18000');
insert into configs(cname, ckey, cvalue) values ('collector', 'from.protocol', 'jetty:http');

insert into configs(cname, ckey, cvalue) values ('collector', 'to.host', '127.0.0.1');
insert into configs(cname, ckey, cvalue) values ('collector', 'to.port', '19000');
insert into configs(cname, ckey, cvalue) values ('collector', 'to.protocol', 'netty:tcp');

insert into plugins(plugin, ckey, cvalue) values ('LogisticRegressionPredict.V1', 'modelpath', '../data/model/LogisticRegressionV1.model');
insert into plugins(plugin, ckey, cvalue) values ('LogisticRegressionPredict.V1', 'dimension', '10');

insert into algorithms(cname, ckey, cvalue) values ('LogisticRegression', 'seed', '47');

insert into schedules(jobname, type, runat) values ('DemoLogisticRegressionTrain', -1, '');
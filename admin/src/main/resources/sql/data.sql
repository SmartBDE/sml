insert into person(name) values ('Jason');
insert into person(name) values ('Jacky');

insert into configs(cname, ckey, cvalue) values ('collector', 'from.host', '127.0.0.1');
insert into configs(cname, ckey, cvalue) values ('collector', 'from.port', '18000');
insert into configs(cname, ckey, cvalue) values ('collector', 'from.protocol', 'jetty:http');

insert into configs(cname, ckey, cvalue) values ('collector', 'to.host', '127.0.0.1');
insert into configs(cname, ckey, cvalue) values ('collector', 'to.port', '19000');
insert into configs(cname, ckey, cvalue) values ('collector', 'to.protocol', 'netty:tcp');

insert into plugins(plugin, ckey, cvalue) values ('LogisticRegressionPredict.V1', '', '');
insert into plugins(plugin, ckey, cvalue) values ('LogisticRegressionPredict.V1', '', '');
insert into plugins(plugin, ckey, cvalue) values ('LogisticRegressionPredict.V1', '', '');
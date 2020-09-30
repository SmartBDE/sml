delete from configs where cname='publisher' and ckey='to.host';
insert into configs(cname, ckey, cvalue) values ('publisher', 'to.host', 'D:\\workshop\\prjs\\sml\\publisher\\?fileName=report.txt');

delete from plugins where plugin='LogisticRegressionPredict.V1' and ckey='modelpath';
insert into plugins(plugin, ckey, cvalue) values ('LogisticRegressionPredict.V1', 'modelpath', 'D:\\workshop\\prjs\\sml\\data\\model\\LogisticRegressionV1.model');

delete from plugins where plugin='FileOutput.V1' and ckey='path';
insert into plugins(plugin, ckey, cvalue) values ('FileOutput.V1', 'path', 'D:\\workshop\\prjs\\sml\\data\\model\\LogisticRegressionV1.model');

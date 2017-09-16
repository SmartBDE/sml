# 智能引擎: sml

SML尝试解决智能计算基础建设的问题，提供数据导入、模型试验、模型部署、对外接口这一系列
大数据智能应用所需的基础性设施，简化从数据试验到上线运行的过程，降低整体研发成本。

用户可以采取模板化的方式，通过对内置的算法模块，进行模板化的设置和导入数据后，即可使用服务。
理想的情况，完成模型设计和试验后，可直接把模型部署上线，提供服务。

SML使用JAVA开发，以满足生产环境正式部署的需求。基于JAVA的方案，便于功能二次开发，更适合商业环境的
实际使用。底层计划整合spark, dl4j作为计算引擎，以便和目前流行的深度学习方案&大数据方案
接轨，为实际应用提供更多的可能性。

# 特性

- 一站式部署方案，简化部署管理
- 方便的对外调用接口，便于和其他应用对接
- 简便的使用接口，模板化的模型配置方式
- 图形化管理后台
- PaaS级别的架构设计，具有良好的横向拓展能力



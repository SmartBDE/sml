spring-test: a demo app to show how to implement spark application

程序的总入口，负责把各模块运行起来，并提供状态的监测和系统的配置服务

程序底层使用spark实现，通过spark的流批一体能力，以job的方式，提交到spark集群中运行

包括：
1. 启动运行的环境，组件包括collector/pipeline/monitor/publisher/smlbatch
2. 提供各组件的状态监控及参数配置界面(TipDM)
   - 提供pipeline插件的管理配置界面
   - 提供smlbatch作业的管理配置界面
3. 启动collector对外的接口，基于Camel，我们先假设数据都是从kafka过来
4. 启动pipeline，并完成基于spark streaming引擎的插件初始化
5. 启动publisher对外的发布，基于Camel，提供消息发布监听的实现
6. 启动smlbatch批量作业的调度，实现批量作业的执行与管理
7. 启动monitor，查看pipeline/smlbatch的实际运行效果，并提供分析工具
8. 启动Spark Thrift Server，提供探索性分析服务
9. 增加辅助功能，如模拟输入，如数据初始化

各组件的接入规范：
1. 以SpringBoot Application启动，可命令行启动
2. 提供对外的API接口，包括状态监测以及配置设置

前端/后端实现技术
https://blog.csdn.net/zwq56693/article/details/107892980 thymeleaf
https://segmentfault.com/a/1190000019537708 webjars
https://www.cnblogs.com/okong/p/springboot-eighteen.html webjars
https://www.cnblogs.com/ityouknow/p/5891443.html jpa
https://blog.csdn.net/zhangcongyi420/article/details/88087511 jpa
程序的总入口，负责把各模块运行起来

包括：
1. 启动运行的环境，包括第三方组件：Storage/collector/pipeline
2. 提供各组件的状态监控及参数配置界面
   - 提供pipeline插件的管理配置界面
   - 提供sml作业的管理配置界面
3. 启动collector对外的接口，基于Spring Integration，我们先假设数据都是从kafka过来，数据会写入storage
4. 启动pipeline，并完成基于spark streaming引擎的插件初始化
5. 启动publisher对外的发布，基于Spring Integration，提供消息发布监听的实现
6. 启动sml批量作业的调度，实现批量作业的执行与管理
7. 启动Spark Thrift Server，提供探索性分析服务

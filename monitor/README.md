我们需要一个可以展现系统运行状况的可视化界面

计划使用gwt实现，达到简化开发流程，快速迭代的效果

batch/stream运行的统计信息，主要功能列表：
1. 调用信息：命令(类型)，次数，耗时
2. 处理情况：命令，运行结果，输入，上下文(如时间)
3. 统计分析：
   - 相似算法运行情况对比
   - 输入信息的规律
   - 运行结果的规律
   - ...

暂时提供以下信息：
1. 按job类型统计次数 -> 柱状图
2. 按job类型统计耗时 -> 折线图

- monitor: (*)
  - dashboard implement

- 提供模型查询接口
  - 服务层，提供预测结果的调用（需要考虑访问的性能）
    - REST API，完成对后端接口的调用，实现实时消息交互
    - 模型AB测试支持，记录多个版本引擎的查询结果，并对比分析
  - 核心数据存储层（保存模型/请求上下文/其他？）
    - 权限控制？数据获取规模控制？
    - 自开发后台？输入SQL，生成报表？方便统计人员调研？
    - 第三方系统支持数据的拉取？导入更多的数据？
    - 支持对计算结果的可视化查看，分析，调优？
    - 记录评估结果，以便和将来发生的真实情况做对比
  - 离线计算层，负责核心数据的持续运算（对性能敏感，需要较多的服务器资源？）
    - 模型的评估
    - 模型的持续演进

https://github.com/pepstock-org/Charba
http://www.pepstock.org/Charba-Showcase/Charba_Showcase.html
https://github.com/manolo/gwt-polymer-elements
https://manolo.github.io/gwt-polymer-elements/demo


关于gwt-maven-archetypes
- 可以用，但是教程不够详细，可用的操作步骤如下
- 使用maven monitor lifecycle clean清除编译的内容
- 运行一次maven monitor plugins gwt-codeserver
  - 创建目录monitor/target/gwt/devmode/war(maven monitor plugins gwt gwt-devmode/gwt-codeserver)
  - 如果其他模块(shared/client)发生了修改，需要先运行maven monitor plugins gwt gwt-devmode/gwt-codeserver
- 使用maven monitor-server lifecycle package打包war
- 把monitor/monitor-server/target/monitor-server-1.0-SNAPSHOT下的目录，拷贝到monitor下的target/gwt/devmode/war下
  - 把monitor-client\war目录拷贝到monitor\target\gwt\devmode\war\app 这里是因为需要做app的独立页面控制 <- 通过调整目录结构已不需要
- 运行monitor的gwt:devmode
- 打开http://127.0.0.1:8888/app/
- 不太方便，可能还不是正确的使用方法，待进一步分析研究
- 前端monitor-client的代码是可以边写边刷新访问的，后端必须编译，所以建议用单元测试来先测试后端的代码

关于gwt-maven-archetypes
- 使用maven创建项目
- 运行maven monitor plugins clean clean，可以把过往编译的内容都删除
- 运行一次maven monitor plugins gwt gwt-devmode/gwt-codeserver 把项目都编译好
- 把monitor-server\src\main\webapp目录拷贝到monitor\target\gwt\devmode\war
- 把monitor-client\war目录拷贝到monitor\target\gwt\devmode\war\app <- 通过调整目录结构已不需要
- 把monitor-server\target\classes目录拷贝到monitor\target\gwt\devmode\war
- 把monitor-shared\target\classes目录拷贝到monitor\target\gwt\devmode\war
- 把monitor-server依赖的lib拷贝到monitor\target\gwt\devmode\war\WEB-INF\lib
- 再运行一次maven monitor plugins gwt gwt-devmode 把项目都编译好
- 访问http://127.0.0.1:8888/app/

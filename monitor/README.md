我们需要一个可以管理系统如何运行的后台界面

计划使用gwt bootstrap/chart实现

界面实现技术：
https://github.com/gwtbootstrap3/gwtbootstrap3
https://github.com/pepstock-org/Charba
https://www.jianshu.com/p/f28778a099ee
https://gwt-maven-plugin.github.io/gwt-maven-plugin/
https://github.com/tbroyer/gwt-maven-archetypes
https://github.com/vaadin/vaadin/
https://blog.csdn.net/dnc8371/article/details/106701787 vaadin教程

batch/stream运行的统计信息，主要功能列表：
1. 运行耗时
2. 运行结果
3. 输入上下文

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
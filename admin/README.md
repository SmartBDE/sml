# admin

spring-test: a demo app to show how to implement spark application

程序的总入口，负责把各模块运行起来，并提供状态的监测和系统的配置服务
程序底层使用spark实现，通过spark的流批一体能力，以job的方式，提交到spark集群中运行

包括：
1. 启动运行的环境，组件包括collector/publisher/smlbatch/pipeline/monitor/bi
2. 启动collector对外的接口，基于Camel，我们先假设数据都是从http/kafka过来
3. 启动publisher对外的发布，基于Camel，提供消息发布监听的实现
4. 提供插件的参数配置及运行状态监控界面(TipDM)
   - 提供pipeline插件的管理配置界面
   - 提供smlbatch作业的管理配置界面
5. 启动pipeline，并完成基于spark streaming引擎的插件初始化和运行
6. 启动smlbatch批量作业的调度，实现批量作业的执行与管理
   - 支持定时和手动执行任务
   - 支持任务完成后，触发下一个任务
7. 启动monitor，查看pipeline/smlbatch的实际运行效果，并提供分析工具
8. 启动Presto/Davinci，提供探索性分析服务
9. 增加辅助功能，如模拟输入，如数据初始化

各组件的接入规范：
1. 以SpringBoot Application启动，可命令行启动
2. 提供对外的API接口，包括状态监测以及配置设置

## 页面设计

### 系统入口 entry
- 系统页面展示
  - 组件名，组件状态
### 收集器 collector
- 配置信息展示
  - 接入的信息源（格式、端口）
  - 转发的目标（格式、端口）
### 发布器 publisher
- 配置信息展示
  - 接入的信息源（格式、端口）
  - 转发的目标（格式、端口）
### 插件配置 plugins
- 插件列表（分类别）
  - 插件编辑界面
### 算法配置 scratch
- 算法列表
  - 算法信息
### 处理器配置 pipeline
- 处理器列表
  - 处理器配置编辑界面
### 定时任务配置 scheduler
- 定时任务列表
  - 定时任务配置信息
### 系统分析器 monitor
- 激活监控的处理器/定时任务列表
### 统计分析服务 Presto/Davinci
- 服务配置
### 辅助功能 utility
- 功能清单
  - 输入模拟
  - 数据生成

## 页面风格简单约定

- 字体大小 <small>
- 标题 <h4>
- 按钮 右下方
- 表单
  - 放在box中
  - 表单操作 右上方

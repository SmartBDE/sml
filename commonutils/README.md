# 公用类库

为项目的公共实现，提供服务，单不提供具体的实现

批量作业的实现见：smlbatch
流式作业的实现见：pipeline

包括以下模块

## 配置

以数据方式保存，包括以下配置：
1. 运行时的任务参数(key-value模式)
   - 系统参数
   - spark运行设定
2. 输入
   - 输入目标
   - 输入参数
3. 处理器
   - 处理器名字
   - 处理器版本(考虑包含在处理器名字中)
   - 处理器参数(key-value模式)
4. 输出
   - 输出目标
   - 输出参数

配置的格式
| section      |  key   | value |
|  -----       | -----  | ----- |
| spark        |  key   | value |
| input.socket |  key   | value |
| filter.f1    |  pri   | 1     |
| filter.f1    |  key   | value |
| filter.f2    |  pri   | 2     |
| filter.f2    |  key   | value |
| output.log   |  src   | table |
| output.log   |  src.t | t.name|

## 插件机制

1. 可扩展的机制(系统级，考虑动态载入)
   - 输入
   - 输出
   - 处理器(filter)
2. 处理器实现，sql函数通过spark udf进行扩展
3. 插件要做类型的区分，看是否支持流式/批量
4. 输入/输出是通过spark生态的支持，手动编码实现

## 插件开发步骤

1. 依接口规范开发插件
2. 配置插件参数 insert into plugins(plugin, ckey, cvalue)
3. 配置插件初始化对应类 insert into pluginclass(name, type, clazz)

## 插件使用步骤

1. 

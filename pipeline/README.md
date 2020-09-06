数据处理的工厂，基于插件的机制，现成的插件可通过配置的方式直接运行

插件定义：
1. input
2. pipeline (一系列处理器的组合)
   a.filter1 (argv1, argv2...)
   b.filter2
   c.filter3 (argv1, argv2, argv3...)
   d....
3. output

支持基于数据库的配置信息进行初始化，这里需要特别注意的是，
argv可以由output提供(但需要先算完)，从而形成了复杂的执行图

pipeline: data streaming (*)，filter基于spark实现

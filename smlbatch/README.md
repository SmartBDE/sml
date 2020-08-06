数据的批量处理

不同于pipeline，这部分的任务管理，是批量的方式进行的

处理任务的定义：
1. input
2. pipeline
3. output

计划支持以下类型的作业：
1. 基于SQL的数据处理
2. 基于机器学习算法的数据处理
3. 基于深度学习的训练与数据处理

作业的运行需要注意：
1. 执行任务的先后次序
2. 集群资源的调度管理

- smlbatch: (*)
  - core, {algorithm-controller, training-dataset, training-workflow, trained-model}
    - controller, manage the algorithm {metric, status, before-done, for-train, for-predict...}
    - dataset, define the strategy of data usage, how many for train, for test, data format verification
    - workflow, how the core works
    - model, if model is trained, save it to storage
  - algorithm {for train, for service-such as: adaboost multiple models}
    - define, interface define of algorithm
    - ...
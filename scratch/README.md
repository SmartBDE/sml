各种扩展算法的实现

- scratch: algorithm code for batch/stream-compute

实现的例子参考JavaLogisticRegression，由算法本身(被filter直接调用)实现分布式
比如，预测的算法，要实现批量的数据处理，则在每个节点都调用预测算法，同时有多个算法在执行，
又比如，在做回归的算法，则由多个节点同时启动梯度的计算

处理对象和算法本身，决定了如何实现的方法
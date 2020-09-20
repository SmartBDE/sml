package me.smartbde.sml.pipeline;

/**
 * 1. 创建spark session
 * 2. 注册udf
 * 3. 根据配置创建input, filter, output
 * 4. 运行(以input判断是stream或batch)
 * 4-1. stream
 *    a. 初始化input, filter, output
 *    b. 把input注册到TempView
 *    c. 启动并运行
 * 4-2. batch
 *    a. 按顺序执行
 *    b. 按需触发下一个任务
 */
public class StreamApplication {
}

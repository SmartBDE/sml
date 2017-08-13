package principality.me;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Logistic regression
 *
 * 算法简介：
 * 已知y和x的关系（线性相关）如下：y = w0+w1*x1+w2*x2+...+wm*xm
 * 对y做sigmoid处理，则：σ(y) = 1 / (1+exp(y))
 * 通过给出观测的y和x，计算w，确定y和x之间的关系模型，关系模型可用于给出新的x值，预测y值
 *
 * 使用文件sample_linear_regression_data.txt，限定维度为10，一共提供501组数据，
 * 可以使用10<n<501组数据作为w的计算依据，余下的作为预测测试集
 *
 * Created by win7 on 2017/8/13.
 */
public class JavaLRSample {
    private static final int D = 10;
    private static final Random rand = new Random(42);

    /**
     * run <file> <iters>
     * @param spark SparkSession
     * @param file
     */
    public double[] run(SparkSession spark, String file, int count) {
        JavaRDD<String> lines = spark.read().textFile(file).javaRDD();
        JavaRDD<DataPoint> points = lines.map(new ParsePoint()).cache();
        int ITERATIONS = count;

        // Initialize w to a random value
        double[] w = new double[D];
        for (int i = 0; i < D; i++) {
            w[i] = 2 * rand.nextDouble() - 1;
        }

        System.out.print("Init w: ");
        System.out.println(Arrays.toString(w));

        for (int i = 1; i <= ITERATIONS; i++) {
            System.out.println("On iteration " + i + ", job: " + (i - 1));

            double[] gradient = points.map(
                    new ComputeGradient(w) // 把梯度计算分布到节点上，考虑参数很多的情况，数据量很大的情况
            ).reduce(new VectorSum()); // 每一个map都把自己计算的梯度，提交到reduce上，reduce负责汇总

            for (int j = 0; j < D; j++) {
                w[j] -= gradient[j]; // 计算完以后，根据梯度减少权重，这个通常要提交到参数服务器？
            }
        }

        System.out.print("Final w: ");
        System.out.println(Arrays.toString(w));

        return w;
    }

    /**
     * 在节点上计算梯度下降值，输出double[]
     */
    static class ComputeGradient implements Function<DataPoint, double[]> {
        private final double[] weights;

        ComputeGradient(double[] weights) {
            this.weights = weights;
        }

        @Override
        public double[] call(DataPoint p) {
            double[] gradient = new double[D];
            for (int i = 0; i < D; i++) {
                double dot = dot(weights, p.x, D);
                gradient[i] = (1 / (1 + Math.exp(-p.y * dot)) - 1) * p.y * p.x[i];
            }
            return gradient;
        }
    }

    /**
     * 获得输入：梯度累积计算
     */
    static class VectorSum implements Function2<double[], double[], double[]> {
        @Override
        public double[] call(double[] a, double[] b) {
            double[] result = new double[D];
            for (int j = 0; j < D; j++) {
                result[j] = a[j] + b[j];
            }
            return result;
        }
    }

    /**
     * 用于表达每一对x和y，其中x[]是输入，y是输出
     */
    static class DataPoint implements Serializable {
        DataPoint(double[] x, double y) {
            this.x = x;
            this.y = y;
        }

        double[] x;
        double y;
    }

    /**
     * 用于解析每一行的输入，输入为字符串格式，以空格为分隔符，每个token的格式为index:number
     */
    static class ParsePoint implements Function<String, DataPoint> {
        private final Pattern SPACE = Pattern.compile(" ");

        @Override
        public DataPoint call(String line) {
            String[] tok = SPACE.split(line);
            double y = Double.parseDouble(tok[0]);
            double[] x = new double[D];
            for (int i = 0; i < D; i++) {
                String[] array = tok[i+1].split(":");
                x[i] = Double.parseDouble(array[1]);
            }
            return new DataPoint(x, y);
        }
    }

    /**
     * 点积计算
     * @param a
     * @param b
     * @param dimension
     * @return
     */
    static private double dot(double[] a, double[] b, int dimension) {
        double x = 0;
        for (int i = 0; i < dimension; i++) {
            x += a[i] * b[i];
        }
        return x;
    }
}

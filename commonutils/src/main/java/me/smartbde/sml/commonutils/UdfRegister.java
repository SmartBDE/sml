package me.smartbde.sml.commonutils;

import javafx.util.Pair;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.expressions.UserDefinedFunction;

import java.util.List;

public class UdfRegister {
    public static void findAndRegisterUdfs(SparkSession spark, ISQLUdfFilter filter) {
        List<Pair<String, UserDefinedFunction>> list = filter.getUdfList();
        for (Pair<String, UserDefinedFunction> pair : list) {
            String udfName = pair.getKey();
            UserDefinedFunction udfImpl = pair.getValue();
            spark.udf().register(udfName, udfImpl);
        }

        List<Pair<String, UserDefinedAggregateFunction>> alist = filter.getUdafList();
        for (Pair<String, UserDefinedAggregateFunction> pair : alist) {
            String udfName = pair.getKey();
            UserDefinedAggregateFunction udafImpl = pair.getValue();
            spark.udf().register(udfName, udafImpl);
        }
    }
}
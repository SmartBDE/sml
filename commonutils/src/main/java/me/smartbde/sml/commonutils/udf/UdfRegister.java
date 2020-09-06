package me.smartbde.sml.commonutils.udf;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.ISQLFilter;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.expressions.UserDefinedFunction;

import java.util.List;
import java.util.ServiceLoader;

public class UdfRegister {
    public static void findAndRegisterUdfs(SparkSession spark) {
        ServiceLoader<ISQLFilter> services = ServiceLoader.load(ISQLFilter.class);

        for (ISQLFilter f : services) {
            List<Pair<String, UserDefinedFunction>> list = f.getUdfList();
            for (Pair<String, UserDefinedFunction> pair : list) {
                String udfName = pair.getKey();
                UserDefinedFunction udfImpl = pair.getValue();
                spark.udf().register(udfName, udfImpl);
            }

            List<Pair<String, UserDefinedAggregateFunction>> alist = f.getUdafList();
            for (Pair<String, UserDefinedAggregateFunction> pair : alist) {
                String udfName = pair.getKey();
                UserDefinedAggregateFunction udafImpl = pair.getValue();
                spark.udf().register(udfName, udafImpl);
            }
        }
    }
}
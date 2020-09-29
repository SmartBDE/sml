package me.smartbde.sml.commonutils.plugins.filter;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IFilter;
import me.smartbde.sml.commonutils.ISession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明：对Dataset<Row>中的信息，用脚本进行处理，通常这发生在Dataset<Row>.map中
 * 格式输入要求：无
 *
 * 可以热加载的脚本，以及编译后不错的速度，是groovy的一大亮点
 */
public class GroovyScript extends AbstractPlugin implements IFilter {
    private GroovyClassLoader groovyClassLoader;
    private Logger logger = LoggerFactory.getLogger(GroovyScript.class);

    @Override
    public Dataset<Row> process(SparkSession spark, Dataset<Row> df, ISession session) {

        Map<Integer, String> map = new HashMap<>();
        map.put(0, "a");
        map.put(1, "b");
        map.put(2, "c");

        try {
            FileInputStream inputStream = new FileInputStream(properties.get("script"));
            int length = inputStream.available();
            byte bytes[] = new byte[length];
            inputStream.read(bytes);
            inputStream.close();
            String content = new String(bytes, StandardCharsets.UTF_8);
            Class groovyClass = groovyClassLoader.parseClass(content);

            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
            Object object = groovyObject.invokeMethod(properties.get("func"), map);
        } catch (IOException e) {
            logger.warn(e.toString());
        } catch (Exception e) {
            logger.warn(e.toString());
        }

        return null;
    }

    @Override
    public Pair<Boolean, String> checkConfig() {
        if (properties == null) {
            return new Pair<>(false, "missing config");
        }

        if (properties.get("script") != null
                && properties.get("func") != null
                && properties.get("schema") != null) {
            return new Pair<>(true, "");
        }

        return new Pair<>(false, "missing config");
    }

    @Override
    public String getName() {
        return "GroovyScript";
    }

    @Override
    public boolean prepare(SparkSession spark) {


        if (properties != null && checkConfig().getKey()) {
            try {
//                String separator = System.getProperty("file.separator");
//                String path = properties.get("script");
//                int index = path.lastIndexOf(separator);
//                String root = path.substring(0, index);
//                String file = path.substring(index+1, path.length());
                ClassLoader cl = new GroovyScript().getClass().getClassLoader();
                groovyClassLoader = new GroovyClassLoader(cl);
            } catch (Exception e) {
                logger.info(e.toString());
            }
            return true;
        }
        return false;
    }
}

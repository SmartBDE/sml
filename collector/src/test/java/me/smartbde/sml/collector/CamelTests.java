package me.smartbde.sml.collector;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import java.io.*;

public class CamelTests {
    @Test
    public void test() throws Exception {
        //创建Camel上下文
        DefaultCamelContext camelContext = new DefaultCamelContext();
        //添加一个路由，参数为路由建造者
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                this.from("file:..\\data\\mllib\\sample_linear_regression_data.txt").process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        GenericFile<File> gf = exchange.getIn().getBody(GenericFile.class);
                        File file = gf.getFile();
                        PrintStream ps = new PrintStream(System.out);
                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            ps.println(line);
                        }

                        ps.close();
                        br.close();
                    }
                }).to("file:..\\data\\mllib\\sample_linear_regression_data_out.txt");
            }
        });
        //启动上下文
        camelContext.start();
        //防止主线程退出
        Object object = new Object();
        synchronized (object) {
            object.wait();
        }
    }
}

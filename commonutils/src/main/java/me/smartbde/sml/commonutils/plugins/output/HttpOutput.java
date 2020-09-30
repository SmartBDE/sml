package me.smartbde.sml.commonutils.plugins.output;

import javafx.util.Pair;
import me.smartbde.sml.commonutils.AbstractPlugin;
import me.smartbde.sml.commonutils.IOutput;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class HttpOutput extends AbstractPlugin implements IOutput {
    private Logger logger = LoggerFactory.getLogger(HttpOutput.class);

    @Override
    public void process(Dataset<Row> df) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String url = String.format("http://%s:%s/", properties.get("host"), properties.get("port"));
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-type", "application/json; charset=utf-8");
            StringEntity entity = new StringEntity(df.toString(), Charset.forName("UTF-8"));
//            entity.setContentEncoding("UTF-8");
//            entity.setContentType("application/json");
            post.setEntity(entity);

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(post, responseHandler);
            logger.info(responseBody);
        } catch (Exception e) {
            logger.info(e.toString());
        }
    }

    /**
     * Return true and empty string if config is valid, return false and error message if config is invalid.
     */
    @Override
    public Pair<Boolean, String> checkConfig() {
        if (properties == null) {
            return new Pair<>(false, "missing config");
        }

        if (properties.get("host") != null
                && properties.get("port") != null) {
            return new Pair<>(true, "");
        }

        return new Pair<>(false, "missing config");
    }

    /**
     * Get Plugin Name.
     */
    @Override
    public String getName() {
        return "HttpOutput";
    }
}

package me.smartbde.sml.springtest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Add http data to local env
 */
@RunWith(SpringRunner.class)
public class HttpDataInput {
    @Test
    public void test() throws Exception {

        for (int i = 1; i < 10; i++) {
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpGet httpget = new HttpGet("http://127.0.0.1:8000/user/" + i);

                // Create a custom response handler
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
                String responseBody = httpclient.execute(httpget, responseHandler);
                System.out.println("----------------------------------------");
                System.out.println(responseBody);
            }
        }
    }
}

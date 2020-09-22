package me.smartbde.sml.admin.controller.utility;

import com.google.gson.Gson;
import me.smartbde.sml.admin.domain.model.JsonEvent;
import me.smartbde.sml.admin.domain.model.UserAction;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/utility")
public class DemoInputController {
    @Value("${application.utility.userinput.generationurl}")
    private String generationUrl;

    @RequestMapping("/demoinput")
    public String index(Model model) {
        return "/utility/demoinput";
    }

    @RequestMapping("/demoinput/submit")
    public void userAction() {
                /*
        StringEntity requestEntity = new StringEntity(
                JSON_STRING,
                ContentType.APPLICATION_JSON);

        HttpPost postMethod = new HttpPost("http://example.com/action");
        postMethod.setEntity(requestEntity);

        HttpResponse rawResponse = httpclient.execute(postMethod);
        */
        UserAction userAction = new UserAction("me", "buy", "item1", "test");
        JsonEvent event = new JsonEvent();
        event.body = userAction.toString();
        event.headers.put("a", "b");

        List<JsonEvent> events = new ArrayList<>(1);
        events.add(event);

        Gson g = new Gson();
        String jsonObj = (g.toJson(events));
        System.out.println(jsonObj);

        /*
        Person person = g.fromJson("{\"name\": \"John\"}", Person.class);
        System.out.println(person.name); //John
        */

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(generationUrl);
            post.setHeader("Content-type", "application/json; charset=utf-8");

            StringEntity entity = new StringEntity(jsonObj.toString(), Charset.forName("UTF-8"));
//            entity.setContentEncoding("UTF-8");
//            entity.setContentType("application/json");
            post.setEntity(entity);

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
            String responseBody = httpclient.execute(post, responseHandler);
            System.out.println(responseBody);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

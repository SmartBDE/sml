package me.smartbde.sml.admin.controller.utility;

import me.smartbde.sml.admin.domain.model.Message;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Controller
@RequestMapping("/utility")
@PropertySource("classpath:application.properties")
public class FakeInputController {
    @Value("${application.utility.fakeinput.generationurl}")
    private String generationUrl;

    @RequestMapping("/fakeinput")
    public String action(Model model) throws Exception {
        Message message = new Message();
        model.addAttribute("message", message);
        return "utility/fakeinput";
    }

    @RequestMapping("/fakeinput/submit")
    public String submit(@ModelAttribute(value="message") Message message, Model model) throws Exception {
//        for (int i = 1; i < 10; i++) {
//            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
//                HttpGet httpget = new HttpGet(generationUrl + i);
//
//                // Create a custom response handler
//                ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
//                    @Override
//                    public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
//                        int status = response.getStatusLine().getStatusCode();
//                        if (status >= 200 && status < 300) {
//                            HttpEntity entity = response.getEntity();
//                            return entity != null ? EntityUtils.toString(entity) : null;
//                        } else {
//                            throw new ClientProtocolException("Unexpected response status: " + status);
//                        }
//                    }
//                };
//                String responseBody = httpclient.execute(httpget, responseHandler);
//                System.out.println("----------------------------------------");
//                System.out.println(responseBody);
//            }
//        }
        model.addAttribute("head", "输入信息");
        model.addAttribute("msg", message.getValue());
        model.addAttribute("url", "/utility/fakeinput");
        return "info";
    }
}

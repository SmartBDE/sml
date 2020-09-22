package me.smartbde.sml.admin.controller.utility;

import com.google.gson.Gson;
import me.smartbde.sml.admin.domain.model.Message;
import me.smartbde.sml.admin.domain.model.UserInput;
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

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import me.smartbde.sml.admin.domain.model.JsonEvent;
import me.smartbde.sml.admin.domain.model.UserAction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/utility")
public class UserInputController {
    @RequestMapping("/userinput")
    public String userInput(Model model) {
        UserInput userInput = new UserInput();
        model.addAttribute("userinput", userInput);
        return "utility/userinput";
    }

    @RequestMapping("/userinput/submit")
    public String userInputSubmit(@ModelAttribute(value="userinput") UserInput userInput, Model model) {
        model.addAttribute("head", "执行结果");
        model.addAttribute("msg", "成功");
        model.addAttribute("url", "/utility/userinput");
        return "info";
    }
}
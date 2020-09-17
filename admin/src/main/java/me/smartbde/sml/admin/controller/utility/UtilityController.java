package me.smartbde.sml.admin.controller.utility;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/utility")
public class UtilityController {
    @RequestMapping("/index")
    public String getIndex(Model model) {
        return "utility/index";
    }

//    @RequestMapping("/fakeinput")
//    public String getFakeInput(Model model) {
//        return "utility/fakeinput";
//    }
//
//    @RequestMapping("/userinput")
//    public String getUserInput(Model model) {
//        return "utility/userinput";
//    }
}

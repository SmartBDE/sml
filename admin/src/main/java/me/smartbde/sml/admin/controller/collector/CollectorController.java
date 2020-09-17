package me.smartbde.sml.admin.controller.collector;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collector")
public class CollectorController {
    @RequestMapping("/index")
    public String getIndex(Model model) {
        return "collector/index";
    }
}

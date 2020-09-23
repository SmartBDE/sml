package me.smartbde.sml.admin.controller.monitor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/monitor")
public class MonitorController {
    @RequestMapping("/index")
    public String getIndex(Model model) {
        return "monitor/index";
    }
}

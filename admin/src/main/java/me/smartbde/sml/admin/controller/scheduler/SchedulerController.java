package me.smartbde.sml.admin.controller.scheduler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scheduler")
public class SchedulerController {
    @RequestMapping("/index")
    public String getIndex(Model model) {
        return "scheduler/index";
    }
}

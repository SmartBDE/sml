package me.smartbde.sml.admin.controller.monitor;

import me.smartbde.sml.admin.domain.model.JobLogger;
import me.smartbde.sml.admin.domain.model.StatInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/monitor")
public class MonitorController {
    @RequestMapping("/index")
    public String getIndex(Model model) {
        List<JobLogger> jobloggers = new ArrayList<JobLogger>();
        model.addAttribute("jobloggers", jobloggers);
        return "monitor/index";
    }

    @RequestMapping("/list")
    public String getList(Model model) {
        List<StatInfo> stats = new ArrayList<StatInfo>();
        model.addAttribute("stats", stats);
        return "monitor/list";
    }
}

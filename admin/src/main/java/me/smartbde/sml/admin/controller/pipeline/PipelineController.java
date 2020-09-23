package me.smartbde.sml.admin.controller.pipeline;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pipeline")
public class PipelineController {
    @RequestMapping("/index")
    public String getIndex(Model model) {
        return "pipeline/index";
    }
}

package me.smartbde.sml.admin.controller.entry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entry")
public class EntryController {
    @RequestMapping("/index")
    public String getIndex(Model model) {
        return "entry/index";
    }
}

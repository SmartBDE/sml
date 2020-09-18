package me.smartbde.sml.admin.controller.entry;

import me.smartbde.sml.admin.domain.model.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/entry")
public class EntryController {
    @RequestMapping("/index")
    public String getIndex(Model model) {
        List components = new ArrayList<Component>();
        Component component = new Component();
        component.setId(1);
        component.setName("monitor");
        component.setStatus("active");
        component.setDuration("1d 12h 55m");
        components.add(component);
        model.addAttribute("components", components);
        return "entry/index";
    }
}

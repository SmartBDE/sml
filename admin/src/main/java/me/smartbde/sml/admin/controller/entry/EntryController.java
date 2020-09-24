package me.smartbde.sml.admin.controller.entry;

import me.smartbde.sml.admin.domain.model.Component;
import me.smartbde.sml.admin.domain.model.Systems;
import me.smartbde.sml.admin.domain.repository.MySQLSystemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/entry")
public class EntryController {
    @Autowired
    MySQLSystemsRepository mySQLSystemsRepository;

    @RequestMapping("/index")
    public String getIndex(Model model) {
        List<Systems> systems = mySQLSystemsRepository.findAll();

        List components = new ArrayList<Component>();
        int count = 0;
        for (Systems sys : systems) {
            count++;

            Component component = new Component();
            component.setId(count);
            component.setName(sys.getName());
            component.setStatus("active");

            Date current = new Date();

            Calendar c1 = Calendar.getInstance();
            c1.setTime(sys.getStart());
            Calendar c2 = Calendar.getInstance();
            c2.setTime(current);

            int mins = c2.get(Calendar.MINUTE) - c1.get(Calendar.MINUTE);

            component.setDuration(String.format("%dm", mins));
            components.add(component);
        }

        model.addAttribute("components", components);
        return "entry/index";
    }
}

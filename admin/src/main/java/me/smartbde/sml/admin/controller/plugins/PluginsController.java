package me.smartbde.sml.admin.controller.plugins;

import me.smartbde.sml.admin.domain.model.Configs;
import me.smartbde.sml.admin.domain.model.PluginInfo;
import me.smartbde.sml.admin.domain.repository.MySQLPluginsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/plugins")
public class PluginsController {
    @Autowired
    MySQLPluginsRepository mySQLPluginsRepository;

    @RequestMapping("/index")
    public String getIndex(Model model) {
        List<PluginInfo> plugins = mySQLPluginsRepository.findAll();

        model.addAttribute("plugins", plugins);
        return "plugins/index";
    }
}

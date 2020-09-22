package me.smartbde.sml.admin.controller.scratch;

import me.smartbde.sml.admin.domain.model.Algorithm;
import me.smartbde.sml.admin.domain.model.PluginInfo;
import me.smartbde.sml.admin.domain.repository.MySQLAlgorithmsRepository;
import me.smartbde.sml.admin.domain.repository.MySQLPluginsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/scratch")
public class ScratchController {
    @Autowired
    MySQLAlgorithmsRepository mySQLAlgorithmsRepository;

    @RequestMapping("/index")
    public String getIndex(Model model) {
        List<Algorithm> algorithms = mySQLAlgorithmsRepository.findAll();

        model.addAttribute("algorithms", algorithms);
        return "scratch/index";
    }
}

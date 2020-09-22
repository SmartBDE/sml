package me.smartbde.sml.admin.controller.collector;

import me.smartbde.sml.admin.domain.model.Component;
import me.smartbde.sml.admin.domain.model.Configs;
import me.smartbde.sml.admin.domain.model.Endpoint;
import me.smartbde.sml.admin.domain.model.PluginInfo;
import me.smartbde.sml.admin.domain.repository.MySQLConfigsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/collector")
public class CollectorController {
    @Autowired
    MySQLConfigsRepository mySQLConfigsRepository;

    @RequestMapping("/index")
    public String getIndex(Model model) {
        List endpoints = new ArrayList<Endpoint>();

        List<Configs> configs = mySQLConfigsRepository.findByCnameAndCkeyLikeOrderByCkeyAsc("collector", "from%");

        if (!configs.isEmpty()) {
            Endpoint endpoint = new Endpoint();
            endpoint.setType("from");
            for (Configs config : configs) {
                if (config.getCkey().equals("from.host")) {
                    endpoint.setHost(config.getCvalue());
                }
                if (config.getCkey().equals("from.port")) {
                    endpoint.setPort(config.getCvalue());
                }
                if (config.getCkey().equals("from.protocol")) {
                    endpoint.setProtocol(config.getCvalue());
                }
            }
            endpoints.add(endpoint);
        }

        List<Configs> configs1 = mySQLConfigsRepository.findByCnameAndCkeyLikeOrderByCkeyAsc("collector", "to%");

        if (!configs1.isEmpty()) {
            Endpoint endpoint1 = new Endpoint();
            endpoint1.setType("to");
            for (Configs config : configs1) {
                if (config.getCkey().equals("to.host")) {
                    endpoint1.setHost(config.getCvalue());
                }
                if (config.getCkey().equals("to.port")) {
                    endpoint1.setPort(config.getCvalue());
                }
                if (config.getCkey().equals("to.protocol")) {
                    endpoint1.setProtocol(config.getCvalue());
                }
            }
            endpoints.add(endpoint1);
        }

        model.addAttribute("endpoints", endpoints);
        return "collector/index";
    }
}

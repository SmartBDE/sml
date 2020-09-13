package me.smartbde.sml.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import me.smartbde.sml.admin.domain.model.Person;
import me.smartbde.sml.admin.domain.repository.MySQLPersonRepository;
import me.smartbde.sml.admin.service.MongoServiceImpl;

/**
 * Created by win7 on 2017/7/23.
 */
@Controller
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private MySQLPersonRepository mysqlRepository;
    @Autowired
    private MongoServiceImpl mongoService;

    @RequestMapping(method = RequestMethod.GET)
    public String getPerson(Model model) {
        String msg = mysqlRepository.findPersonById(1L).toString();
        model.addAttribute("msg", msg);
        return "person/hello";
    }

    @RequestMapping("/mongo")
    String mongo(Model model) {
        String msg = mongoService.findById(3L).toString();
        model.addAttribute("msg", msg);
        return "person/hello";
    }

    @RequestMapping("/hello")
    String hello(Model model) {
        model.addAttribute("msg", "Hello Thymeleaf!");
        return "person/hello";
    }
}
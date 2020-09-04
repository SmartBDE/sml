package me.smartbde.sml.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import me.smartbde.sml.admin.domain.model.Person;
import me.smartbde.sml.admin.domain.repository.MySQLPersonRepository;
import me.smartbde.sml.admin.service.MongoServiceImpl;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by win7 on 2017/7/23.
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private MySQLPersonRepository mysqlRepository;
    @Autowired
    private MongoServiceImpl mongoService;

    @RequestMapping(method = RequestMethod.GET)
    public Person getPerson() {
        return mysqlRepository.findPersonById(1L);
    }

    @RequestMapping("/mongo")
    Person mongo() {
        return mongoService.findById(3L);
    }

    @RequestMapping("/hello")
    String hello() {
        return "Hello World!";
    }
}
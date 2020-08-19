package me.smartbde.sml.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import me.smartbde.sml.springtest.domain.model.Person;
import me.smartbde.sml.springtest.domain.repository.MySQLPersonRepository;
import me.smartbde.sml.springtest.service.MongoServiceImpl;
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
        return mongoService.findById(2L);
    }

    @RequestMapping("/hello")
    String hello() {
        return "Hello World!";
    }
}
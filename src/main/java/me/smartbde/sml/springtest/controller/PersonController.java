package me.smartbde.sml.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import me.smartbde.sml.springtest.domain.model.Person;
import me.smartbde.sml.springtest.domain.repository.MongoRepositoryImpl;

/**
 * Created by win7 on 2017/7/23.
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    private MongoRepositoryImpl repository;

    @Autowired
    public PersonController(MongoRepositoryImpl repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Person getPerson() {
        return repository.findById(4L);
    }
}

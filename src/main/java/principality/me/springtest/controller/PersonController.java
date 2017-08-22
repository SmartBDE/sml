package principality.me.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import principality.me.springtest.domain.model.Person;
import principality.me.springtest.service.MongoBean;

/**
 * Created by win7 on 2017/7/23.
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    private MongoBean repository;

    @Autowired
    public PersonController(MongoBean repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Person getPerson() {
        return repository.findById(4L);
    }
}

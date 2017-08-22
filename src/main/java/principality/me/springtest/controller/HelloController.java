package principality.me.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import principality.me.springtest.service.PersonService;

/**
 * Created by win7 on 2017/7/23.
 */
@RestController
public class HelloController {
    @Autowired
    private PersonService service;

    @RequestMapping("/")
    String home() {
        return "Hello Spring Boot!";
    }

    @RequestMapping("/hello")
    String hello() {
        return "Hello World!";
    }
}

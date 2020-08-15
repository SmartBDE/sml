package me.smartbde.sml.springtest.service;

import me.smartbde.sml.springtest.domain.repository.MongoPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.smartbde.sml.springtest.domain.model.Person;

/**
 * Created by win7 on 2017/7/23.
 */
@Service
public class MongoPersonServiceImpl implements PersonService {
    private final MongoPersonRepository repository;

    @Autowired
    MongoPersonServiceImpl(MongoPersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isTopOneUser(Long userId) {
        Person p = repository.findPersonById(userId);
        if (p.getName().equals("Jason")) {
            return true;
        }
        return false;
    }
}

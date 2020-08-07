package me.smartbde.sml.springtest.service;

import me.smartbde.sml.springtest.domain.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.smartbde.sml.springtest.domain.model.Person;

/**
 * Created by win7 on 2017/7/23.
 */
@Service
public class MongoPersonServiceImpl implements PersonService {
    private final PersonRepository repository;

    @Autowired
    MongoPersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person findUserById(Long userId) {
        return repository.findOne(userId);
    }
}

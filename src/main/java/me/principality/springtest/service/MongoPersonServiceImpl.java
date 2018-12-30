package me.principality.springtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.principality.springtest.domain.model.Person;
import me.principality.springtest.domain.repository.PersonRepository;

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

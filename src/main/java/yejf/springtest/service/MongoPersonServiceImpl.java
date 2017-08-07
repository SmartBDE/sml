package yejf.springtest.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yejf.springtest.domain.model.Person;
import yejf.springtest.domain.repository.PersonRepository;

import java.util.List;

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

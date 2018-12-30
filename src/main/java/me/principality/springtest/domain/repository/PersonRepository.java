package me.principality.springtest.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import me.principality.springtest.domain.model.Person;

/**
 * Created by win7 on 2017/7/23.
 */
@Repository
public interface PersonRepository extends MongoRepository<Person, Long> {
    Person findPersonById(Long userId);
}

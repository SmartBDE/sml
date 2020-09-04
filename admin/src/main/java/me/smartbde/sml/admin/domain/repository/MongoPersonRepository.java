package me.smartbde.sml.admin.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import me.smartbde.sml.admin.domain.model.Person;

/**
 * Created by win7 on 2017/7/23.
 */
@Repository
public interface MongoPersonRepository extends MongoRepository<Person, Long> {
    Person findPersonById(Long userId);
}

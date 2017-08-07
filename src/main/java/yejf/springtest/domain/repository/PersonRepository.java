package yejf.springtest.domain.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import yejf.springtest.domain.model.Person;

import java.util.List;

/**
 * Created by win7 on 2017/7/23.
 */
@Repository
public interface PersonRepository extends MongoRepository<Person, Long> {
    Person findPersonById(Long userId);
}

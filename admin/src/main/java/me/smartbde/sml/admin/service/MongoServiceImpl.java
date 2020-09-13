package me.smartbde.sml.admin.service;

import com.mongodb.*;
import me.smartbde.sml.admin.domain.repository.MongoPersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;
import me.smartbde.sml.admin.domain.model.Person;

@Component
public class MongoServiceImpl {
    private MongoPersonRepository mongoPersonRepository;
    private final MongoDbFactory mongo;
    private static final Logger logger = LoggerFactory.getLogger(MongoServiceImpl.class);

    @Autowired
    public MongoServiceImpl(MongoDbFactory mongo) {
        this.mongo = mongo;
    }

    public void init(Long id) {
        DB db = mongo.getDb();

        //查询所有的聚集集合
        for (String name : db.getCollectionNames()) {
            logger.debug("collectionName: " + name);
        }

        DBCollection persons = db.getCollection("person");
        DBCursor cur = persons.find(); // 查询所有的数据

        boolean found = false;
        if (cur.count() > 0) {
            while (cur.hasNext()) {
                DBObject p = cur.next();
                logger.debug(p.toString());
                if (Integer.parseInt(p.get("_id").toString()) == id) {
                    found = true;
                }
            }
        }
        if (!found) {
            DBObject person = new BasicDBObject();
            person.put("id", id);
            person.put("name", "hoojo");

            persons.save(person);
        }
    }

    public Person findById(Long id) {
        init(id);
        return mongoPersonRepository.findPersonById(id);
    }
}
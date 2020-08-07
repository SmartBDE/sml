package me.smartbde.sml.springtest.service;

import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;
import me.smartbde.sml.springtest.domain.model.Person;

@Component
public class MongoBean {
    private final MongoDbFactory mongo;
    private static final Logger logger = LoggerFactory.getLogger(MongoBean.class);

    @Autowired
    public MongoBean(MongoDbFactory mongo) {
        this.mongo = mongo;
    }

    public void init() {
        DB db = mongo.getDb();

        DBObject person = new BasicDBObject();
        person.put("id", 4);
        person.put("name", "hoojo");

        //查询所有的聚集集合
        for (String name : db.getCollectionNames()) {
            logger.debug("collectionName: " + name);
        }

        DBCollection persons = db.getCollection("person");
        DBCursor cur = persons.find(); // 查询所有的数据
        while (cur.hasNext()) {
            logger.debug(cur.next().toString());
        }
    }

    public Person findById(Long id) {
        return new Person(id, "a");
    }
}
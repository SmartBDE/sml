package me.smartbde.sml.admin;

import me.smartbde.sml.admin.domain.model.Person;
import me.smartbde.sml.admin.domain.repository.MySQLPersonRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MySQLPersonRepositoryTests {
    @Autowired
    private MySQLPersonRepository repository;

    @Test
    public void test() throws Exception {
        Assert.assertEquals(2, repository.findAll().size());

        // 查找一个User，再验证User的名字
        Person u = repository.findOne(1L);
        Assert.assertEquals("Jason", u.getName());

        // 查找一个User，再验证User的名字
        u = repository.findPersonById(2L);
        Assert.assertEquals("Jacky", u.getName());
    }
}

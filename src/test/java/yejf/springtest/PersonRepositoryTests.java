package yejf.springtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import yejf.springtest.domain.model.Person;
import yejf.springtest.domain.repository.PersonRepository;

/**
 * Created by win7 on 2017/7/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // MUST
public class PersonRepositoryTests {
    @Autowired
    private PersonRepository repository;

    @Before
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void test() throws Exception {
        // 创建三个User，并验证User总数
        repository.save(new Person(1L, "didi"));
        repository.save(new Person(2L, "mama"));
        repository.save(new Person(3L, "kaka"));
        Assert.assertEquals(3, repository.findAll().size());

        // 删除一个User，再验证User总数
        Person u = repository.findOne(1L);
        repository.delete(u);
        Assert.assertEquals(2, repository.findAll().size());

        // 删除一个User，再验证User总数
        u = repository.findPersonById(2L);
        repository.delete(u);
        Assert.assertEquals(1, repository.findAll().size());
    }
}

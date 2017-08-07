package yejf.springtest.service;

import yejf.springtest.domain.model.Person;

import java.util.List;

/**
 * Created by win7 on 2017/7/23.
 */
public interface PersonService {
    Person findUserById(Long userId);
}

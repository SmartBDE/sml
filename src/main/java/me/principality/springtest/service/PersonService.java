package me.principality.springtest.service;

import me.principality.springtest.domain.model.Person;

/**
 * Created by win7 on 2017/7/23.
 */
public interface PersonService {
    Person findUserById(Long userId);
}

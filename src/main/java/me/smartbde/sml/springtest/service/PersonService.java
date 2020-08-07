package me.smartbde.sml.springtest.service;

import me.smartbde.sml.springtest.domain.model.Person;

/**
 * Created by win7 on 2017/7/23.
 */
public interface PersonService {
    Person findUserById(Long userId);
}

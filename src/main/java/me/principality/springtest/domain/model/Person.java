package me.principality.springtest.domain.model;

import org.springframework.data.annotation.Id;

/**
 * Created by win7 on 2017/7/23.
 */
public class Person {
    @Id
    private Long id;
    private String name;

    public Person(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

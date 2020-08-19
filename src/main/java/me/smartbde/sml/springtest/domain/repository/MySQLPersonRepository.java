package me.smartbde.sml.springtest.domain.repository;

import me.smartbde.sml.springtest.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySQLPersonRepository extends JpaRepository<Person, Long> {
    Person findPersonById(Long userId);
}

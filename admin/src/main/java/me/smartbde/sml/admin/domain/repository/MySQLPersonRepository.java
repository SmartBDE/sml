package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySQLPersonRepository extends JpaRepository<Person, Long> {
    Person findPersonById(Long userId);
}

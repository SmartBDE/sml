package me.smartbde.sml.smlbatch.domain.repository;

import me.smartbde.sml.smlbatch.domain.model.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySQLSchedulesRepository extends JpaRepository<Schedules, Integer> {
}

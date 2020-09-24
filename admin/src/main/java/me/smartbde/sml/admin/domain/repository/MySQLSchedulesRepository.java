package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySQLSchedulesRepository extends JpaRepository<Schedules, String> {
}

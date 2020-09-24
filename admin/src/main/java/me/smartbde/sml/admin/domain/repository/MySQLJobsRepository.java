package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MySQLJobsRepository extends JpaRepository<Jobs, Integer> {
    List<Jobs> findByName(String name);
}


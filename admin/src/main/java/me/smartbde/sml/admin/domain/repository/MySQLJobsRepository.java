package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MySQLJobsRepository extends JpaRepository<Jobs, Integer> {
    List<Jobs> findByName(String name);
    List<Jobs> findByNameLike(String name);
    List<Jobs> findByPluginLike(String plugin);
    List<Jobs> findByNameOrderByPriorityAsc(String name);
}


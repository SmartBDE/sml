package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.Streamings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySQLStreamingsRepository extends JpaRepository<Streamings, String> {
}

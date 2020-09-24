package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.Systems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySQLSystemsRepository extends JpaRepository<Systems, String> {
}

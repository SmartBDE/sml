package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.Algorithm;
import me.smartbde.sml.admin.domain.model.Configs;
import me.smartbde.sml.admin.domain.model.ConfigsCompositeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MySQLAlgorithmsRepository extends JpaRepository<Algorithm, ConfigsCompositeId> {
}

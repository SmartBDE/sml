package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.StatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySQLStatInfoRepository extends JpaRepository<StatInfo, Integer> {
}

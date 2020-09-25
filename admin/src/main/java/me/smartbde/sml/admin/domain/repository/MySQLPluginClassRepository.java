package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.PluginClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySQLPluginClassRepository extends JpaRepository<PluginClass, String> {
}

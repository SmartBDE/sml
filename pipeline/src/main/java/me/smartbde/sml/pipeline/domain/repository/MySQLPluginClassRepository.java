package me.smartbde.sml.pipeline.domain.repository;

import me.smartbde.sml.pipeline.domain.model.PluginClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySQLPluginClassRepository extends JpaRepository<PluginClass, String> {
}

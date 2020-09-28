package me.smartbde.sml.smlbatch.domain.repository;

import me.smartbde.sml.smlbatch.domain.model.PluginClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySQLPluginClassRepository extends JpaRepository<PluginClass, String> {
}

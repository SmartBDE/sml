package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.PluginCompositeId;
import me.smartbde.sml.admin.domain.model.PluginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MySQLPluginsRepository extends JpaRepository<PluginInfo, PluginCompositeId> {
    List<PluginInfo> findByPlugin(String plugin);
    List<PluginInfo> findByCkey(String ckey);
    List<PluginInfo> findByCvalue(String cvalue);
    List<PluginInfo> findByPluginLike(String plugin);
}

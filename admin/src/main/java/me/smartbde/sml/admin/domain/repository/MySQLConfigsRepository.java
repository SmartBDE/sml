package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.Configs;
import me.smartbde.sml.admin.domain.model.ConfigsCompositeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MySQLConfigsRepository extends JpaRepository<Configs, ConfigsCompositeId> {
    List<Configs> findByCname(String cname);
    List<Configs> findByCkey(String ckey);
    List<Configs> findByCvalue(String cvalue);
    List<Configs> findByCnameOrderByCkeyAsc(String cname);
    List<Configs> findByCnameAndCkeyLikeOrderByCkeyAsc(String cname, String ckey);
}

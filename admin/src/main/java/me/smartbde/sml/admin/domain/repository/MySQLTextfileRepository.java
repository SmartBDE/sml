package me.smartbde.sml.admin.domain.repository;

import me.smartbde.sml.admin.domain.model.Textfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySQLTextfileRepository extends JpaRepository<Textfile, Integer> {
}

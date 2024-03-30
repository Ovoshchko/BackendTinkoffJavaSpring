package edu.java.scrapper.repository.jpa.dao;

import edu.java.scrapper.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface LinkDao extends JpaRepository<Link, Long> {

    Collection<Link> findByLastCheckBefore(LocalDateTime borderTime);

    Link findByLink(String url);

    void deleteByLink(String link);
}

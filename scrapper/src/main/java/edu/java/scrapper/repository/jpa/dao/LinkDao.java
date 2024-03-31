package edu.java.scrapper.repository.jpa.dao;

import edu.java.scrapper.model.Link;
import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkDao extends JpaRepository<Link, Long> {

    Collection<Link> findByLastCheckBefore(LocalDateTime borderTime);

    Link findByLink(String url);

    void deleteByLink(String link);
}

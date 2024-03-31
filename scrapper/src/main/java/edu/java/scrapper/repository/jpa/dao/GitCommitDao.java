package edu.java.scrapper.repository.jpa.dao;

import edu.java.scrapper.model.GitCommit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitCommitDao extends JpaRepository<GitCommit, String> {
}

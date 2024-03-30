package edu.java.scrapper.repository.jpa.dao;

import edu.java.scrapper.model.StackoverflowAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;

@Repository
public interface StackoverflowAnswerDao extends JpaRepository<StackoverflowAnswer, Long> {
    List<StackoverflowAnswer> findAllByQuestionId(long questionId);
}

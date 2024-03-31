package edu.java.scrapper.repository.jpa.dao;

import edu.java.scrapper.model.StackoverflowAnswer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StackoverflowAnswerDao extends JpaRepository<StackoverflowAnswer, Long> {
    List<StackoverflowAnswer> findAllByQuestionId(long questionId);
}

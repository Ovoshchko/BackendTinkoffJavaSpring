package edu.java.scrapper.repository;

import edu.java.scrapper.model.StackoverflowAnswer;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface StackoverflowAnswerRepository {
    String QUESTION_ID_NAME = "question_id";
    String ANSWER_ID_NAME = "answer_id";
    String NAME_NAME = "name";

    @Transactional
    Integer addAnswer(StackoverflowAnswer answer);

    List<StackoverflowAnswer> getAnswerByQuestionId(long questionId);
}

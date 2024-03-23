package edu.java.scrapper.repository;

import edu.java.scrapper.dto.stackoverflow.Answer;
import java.util.List;

public interface StackoverflowAnswerRepository {
    String QUESTION_ID_NAME = "question_id";
    String ANSWER_ID_NAME = "answer_id";
    String NAME_NAME = "name";

    Integer addAnswer(Answer answer);

    List<Answer> getAnswerByQuestionId(long questionId);
}

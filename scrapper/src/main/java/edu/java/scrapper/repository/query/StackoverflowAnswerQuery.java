package edu.java.scrapper.repository.query;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class StackoverflowAnswerQuery {
    private final String getAnswerByQuestionId = "SELECT * FROM stackoverflowanswers WHERE (question_id = ?);";
    private final String getAnswerByAnswerAndQuestionId =
        "SELECT * FROM stackoverflowanswers WHERE (answer_id = ?) AND (question_id = ?);";
    private final String addAnswer =
        "INSERT INTO stackoverflowanswers (name, answer_id, question_id) VALUES (?, ?, ?);";

}

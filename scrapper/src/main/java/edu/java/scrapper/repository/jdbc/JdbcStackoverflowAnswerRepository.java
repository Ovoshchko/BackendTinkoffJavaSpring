package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.dto.stackoverflow.Answer;
import edu.java.scrapper.repository.StackoverflowAnswerRepository;
import edu.java.scrapper.repository.query.StackoverflowAnswerQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcStackoverflowAnswerRepository implements StackoverflowAnswerRepository {

    private final StackoverflowAnswerQuery stackoverflowAnswerQuery;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Integer addAnswer(Answer answer) {
        List<Answer> existingAnswer = getAnswerByAnswerAndQuestionId(answer.answerId(), answer.questionId());
        if ((existingAnswer != null) && (!existingAnswer.isEmpty())) {
            return 1;
        }
        return jdbcTemplate.update(
            stackoverflowAnswerQuery.getAddAnswer(),
            answer.owner().displayName(),
            answer.answerId(),
            answer.questionId()
        );
    }

    @Override
    public List<Answer> getAnswerByQuestionId(long questionId) {
        return jdbcTemplate.query(
            con -> {
                PreparedStatement statement = con.prepareStatement(stackoverflowAnswerQuery.getGetAnswerByQuestionId());
                statement.setLong(1, questionId);
                return statement;
            },
            (ResultSet resultSet, int rowNum) -> new Answer(
                new Answer.Owner(resultSet.getString(NAME_NAME)),
                resultSet.getLong(ANSWER_ID_NAME),
                resultSet.getLong(QUESTION_ID_NAME)
            )
        );
    }

    private List<Answer> getAnswerByAnswerAndQuestionId(long answerId, long questionId) {
        return jdbcTemplate.query(
            con -> {
                PreparedStatement statement =
                    con.prepareStatement(stackoverflowAnswerQuery.getGetAnswerByAnswerAndQuestionId());
                statement.setLong(1, answerId);
                statement.setLong(2, questionId);
                return statement;
            },
            (ResultSet resultSet, int rowNum) -> new Answer(
                new Answer.Owner(resultSet.getString(NAME_NAME)),
                resultSet.getLong(ANSWER_ID_NAME),
                resultSet.getLong(QUESTION_ID_NAME)
            )
        );
    }
}

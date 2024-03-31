package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.model.StackoverflowAnswer;
import java.sql.ResultSet;
import java.util.List;
import edu.java.scrapper.repository.StackoverflowAnswerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StackoverflowAnswerRepositoryTest extends IntegrationTest {

    private final static String NAME = "name";
    private final static long QUESTION_ID = 123;
    private final static long ANSWER_ID = 12;
    private final static StackoverflowAnswer ANSWER = new StackoverflowAnswer().setName(NAME).setAnswerId(ANSWER_ID)
        .setQuestionId(QUESTION_ID);
    @Autowired
    private List<StackoverflowAnswerRepository> stackoverflowAnswerRepositories;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Rollback
    void addAnswer() {
        for (StackoverflowAnswerRepository repository : stackoverflowAnswerRepositories) {
            jdbcTemplate.update("delete from stackoverflowanswers;");

            repository.addAnswer(ANSWER);

            List<StackoverflowAnswer> answers = jdbcTemplate.query(
                "select * from stackoverflowanswers;",
                (ResultSet resultSet, int rowNum) -> new StackoverflowAnswer()
                    .setName(resultSet.getString("name"))
                    .setAnswerId(resultSet.getLong("answer_id"))
                    .setQuestionId(resultSet.getLong("question_id"))
            );

            assertEquals(1, answers.size());
            assertThat(answers.get(0)).isEqualTo(ANSWER);
        }
    }

    @Test
    @Rollback
    void getAnswerByQuestionId() {
        jdbcTemplate.update(
            "insert into stackoverflowanswers (name, answer_id, question_id) values (?, ?, ?);",
            NAME,
            ANSWER_ID,
            QUESTION_ID
        );

        for (StackoverflowAnswerRepository repository : stackoverflowAnswerRepositories) {
            List<StackoverflowAnswer> answers = repository.getAnswerByQuestionId(QUESTION_ID);

            assertEquals(1, answers.size());
            assertThat(answers.get(0)).isEqualTo(ANSWER);
        }
    }
}

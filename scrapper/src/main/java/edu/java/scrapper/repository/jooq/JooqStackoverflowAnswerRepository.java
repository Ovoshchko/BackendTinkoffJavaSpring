package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.domain.jooq.linkviewer.Tables;
import edu.java.scrapper.dto.stackoverflow.Answer;
import edu.java.scrapper.model.StackoverflowAnswer;
import edu.java.scrapper.repository.StackoverflowAnswerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.domain.jooq.linkviewer.Tables.STACKOVERFLOWANSWERS;

@Repository
@RequiredArgsConstructor
public class JooqStackoverflowAnswerRepository implements StackoverflowAnswerRepository {

    private final DSLContext dsl;

    @Override
    public Integer addAnswer(StackoverflowAnswer answer) {
        return dsl.insertInto(STACKOVERFLOWANSWERS)
            .set(STACKOVERFLOWANSWERS.NAME, answer.getName())
            .set(STACKOVERFLOWANSWERS.ANSWER_ID, answer.getAnswerId())
            .set(STACKOVERFLOWANSWERS.QUESTION_ID, answer.getQuestionId())
            .execute();
    }

    @Override
    public List<StackoverflowAnswer> getAnswerByQuestionId(long questionId) {
        return dsl.selectFrom(STACKOVERFLOWANSWERS)
            .where(STACKOVERFLOWANSWERS.QUESTION_ID.in(questionId))
            .fetchInto(StackoverflowAnswer.class);
    }
}

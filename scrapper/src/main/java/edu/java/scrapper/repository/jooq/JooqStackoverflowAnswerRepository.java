package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.domain.jooq.linkviewer.Tables;
import edu.java.scrapper.dto.stackoverflow.Answer;
import edu.java.scrapper.repository.StackoverflowAnswerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JooqStackoverflowAnswerRepository implements StackoverflowAnswerRepository {

    private final DSLContext dsl;

    @Override
    public Integer addAnswer(Answer answer) {
        return dsl.insertInto(Tables.STACKOVERFLOWANSWERS)
            .set(Tables.STACKOVERFLOWANSWERS.NAME, answer.owner().displayName())
            .set(Tables.STACKOVERFLOWANSWERS.ANSWER_ID, answer.answerId())
            .set(Tables.STACKOVERFLOWANSWERS.QUESTION_ID, answer.questionId())
            .execute();
    }

    @Override
    public List<Answer> getAnswerByQuestionId(long questionId) {
        return dsl.selectFrom(Tables.STACKOVERFLOWANSWERS)
            .where(Tables.STACKOVERFLOWANSWERS.QUESTION_ID.in(questionId))
            .fetchInto(Answer.class);
    }
}

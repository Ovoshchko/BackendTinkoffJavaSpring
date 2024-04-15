package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.StackoverflowAnswer;
import edu.java.scrapper.repository.StackoverflowAnswerRepository;
import edu.java.scrapper.repository.jpa.dao.StackoverflowAnswerDao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JpaStackoverflowAnswerRepository implements StackoverflowAnswerRepository {

    private final StackoverflowAnswerDao stackoverflowAnswerDao;

    @Override
    public Integer addAnswer(StackoverflowAnswer answer) {
        stackoverflowAnswerDao.saveAndFlush(answer);
        return 1;
    }

    @Override
    public List<StackoverflowAnswer> getAnswerByQuestionId(long questionId) {
        return stackoverflowAnswerDao.findAllByQuestionId(questionId);
    }
}

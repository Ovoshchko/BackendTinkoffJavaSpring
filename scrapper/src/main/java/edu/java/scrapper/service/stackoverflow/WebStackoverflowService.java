package edu.java.scrapper.service.stackoverflow;

import edu.java.scrapper.clients.stackoverflow.StackoverflowClient;
import edu.java.scrapper.dto.stackoverflow.Answer;
import edu.java.scrapper.dto.stackoverflow.ListAnswer;
import edu.java.scrapper.dto.stackoverflow.StackoverflowResponse;
import edu.java.scrapper.repository.StackoverflowAnswerRepository;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebStackoverflowService implements StackoverflowService {

    public static final String UNKNOWN_UPDATE = "Пришло обновление, но я не знаю какое(";
    public static final String NOT_ANSWERS_UPDATE = "Что-то произошло. Но это было не в ответах";
    public static final String BAD_LINK = "Плохая ссылка";
    private final StackoverflowClient stackoverflowWebClient;
    private final StackoverflowAnswerRepository jdbcStackoverflowAnswerRepository;

    @Override
    public List<String> checkForUpdates(URI url, LocalDateTime time) {
        List<String> description = new ArrayList<>();
        try {
            long questionId = getQuestionId(url);

            StackoverflowResponse stackoverflowResponse = stackoverflowWebClient.fetchUpdate(questionId);

            if (time.isBefore(stackoverflowResponse.items().get(0).lastActivityDate().toLocalDateTime())) {
                description = List.of(UNKNOWN_UPDATE);

                ListAnswer answer = stackoverflowWebClient.checkForAnswers(questionId);

                if ((answer.answers() != null) && (!answer.answers().isEmpty())) {
                    description = processAnswers(answer.answers(), questionId);
                }

                if (description.isEmpty()) {
                    description = List.of(NOT_ANSWERS_UPDATE);
                }
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            description = List.of(url + " " + BAD_LINK);
        }

        return description;
    }

    private List<String> processAnswers(List<Answer> answers, long questionId) {
        List<String> stringAnswers = new ArrayList<>();
        List<Answer> existingAnswers = jdbcStackoverflowAnswerRepository.getAnswerByQuestionId(questionId);
        if (existingAnswers.size() < answers.size()) {
            for (Answer answer : answers) {
                if (!existingAnswers.contains(answer)) {
                    jdbcStackoverflowAnswerRepository.addAnswer(answer);
                    stringAnswers.add("Появился новый ответ от пользователя " + answer.owner().displayName()
                        + System.lineSeparator());
                }
            }
        }
        return stringAnswers;
    }

    private long getQuestionId(URI url) {
        String[] pathParts = url.getPath().split("/");
        return Long.parseLong(pathParts[pathParts.length - 2]);
    }
}

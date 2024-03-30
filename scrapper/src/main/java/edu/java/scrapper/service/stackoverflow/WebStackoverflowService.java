package edu.java.scrapper.service.stackoverflow;

import edu.java.scrapper.clients.stackoverflow.StackoverflowClient;
import edu.java.scrapper.dto.stackoverflow.Answer;
import edu.java.scrapper.dto.stackoverflow.ListAnswer;
import edu.java.scrapper.dto.stackoverflow.StackoverflowResponse;
import edu.java.scrapper.model.StackoverflowAnswer;
import edu.java.scrapper.repository.StackoverflowAnswerRepository;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class WebStackoverflowService implements StackoverflowService {

    private final StackoverflowClient stackoverflowWebClient;
    private final StackoverflowAnswerRepository stackoverflowAnswerRepository;

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
                    description = processAnswers(getStackOverflowAnswerFromDto(answer.answers()), questionId);
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

    private List<String> processAnswers(List<StackoverflowAnswer> answers, long questionId) {
        List<String> stringAnswers = new ArrayList<>();
        List<StackoverflowAnswer> existingAnswers = stackoverflowAnswerRepository.getAnswerByQuestionId(questionId);
        if (existingAnswers.size() < answers.size()) {
            for (StackoverflowAnswer answer : answers) {
                if (!existingAnswers.contains(answer)) {
                    stackoverflowAnswerRepository.addAnswer(answer);
                    stringAnswers.add("Появился новый ответ от пользователя " + answer.getName()
                        + System.lineSeparator());
                }
            }
        }
        return stringAnswers;
    }

    private List<StackoverflowAnswer> getStackOverflowAnswerFromDto(List<Answer> answers) {
        return answers.stream()
            .map(answer -> new StackoverflowAnswer().setAnswerId(answer.answerId())
                .setName(answer.owner().displayName())
                .setQuestionId(answer.questionId()))
            .toList();
    }

    private long getQuestionId(URI url) {
        String[] pathParts = url.getPath().split("/");
        return Long.parseLong(pathParts[pathParts.length - 2]);
    }
}

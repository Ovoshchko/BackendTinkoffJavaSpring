package edu.java.scrapper.service.stackoverflow;

import edu.java.scrapper.clients.stackoverflow.StackoverflowWebClient;
import edu.java.scrapper.dto.stackoverflow.Answer;
import edu.java.scrapper.dto.stackoverflow.ListAnswer;
import edu.java.scrapper.dto.stackoverflow.StackoverflowResponse;
import edu.java.scrapper.repository.StackoverflowAnswerRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebStackoverflowServiceTest {

    @Mock
    private StackoverflowWebClient stackoverflowWebClient;
    @Mock
    private StackoverflowAnswerRepository stackoverflowAnswerRepository;
    @InjectMocks
    private WebStackoverflowService stackoverflowService;

    public static Stream<Arguments> provideArgs() {
        return Stream.of(
            Arguments.of(
                URI.create(
                    "https://stackoverflow.com/questions/52020732/how-to-imitate-sql-query-in-spring-data-using-query"),
                LocalDateTime.now(),
                new StackoverflowResponse(
                    List.of(new StackoverflowResponse.QuestionResponse(
                        123L,
                        "https://stackoverflow.com/questions/52020732/how-to-imitate-sql-query-in-spring-data-using-query",
                        OffsetDateTime.now().minusMinutes(5),
                        OffsetDateTime.now().minusMinutes(5)
                    ))
                ),
                new ListAnswer(new ArrayList<>()),
                new ArrayList<>()
            ),
            Arguments.of(
                URI.create(
                    "https://stackoverflow.com/questions/52020732/how-to-imitate-sql-query-in-spring-data-using-query"),
                LocalDateTime.now().minusMinutes(5),
                new StackoverflowResponse(
                    List.of(new StackoverflowResponse.QuestionResponse(
                        123L,
                        "https://stackoverflow.com/questions/52020732/how-to-imitate-sql-query-in-spring-data-using-query",
                        OffsetDateTime.now().minusMinutes(5),
                        OffsetDateTime.now()
                    ))
                ),
                new ListAnswer(List.of(new Answer(new Answer.Owner("Ovoshch"), 12, 123))),
                List.of("Появился новый ответ от пользователя Ovoshch" + System.lineSeparator())
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgs")
    void checkForUpdates(
        URI url,
        LocalDateTime time,
        StackoverflowResponse stackoverflowResponse,
        ListAnswer answers,
        List<String> description
    ) {
        String[] pathParts = url.getPath().split("/");

        long id = Long.parseLong(pathParts[pathParts.length - 2]);

        when(stackoverflowWebClient.fetchUpdate(id)).thenReturn(stackoverflowResponse);
        if (!answers.answers().isEmpty()) {
            when(stackoverflowWebClient.checkForAnswers(id)).thenReturn(answers);
        }

        List<String> serviceResponse = stackoverflowService.checkForUpdates(url, time);

        assertThat(serviceResponse).isEqualTo(description);
    }
}

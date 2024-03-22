package edu.java.scrapper.scheduler;

import edu.java.scrapper.configuration.SchedulerParams;
import edu.java.scrapper.dto.stackoverflow.StackoverflowResponse;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.service.github.GitService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class LinkUpdateScheduler {

    public static final String UPDATED = "Пришло обновление!";
    public static final String GITHUB_HOST = "github.com";
    public static final String STACKOVERFLOW_HOST = "stackoverflow.com";
    private final GitService webGitService;
    private final SchedulerParams params;

    @Scheduled(fixedDelayString = "#{@getScheduler.interval.toMillis()}")
    public void update() {
        List<Long> users;
        List<Link> links =
            params.getJdbcLinkRepository()
                .findLinksUpdatedMoreThanNMinutesAgo(params.getScheduler().interval().toMinutes())
                .stream().toList();

        for (Link link : links) {
            users = params.getJdbcUserLinkRepository().getAllUsersByLink(link).stream().toList();
            URI uri = URI.create(link.getLink());
            if (GITHUB_HOST.equals(uri.getHost())) {
                List<String> description =
                    webGitService.checkForUpdates(uri, LocalDateTime.now().minus(params.getScheduler().interval()));
                if (!description.isEmpty()) {
                    params.getBotService().postUpdate(
                        link.getId(),
                        uri,
                        description,
                        users
                    );
                }
            } else if (STACKOVERFLOW_HOST.equals(uri.getHost())) {
                StackoverflowResponse response = params.getStackoverflowWebClient().checkForUpdates(uri);
                if (LocalDateTime.now().minus(params.getScheduler().interval())
                    .isBefore(response.items().get(0).lastActivityDate().toLocalDateTime())) {
                    params.getBotService().postUpdate(
                        link.getId(),
                        uri,
                        List.of(UPDATED),
                        users
                    );
                }
            }
        }
    }
}

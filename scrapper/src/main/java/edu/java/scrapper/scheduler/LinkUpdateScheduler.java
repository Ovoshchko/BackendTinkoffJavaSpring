package edu.java.scrapper.scheduler;

import edu.java.scrapper.configuration.SchedulerParams;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.service.github.GitService;
import edu.java.scrapper.service.stackoverflow.StackoverflowService;
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
    private final StackoverflowService webStackoverflowService;
    private final SchedulerParams params;

    @Scheduled(fixedDelayString = "#{@getScheduler.interval.toMillis()}")
    public void update() {
        List<Long> users;
        List<Link> links =
            params.getLinkRepository()
                .findLinksUpdatedMoreThanNMinutesAgo(params.getScheduler().interval().toMinutes())
                .stream().toList();

        for (Link link : links) {
            users = params.getUserLinkRepository().getAllUsersByLink(link).stream().toList();
            URI url = URI.create(link.getLink());
            if (GITHUB_HOST.equals(url.getHost())) {
                List<String> description =
                    webGitService.checkForUpdates(
                        url,
                        LocalDateTime.now().minus(params.getScheduler().forceCheckDelay())
                    );
                if (!description.isEmpty()) {
                    params.getBotService().postUpdate(
                        link.getId(),
                        url,
                        description,
                        users
                    );
                }
            } else if (STACKOVERFLOW_HOST.equals(url.getHost())) {
                List<String> description =
                    webStackoverflowService.checkForUpdates(
                        url,
                        LocalDateTime.now().minus(params.getScheduler().forceCheckDelay())
                    );
                if (!description.isEmpty()) {
                    params.getBotService().postUpdate(
                        link.getId(),
                        url,
                        List.of(UPDATED),
                        users
                    );
                }
            }
        }
    }
}

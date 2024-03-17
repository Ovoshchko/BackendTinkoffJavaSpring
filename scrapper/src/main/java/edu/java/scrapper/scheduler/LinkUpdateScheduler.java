package edu.java.scrapper.scheduler;

import edu.java.scrapper.configuration.SchedulerParams;
import edu.java.scrapper.dto.github.GithubResponse;
import edu.java.scrapper.dto.stackoverflow.StackoverflowResponse;
import edu.java.scrapper.model.Link;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@AllArgsConstructor
public class LinkUpdateScheduler {

    public static final String UPDATED = "Пришло обновление!";
    private final SchedulerParams params;

    @Scheduled(fixedDelayString = "#{@getScheduler.interval.toMillis()}")
    public void update() {
        List<Long> users;
        List<Link> links =
            params.getLinkRepository().findLinksUpdatedMoreThanNMinutesAgo(params.getScheduler().interval().toMinutes())
                .stream().toList();

        for (Link link : links) {
            users = params.getUserLinkRepository().getAllUsersByLink(link).stream().toList();
            URI uri = URI.create(link.getLink());
            if ("github.com".equals(uri.getHost())) {
                GithubResponse response = params.getGithubWebClient().checkForUpdate(uri);
                if (link.getLastCheck().isBefore(response.lastUpdateTime().toLocalDateTime())) {
                    params.getBotService().postUpdate(
                        link.getId(),
                        uri,
                        UPDATED,
                        users
                    );
                }
            } else if ("stackoverlow.com".equals(uri.getHost())) {
                StackoverflowResponse response = params.getStackoverflowWebClient().checkForUpdates(uri);
                if (link.getLastCheck().isBefore(response.items().get(0).lastActivityDate().toLocalDateTime())) {
                    params.getBotService().postUpdate(
                        link.getId(),
                        uri,
                        UPDATED,
                        users
                    );
                }
            }
        }
    }
}

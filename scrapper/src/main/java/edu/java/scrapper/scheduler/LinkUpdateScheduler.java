package edu.java.scrapper.scheduler;

import edu.java.scrapper.configuration.SchedulerParams;
import edu.java.scrapper.dto.request.LinkUpdate;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.service.github.GitService;
import edu.java.scrapper.service.link.LinkService;
import edu.java.scrapper.service.stackoverflow.StackoverflowService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
    private final LinkService linkService;

    @Scheduled(fixedDelayString = "#{@getScheduler.interval.toMillis()}")
    public void update() {
        List<Link> links =
            params.getLinkRepository()
                .findLinksUpdatedMoreThanNMinutesAgo(params.getScheduler().interval().toMinutes())
                .stream().toList();

        for (LinkUpdate linkUpdate : getDescriptions(links)) {
            params.getBotService().postUpdate(linkUpdate);
        }
    }

    private List<LinkUpdate> getDescriptions(List<Link> links) {
        List<Long> users;
        List<String> description = new ArrayList<>();
        List<LinkUpdate> updates = new ArrayList<>();

        for (Link link : links) {
            users = params.getUserLinkRepository().getAllUsersByLink(link).stream().toList();
            URI url = URI.create(link.getLink());
            if (GITHUB_HOST.equals(url.getHost())) {
                description = getGitDescription(url);
            } else if (STACKOVERFLOW_HOST.equals(url.getHost())) {
                description = getStackOverflowDescription(url);
            }
            if ((description != null) && (!description.isEmpty())) {
                updates.add(new LinkUpdate(link.getId(), url, description, users));
            }
            linkService.updateLinkLastCheck(link);
        }

        return updates;
    }

    private List<String> getGitDescription(URI url) {
        return webGitService.checkForUpdates(
            url,
            OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime().minus(params.getScheduler().forceCheckDelay())
        );
    }

    private List<String> getStackOverflowDescription(URI url) {
        return webStackoverflowService.checkForUpdates(
            url,
            OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime().minus(params.getScheduler().forceCheckDelay())
        );
    }
}

package edu.java.scrapper.configuration;

import edu.java.scrapper.clients.github.GithubClient;
import edu.java.scrapper.clients.stackoverflow.StackoverflowClient;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.repository.UserLinkRepository;
import edu.java.scrapper.service.bot.BotService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class SchedulerParams {

    private final UserLinkRepository userLinkRepository;
    private final LinkRepository linkRepository;
    private final ApplicationConfig.Scheduler scheduler;
    private final BotService botService;
    private final GithubClient githubWebClient;
    private final StackoverflowClient stackoverflowWebClient;

    @Autowired
    public SchedulerParams(
        UserLinkRepository userLinkRepository, LinkRepository linkRepository,
        ApplicationConfig.Scheduler scheduler, BotService botService,
        GithubClient githubWebClient, StackoverflowClient stackoverflowWebClient
    ) {
        this.userLinkRepository = userLinkRepository;
        this.linkRepository = linkRepository;
        this.scheduler = scheduler;
        this.botService = botService;
        this.githubWebClient = githubWebClient;
        this.stackoverflowWebClient = stackoverflowWebClient;
    }

}


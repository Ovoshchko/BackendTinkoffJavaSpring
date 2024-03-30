package edu.java.scrapper.configuration;

import edu.java.scrapper.clients.github.GithubClient;
import edu.java.scrapper.clients.stackoverflow.StackoverflowClient;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.repository.UserLinkRepository;
import edu.java.scrapper.service.bot.BotService;
import lombok.Data;

@Data
public class SchedulerParams {

    private final UserLinkRepository userLinkRepository;
    private final LinkRepository linkRepository;
    private final ApplicationConfig.Scheduler scheduler;
    private final BotService botService;
    private final GithubClient githubWebClient;
    private final StackoverflowClient stackoverflowWebClient;

}


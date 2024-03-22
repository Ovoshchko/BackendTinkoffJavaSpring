package edu.java.scrapper.configuration;

import edu.java.scrapper.clients.github.GithubClient;
import edu.java.scrapper.clients.stackoverflow.StackoverflowClient;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.repository.UserLinkRepository;
import edu.java.scrapper.service.bot.BotService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@RequiredArgsConstructor
public class SchedulerParams {

    private final UserLinkRepository jdbcUserLinkRepository;
    private final LinkRepository jdbcLinkRepository;
    private final ApplicationConfig.Scheduler scheduler;
    private final BotService botService;
    private final GithubClient githubWebClient;
    private final StackoverflowClient stackoverflowWebClient;

}


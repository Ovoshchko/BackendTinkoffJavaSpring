package edu.java.scrapper.configuration;

import edu.java.scrapper.clients.github.GithubClient;
import edu.java.scrapper.clients.stackoverflow.StackoverflowClient;
import edu.java.scrapper.repository.jooq.JooqGitCommitRepository;
import edu.java.scrapper.repository.jooq.JooqLinkRepository;
import edu.java.scrapper.repository.jooq.JooqStackoverflowAnswerRepository;
import edu.java.scrapper.repository.jooq.JooqUserLinkRepository;
import edu.java.scrapper.repository.jooq.JooqUserRepository;
import edu.java.scrapper.service.bot.BotService;
import edu.java.scrapper.service.chat.DbTgChatIdsService;
import edu.java.scrapper.service.chat.TgChatIdsService;
import edu.java.scrapper.service.github.GitService;
import edu.java.scrapper.service.github.WebGitService;
import edu.java.scrapper.service.link.DbLinkService;
import edu.java.scrapper.service.link.LinkService;
import edu.java.scrapper.service.stackoverflow.StackoverflowService;
import edu.java.scrapper.service.stackoverflow.WebStackoverflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    @Autowired
    public TgChatIdsService tgChatIdsService(JooqUserRepository jooqUserRepository) {
        return new DbTgChatIdsService(jooqUserRepository);
    }

    @Bean
    @Autowired
    public GitService gitService(GithubClient githubClient, JooqGitCommitRepository jooqGitCommitRepository) {
        return new WebGitService(githubClient, jooqGitCommitRepository);
    }

    @Bean
    @Autowired
    public LinkService linkService(
        JooqLinkRepository jooqLinkRepository,
        JooqUserLinkRepository jooqUserLinkRepository
    ) {
        return new DbLinkService(jooqLinkRepository, jooqUserLinkRepository);
    }

    @Bean
    @Autowired
    public StackoverflowService stackoverflowService(
        StackoverflowClient stackoverflowClient,
        JooqStackoverflowAnswerRepository jooqStackoverflowAnswerRepository
    ) {
        return new WebStackoverflowService(stackoverflowClient, jooqStackoverflowAnswerRepository);
    }

    @Bean
    @Autowired
    public SchedulerParams schedulerParams(
        JooqUserLinkRepository jooqUserLinkRepository,
        JooqLinkRepository jooqLinkRepository,
        ApplicationConfig.Scheduler scheduler,
        BotService botService,
        GithubClient githubClient,
        StackoverflowClient stackoverflowClient
    ) {
        return new SchedulerParams(
            jooqUserLinkRepository,
            jooqLinkRepository,
            scheduler,
            botService,
            githubClient,
            stackoverflowClient
        );
    }
}

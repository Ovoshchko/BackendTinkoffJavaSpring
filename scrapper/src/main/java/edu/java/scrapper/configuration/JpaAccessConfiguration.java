package edu.java.scrapper.configuration;

import edu.java.scrapper.clients.github.GithubClient;
import edu.java.scrapper.clients.stackoverflow.StackoverflowClient;
import edu.java.scrapper.repository.jpa.JpaGitCommitRepository;
import edu.java.scrapper.repository.jpa.JpaLinkRepository;
import edu.java.scrapper.repository.jpa.JpaStackoverflowAnswerRepository;
import edu.java.scrapper.repository.jpa.JpaUserLinkRepository;
import edu.java.scrapper.repository.jpa.JpaUserRepository;
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
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    @Autowired
    public TgChatIdsService tgChatIdsService(JpaUserRepository jpaUserRepository) {
        return new DbTgChatIdsService(jpaUserRepository);
    }

    @Bean
    @Autowired
    public GitService gitService(GithubClient githubClient, JpaGitCommitRepository jpaGitCommitRepository) {
        return new WebGitService(githubClient, jpaGitCommitRepository);
    }

    @Bean
    @Autowired
    public LinkService linkService(
        JpaLinkRepository jpaLinkRepository,
        JpaUserLinkRepository jpaUserLinkRepository
    ) {
        return new DbLinkService(jpaLinkRepository, jpaUserLinkRepository);
    }

    @Bean
    @Autowired
    public StackoverflowService stackoverflowService(
        StackoverflowClient stackoverflowClient,
        JpaStackoverflowAnswerRepository jpaStackoverflowAnswerRepository
    ) {
        return new WebStackoverflowService(stackoverflowClient, jpaStackoverflowAnswerRepository);
    }

    @Bean
    @Autowired
    public SchedulerParams schedulerParams(
        JpaUserLinkRepository jpaUserLinkRepository,
        JpaLinkRepository jpaLinkRepository,
        ApplicationConfig.Scheduler scheduler,
        BotService botService,
        GithubClient githubClient,
        StackoverflowClient stackoverflowClient
    ) {
        return new SchedulerParams(
            jpaUserLinkRepository,
            jpaLinkRepository,
            scheduler,
            botService,
            githubClient,
            stackoverflowClient
        );
    }
}

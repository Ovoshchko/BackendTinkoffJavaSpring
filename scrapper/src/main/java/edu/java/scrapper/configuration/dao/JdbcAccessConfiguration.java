package edu.java.scrapper.configuration.dao;

import edu.java.scrapper.clients.github.GithubClient;
import edu.java.scrapper.clients.stackoverflow.StackoverflowClient;
import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.configuration.SchedulerParams;
import edu.java.scrapper.repository.jdbc.JdbcGitCommitRepository;
import edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repository.jdbc.JdbcStackoverflowAnswerRepository;
import edu.java.scrapper.repository.jdbc.JdbcUserLinkRepository;
import edu.java.scrapper.repository.jdbc.JdbcUserRepository;
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
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {

    @Bean
    @Autowired
    public TgChatIdsService tgChatIdsService(JdbcUserRepository jdbcUserRepository) {
        return new DbTgChatIdsService(jdbcUserRepository);
    }

    @Bean
    @Autowired
    public GitService gitService(GithubClient githubClient, JdbcGitCommitRepository jdbcGitCommitRepository) {
        return new WebGitService(githubClient, jdbcGitCommitRepository);
    }

    @Bean
    @Autowired
    public LinkService linkService(
        JdbcLinkRepository jdbcLinkRepository,
        JdbcUserLinkRepository jdbcUserLinkRepository
    ) {
        return new DbLinkService(jdbcLinkRepository, jdbcUserLinkRepository);
    }

    @Bean
    @Autowired
    public StackoverflowService stackoverflowService(
        StackoverflowClient stackoverflowClient,
        JdbcStackoverflowAnswerRepository jdbcStackoverflowAnswerRepository
    ) {
        return new WebStackoverflowService(stackoverflowClient, jdbcStackoverflowAnswerRepository);
    }

    @Bean
    @Autowired
    public SchedulerParams schedulerParams(
        JdbcUserLinkRepository jdbcUserLinkRepository,
        JdbcLinkRepository jdbcLinkRepository,
        ApplicationConfig.Scheduler scheduler,
        BotService botService,
        GithubClient githubClient,
        StackoverflowClient stackoverflowClient
    ) {
        return new SchedulerParams(
            jdbcUserLinkRepository,
            jdbcLinkRepository,
            scheduler,
            botService,
            githubClient,
            stackoverflowClient
        );
    }

}

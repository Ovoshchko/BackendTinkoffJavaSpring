package edu.java.scrapper.configuration;

import edu.java.scrapper.scheduler.LinkUpdateScheduler;
import edu.java.scrapper.service.github.GitService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {

    private final GitService gitService;
    private final SchedulerParams schedulerParams;

    @Bean
    public LinkUpdateScheduler getLinkUpdateScheduler() {
        return new LinkUpdateScheduler(gitService, schedulerParams);
    }
}

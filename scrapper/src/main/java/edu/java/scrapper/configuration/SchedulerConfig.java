package edu.java.scrapper.configuration;

import edu.java.scrapper.scheduler.LinkUpdateScheduler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SchedulerConfig {

    private final SchedulerParams params;

    @Bean
    public LinkUpdateScheduler getLinkUpdateScheduler() {
        return new LinkUpdateScheduler(params);
    }
}

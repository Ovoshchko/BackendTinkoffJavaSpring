package edu.java.scrapper.configuration;

import edu.java.scrapper.scheduler.LinkUpdateScheduler;
import org.springframework.context.annotation.Bean;

public class SchedulerConfig {

    @Bean
    public LinkUpdateScheduler getLinkUpdateScheduler() {
        return new LinkUpdateScheduler();
    }
}

package edu.java.configuration;

import edu.java.scheduler.LinkUpdateScheduler;
import org.springframework.context.annotation.Bean;

public class SchedulerConfig {

    @Bean
    public LinkUpdateScheduler getLinkUpdateScheduler() {
        return new LinkUpdateScheduler();
    }
}

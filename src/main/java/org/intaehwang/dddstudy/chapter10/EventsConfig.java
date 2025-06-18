package org.intaehwang.dddstudy.chapter10;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventsConfig {
    private final ApplicationContext applicationContext;

    @Bean
    public InitializingBean eventInitializer(ApplicationContext applicationContext) {
        return () -> Events.setPublisher(applicationContext);
    }
}

package org.intaehwang.dddstudy.chapter10;

import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class Events {
    private static ApplicationEventPublisher publisher;

    public static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(Object event) {
        if (publisher != null) {
            Events.publisher.publishEvent(event);
        }
    }
}

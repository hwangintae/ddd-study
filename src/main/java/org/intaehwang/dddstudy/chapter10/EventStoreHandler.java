package org.intaehwang.dddstudy.chapter10;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventStoreHandler {
    private EventStore eventStore;

    public EventStoreHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @EventListener(Event.class)
    public void handleEvent(Event event) {
        eventStore.save(event);
    }
}

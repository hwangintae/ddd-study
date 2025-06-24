package org.intaehwang.dddstudy.chapter10;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventForwarder {

    private static final int DEFAULT_LIMIT_SIZE = 100;

    private final EventStore eventStore;
    private final OffsetStore offsetStore;
    private final EventSender eventSender;
    private int limitSize = DEFAULT_LIMIT_SIZE;

    @Scheduled(initialDelay = 1000L, fixedDelay = 1000L)
    public void getAndSend() {
        int nextOffset = getNextOffset();
        List<EventEntry> events = eventStore.get(nextOffset, limitSize);

        if (!events.isEmpty()) {
            int processedCount = sendEvent(events);
            if (processedCount > 0) {
                saveNextOffset(nextOffset + processedCount);
            }
        }
    }

    private int getNextOffset() {
        return offsetStore.get();
    }

    private int sendEvent(List<EventEntry> events) {
        int processedCount = 0;

        try {
            for (EventEntry entry : events) {
                eventSender.send(entry);
                processedCount++;
            }
        } catch (Exception e) {

        }

        return processedCount;
    }

    private void saveNextOffset(int nextOffset) {
        offsetStore.update(nextOffset);
    }
}

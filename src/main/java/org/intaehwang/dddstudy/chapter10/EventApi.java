package org.intaehwang.dddstudy.chapter10;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventApi {
    private final EventStore eventStore;

    @GetMapping("/api/events")
    public List<EventEntry> list(@RequestParam int offset, @RequestParam int limit) {
        return eventStore.get(offset, limit);
    }
}

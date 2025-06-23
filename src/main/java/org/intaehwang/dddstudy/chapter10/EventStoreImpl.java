package org.intaehwang.dddstudy.chapter10;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventStoreImpl implements EventStore {

    private final JpaEventStore jpaEventStore;
    private final ObjectMapper objectMapper;

    private static final String APPLICATION_JSON = "application/json";

    @Override
    public void save(Object event) {
        new EventEntry(event.getClass().getName(), APPLICATION_JSON, toJson(event));
    }

    @Override
    public List<EventEntry> get(int offset, int limit) {
        int page = offset / limit;

        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<EventEntry> pageResult = jpaEventStore.findAll(pageRequest);

        return pageResult.getContent();
    }

    private String toJson(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

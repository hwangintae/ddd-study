package org.intaehwang.dddstudy.chapter10;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEventStore extends JpaRepository<EventEntry, Long> {
}

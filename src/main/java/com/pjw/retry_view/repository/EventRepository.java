package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    public List<Event> findAll();
    public Optional<Event> findById(Long id);
    public Event save(Event Event);
    public void deleteById(Long id);
}

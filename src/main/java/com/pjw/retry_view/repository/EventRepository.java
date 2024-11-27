package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    public List<Event> findAllByOrderByIdDesc(Pageable pageable);
    public List<Event> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable);
    public Optional<Event> findById(Long id);
    public Event save(Event Event);
    public void deleteById(Long id);
}

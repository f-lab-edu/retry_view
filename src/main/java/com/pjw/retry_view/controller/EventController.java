package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.EventDTO;
import com.pjw.retry_view.request.WriteEventRequest;
import com.pjw.retry_view.service.EventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventDTO> getEventList(){
        return eventService.getEventList();
    }

    @GetMapping("/{id}")
    public EventDTO getEvent(@PathVariable(name = "id")Long id){
        return eventService.getEvent(id);
    }

    @PostMapping
    public EventDTO writeEvent(@RequestBody @Valid WriteEventRequest event){
        return eventService.saveEvent(event);
    }

    @PutMapping
    public EventDTO updateEvent(@RequestBody @Valid WriteEventRequest event){
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable(name = "id")Long id){
        eventService.deleteEvent(id);
    }
}

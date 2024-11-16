package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.EventDTO;
import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.request.WriteEventRequest;
import com.pjw.retry_view.service.EventService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public List<EventDTO> getEventList(@RequestParam(name = "cursor", required = false) Long cursor){
        return eventService.getEventList(cursor);
    }

    @GetMapping("/{id}")
    public EventDTO getEvent(@PathVariable(name = "id")Long id){
        return eventService.getEvent(id);
    }

    @PostMapping
    public EventDTO writeEvent(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteEventRequest event){
        event.setCreatedBy(userDetail.getId());
        return eventService.saveEvent(event);
    }

    @PutMapping("/{id}")
    public EventDTO updateEvent(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteEventRequest event, @PathVariable(name = "id")Long id){
        event.setUpdatedBy(userDetail.getId());
        return eventService.updateEvent(event, id);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable(name = "id")Long id){
        eventService.deleteEvent(id);
    }
}

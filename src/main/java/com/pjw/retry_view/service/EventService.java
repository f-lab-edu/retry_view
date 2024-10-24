package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.EventDTO;
import com.pjw.retry_view.entity.Event;
import com.pjw.retry_view.entity.EventImage;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repository.EventImageRepository;
import com.pjw.retry_view.repository.EventRepository;
import com.pjw.retry_view.request.WriteEventRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventImageRepository eventImageRepository;

    public EventService(EventRepository eventRepository, EventImageRepository eventImageRepository) {
        this.eventRepository = eventRepository;
        this.eventImageRepository = eventImageRepository;
    }

    public List<EventDTO> getEventList(){
        return eventRepository.findAll().stream().map(Event::toDTO).toList();
    }

    public EventDTO getEvent(Long id){
        return eventRepository.findById(id).orElseThrow(ResourceNotFoundException::new).toDTO();
    }

    @Transactional
    public EventDTO saveEvent(WriteEventRequest req){
        Event event = Event.newOne(req.getContent(), req.getStartAt(), req.getEndAt(), req.getCreatedBy());
        List<EventImage> images = req.getImages().stream().map(img -> EventImage.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList();
        eventRepository.save(event);

        for(EventImage eventImage : images){
            eventImage.changeEvent(event);
            eventImageRepository.save(eventImage);
        }
        return event.toDTO();
    }

    @Transactional
    public EventDTO updateEvent(WriteEventRequest req){
        Event event = eventRepository.findById(req.getId()).orElseThrow(ResourceNotFoundException::new);
        List<EventImage> reqImages = req.getImages().stream().map(img -> EventImage.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList();

        List<Long> imageIds = req.getImages().stream().map(WriteEventRequest.Image::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = event.getEventImage().stream().map(EventImage::getId).toList();
        List<Long> deleteImageIds = getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            eventImageRepository.deleteByIds(deleteImageIds);
            event.getEventImage().removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(EventImage eventImage : reqImages){
            if(eventImage.getId() == null) {
                eventImage.changeEvent(event);
                eventImageRepository.save(eventImage);
            }
        }

        event.updateEvent(req.getContent(), req.getStartAt(), req.getEndAt(), req.getUpdatedBy());
        return eventRepository.save(event).toDTO();
    }

    @Transactional
    public void deleteEvent(Long id){
        Event event = eventRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        eventImageRepository.deleteByIds(event.getEventImage().stream().map(EventImage::getId).toList());
        eventRepository.deleteById(id);
    }

    public List<Long> getDeleteImageIds(List<Long> images, List<Long> oldImageIds){
        if(CollectionUtils.isEmpty(images) || CollectionUtils.isEmpty(oldImageIds)) return null;
        List<Long> deleteImageIds = new ArrayList<>();
        for(Long id : oldImageIds){
            if(!images.contains(id)){
                deleteImageIds.add(id);
            }
        }
        return deleteImageIds;
    }
}

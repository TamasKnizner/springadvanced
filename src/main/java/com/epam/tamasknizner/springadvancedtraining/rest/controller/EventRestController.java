package com.epam.tamasknizner.springadvancedtraining.rest.controller;

import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.rest.domain.EventRequest;
import com.epam.tamasknizner.springadvancedtraining.rest.transformer.EventTransformer;
import com.epam.tamasknizner.springadvancedtraining.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/event")
public class EventRestController {

    private final EventService eventService;
    private final EventTransformer eventTransformer;

    @Autowired
    public EventRestController(final EventService eventService, final EventTransformer eventTransformer) {
        this.eventService = eventService;
        this.eventTransformer = eventTransformer;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Event createEvent(@RequestBody EventRequest eventRequest) {
        return eventService.save(eventTransformer.transform(eventRequest));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Event updateEvent(@PathVariable Long id, @RequestBody EventRequest eventRequest) {
        return eventService.save(eventTransformer.transform(id, eventRequest));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable Long id) {
        eventService.remove(eventService.getById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Event getEventById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Event> getAllEvent() {
        return eventService.getAll();
    }

}

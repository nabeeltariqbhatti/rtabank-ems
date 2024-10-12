package ae.rakbank.eventmanagementservice.controller;

import ae.rakbank.eventmanagementservice.dtos.request.EventRequest;
import ae.rakbank.eventmanagementservice.dtos.response.EventResponse;
import ae.rakbank.eventmanagementservice.exceptions.EventNotFoundException;
import ae.rakbank.eventmanagementservice.mapper.EventMapper;
import ae.rakbank.eventmanagementservice.model.Event;
import ae.rakbank.eventmanagementservice.service.EventService;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/events")
@Slf4j
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Observed(name = "create.event",
            contextualName = "creating-event"
    )
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest event) {
        log.info("Creating event: {}", event);
        var createdEvent = eventService.createEvent(event);
        var eventDto = EventMapper.toEventResponse(createdEvent);
        log.info("Event created successfully: {}", eventDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventDto);
    }

    @GetMapping(path = "{eventId}")
    @Observed(name = "retrieve.event",
            contextualName = "retrieving-event"
    )
    public ResponseEntity<EventResponse> getEventById(@PathVariable("eventId") Long id) {
        log.info("Fetching event with id: {}", id);
        Event eventById = eventService.getEventById(id)
                .orElseThrow(() -> {
                    log.warn("Event with id {} not found", id);
                    return new EventNotFoundException(String.format("event with %s having %d value not found", "id", id));
                });
        log.info("Event retrieved successfully: {}", eventById);
        return new ResponseEntity<>(EventMapper.toEventResponse(eventById), HttpStatus.OK);
    }

    @GetMapping("/search")
    @Observed(name = "search.events",
            contextualName = "searching-event"
    )
    public ResponseEntity<Page<EventResponse>> searchEvent(@RequestParam("name") String name,
                                                           @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        log.info("Searching events by name: {} (Page: {}, Size: {})", name, pageNo, pageSize);
        Page<EventResponse> events = eventService.searchByName(pageNo, pageSize, name);
        log.info("Found {} events", events.getTotalElements());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PutMapping
    @Observed(name = "update.events",
            contextualName = "updating-event"
    )
    public ResponseEntity<EventResponse> updateEvent(@RequestParam("eventId") Long eventId,
                                                     @RequestBody EventRequest eventRequest) {
        Event updateEvent = eventService.updateEvent(eventId, eventRequest);

        return new ResponseEntity<>(EventMapper.toEventResponse(updateEvent), HttpStatus.OK);
    }

    @DeleteMapping
    @Observed(name = "delete.event",
            contextualName = "deleting-event"
    )
    public ResponseEntity<Void> deleteEvent(@PathVariable("eventId") Long eventId) {
        String message = "Event successfully deleted.";
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

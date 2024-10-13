package ae.rakbank.eventmanagementservice.controller;

import ae.rakbank.eventmanagementservice.dtos.request.EventRequest;
import ae.rakbank.eventmanagementservice.dtos.response.EventResponse;
import ae.rakbank.eventmanagementservice.exceptions.EventNotFoundException;
import ae.rakbank.eventmanagementservice.mapper.EventMapper;
import ae.rakbank.eventmanagementservice.model.Event;
import ae.rakbank.eventmanagementservice.service.EventService;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@CrossOrigin(origins = {"http://localhost:8080","http://ems:9090"})
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Create a new event",
            description = "This endpoint allows you to create a new event by providing the necessary details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Observed(name = "create.event", contextualName = "creating-event")
    public ResponseEntity<EventResponse> createEvent(
            @Parameter(description = "Event request details", required = true)
            @Valid @RequestBody EventRequest event) {
        log.info("Creating event: {}", event);
        var createdEvent = eventService.createEvent(event);
        var eventDto = EventMapper.toEventResponse(createdEvent);
        log.info("Event created successfully: {}", eventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventDto);
    }

    @Operation(summary = "Retrieve event by ID",
            description = "Fetch event details using the event ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping(path = "{eventId}")
    @Observed(name = "retrieve.event", contextualName = "retrieving-event")
    public ResponseEntity<EventResponse> getEventById(
            @Parameter(description = "ID of the event", required = true)
            @PathVariable("eventId") Long id) {
        log.info("Fetching event with id: {}", id);
        Event eventById = eventService.getEventById(id)
                .orElseThrow(() -> {
                    log.warn("Event with id {} not found", id);
                    return new EventNotFoundException(String.format("Event with %s having %d value not found", "id", id));
                });
        log.info("Event retrieved successfully: {}", eventById);
        return new ResponseEntity<>(EventMapper.toEventResponse(eventById), HttpStatus.OK);
    }

    @Operation(summary = "Search events by name",
            description = "Search for events by providing a name, with pagination options.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of events found")
    })
    @GetMapping("/search")
    @Observed(name = "search.events", contextualName = "searching-event")
    public ResponseEntity<Page<EventResponse>> searchEvent(
            @Parameter(description = "Name of the event to search", required = true)
            @RequestParam("name") String name,
            @Parameter(description = "Page number for pagination", example = "0", required = false)
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @Parameter(description = "Page size for pagination", example = "10", required = false)
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        log.info("Searching events by name: {} (Page: {}, Size: {})", name, pageNo, pageSize);
        Page<EventResponse> events = eventService.searchByName(pageNo, pageSize, name);
        log.info("Found {} events", events.getTotalElements());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @Operation(summary = "Update an event",
            description = "Update an existing event by providing its ID and the updated details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event successfully updated"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping
    @Observed(name = "update.events", contextualName = "updating-event")
    public ResponseEntity<EventResponse> updateEvent(
            @Parameter(description = "ID of the event", required = true)
            @RequestParam("eventId") Long eventId,
            @Parameter(description = "Updated event details", required = true)
            @RequestBody EventRequest eventRequest) {
        Event updateEvent = eventService.updateEvent(eventId, eventRequest);
        return new ResponseEntity<>(EventMapper.toEventResponse(updateEvent), HttpStatus.OK);
    }

    @Operation(summary = "Delete an event",
            description = "Delete an existing event using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping
    @Observed(name = "delete.event", contextualName = "deleting-event")
    public ResponseEntity<Void> deleteEvent(
            @Parameter(description = "ID of the event to delete", required = true)
            @PathVariable("eventId") Long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

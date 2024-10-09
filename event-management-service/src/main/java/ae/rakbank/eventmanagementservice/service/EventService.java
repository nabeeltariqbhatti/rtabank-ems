package ae.rakbank.eventmanagementservice.service;

import ae.rakbank.eventmanagementservice.dtos.request.EventRequest;
import ae.rakbank.eventmanagementservice.dtos.response.EventResponse;
import ae.rakbank.eventmanagementservice.exceptions.EventCreationException;
import ae.rakbank.eventmanagementservice.exceptions.EventNotFoundException;
import ae.rakbank.eventmanagementservice.exceptions.VenueSlotNotAvailableException;
import ae.rakbank.eventmanagementservice.mapper.EventMapper;
import ae.rakbank.eventmanagementservice.model.Event;
import ae.rakbank.eventmanagementservice.repositories.EventRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class EventService {


    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Transactional
//    @Retryable(
//            value = {
//                    SocketTimeoutException.class,
//                    SQLException.class,
//                    DataAccessResourceFailureException.class,
//                    DataAccessException.class
//            },
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 2000)
//    )
    //TODO: make it retryable
    public Event createEvent(EventRequest event) {
        long count = eventRepository.existsEventByVenueAndStartDateTimeBetween(event.getVenue(), event.getStartDateTime(), event.getEndDateTime());
        if (count > 0) throw new VenueSlotNotAvailableException("Please select another slot.");
        try {
            Event mappedEvent = EventMapper.toEvent(event);
            return eventRepository.save(mappedEvent);
        } catch (Exception ex) {
            log.info("error while creating event", ex);
            throw new EventCreationException("error while creating event");
        }
    }

    public Event updateEvent(Long eventId, EventRequest eventDTO) {
        Event eventById = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + eventId));

        if (Objects.nonNull(eventDTO.getName())) eventById.setName(eventDTO.getName());
        if (Objects.nonNull(eventDTO.getDescription())) eventById.setDescription(eventDTO.getDescription());
        if (Objects.nonNull(eventDTO.getVenue())) eventById.setVenue(eventDTO.getVenue());

        if (Objects.nonNull(eventDTO.getStartDateTime())) eventById.setStartDateTime(eventDTO.getStartDateTime());
        if (Objects.nonNull(eventDTO.getEndDateTime())) eventById.setEndDateTime(eventDTO.getEndDateTime());
        if (Objects.nonNull(eventDTO.getOrganizer())) eventById.setOrganizer(eventDTO.getOrganizer());
        if (Objects.nonNull(eventDTO.getTags())) eventById.setTags(eventDTO.getTags());
        if (Objects.nonNull(eventDTO.getStatus())) {
            log.info("Event ID: " + eventId + ", Status: " + eventDTO.getStatus());
        };
        eventById.setStatus(eventDTO.getStatus());
        return eventRepository.save(eventById);
    }

    public void deleteEvent(Long id) {
        Event eventById = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
        eventRepository.deleteById(id);
    }

    public Page<EventResponse> searchByName(int pageNo, int pageSize, String query) {
        Page<Event> searchEvents = eventRepository.findAllByNameLike(query, PageRequest.of(pageNo, pageSize));
        List<EventResponse> eventResponses = searchEvents.stream().map(EventMapper::toEventResponse).toList();
        return new PageImpl<>(eventResponses, PageRequest.of(pageNo, pageSize), searchEvents.getTotalElements());
    }
}

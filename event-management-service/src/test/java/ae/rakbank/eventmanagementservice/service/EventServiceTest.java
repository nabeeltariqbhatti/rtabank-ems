package ae.rakbank.eventmanagementservice.service;

import ae.rakbank.eventmanagementservice.dtos.request.EventRequest;
import ae.rakbank.eventmanagementservice.dtos.response.EventResponse;
import ae.rakbank.eventmanagementservice.exceptions.EventCreationException;
import ae.rakbank.eventmanagementservice.exceptions.EventNotFoundException;
import ae.rakbank.eventmanagementservice.exceptions.VenueSlotNotAvailableException;
import ae.rakbank.eventmanagementservice.mapper.EventMapper;
import ae.rakbank.eventmanagementservice.model.Event;
import ae.rakbank.eventmanagementservice.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        event = new Event();
        event.setId(1L);
        event.setName("Sample Event");
        event.setVenue("Sample Venue");
        event.setStartDateTime(LocalDateTime.now());
        event.setEndDateTime(LocalDateTime.now().plusHours(1));
    }

    @Test
    public void testGetAllEvents() {
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event));
        List<Event> events = eventService.getAllEvents();
        assertEquals(1, events.size());
        assertEquals(event.getName(), events.get(0).getName());
    }

    @Test
    public void testGetEventById() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        Optional<Event> foundEvent = eventService.getEventById(1L);
        assertTrue(foundEvent.isPresent());
        assertEquals(event.getName(), foundEvent.get().getName());
    }

    @Test
    public void testCreateEvent() {
        EventRequest request = new EventRequest();
        request.setName("New Event");
        request.setVenue("New Venue");
        request.setStartDateTime(LocalDateTime.now());
        request.setEndDateTime(LocalDateTime.now().plusHours(1));

        when(eventRepository.existsEventByVenueAndStartDateTimeBetween(any(), any(), any())).thenReturn(0);
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event createdEvent = eventService.createEvent(request);
        assertNotNull(createdEvent);
        assertEquals(event.getName(), createdEvent.getName());
    }

    @Test
    public void testCreateEvent_VenueSlotNotAvailable() {
        EventRequest request = new EventRequest();
        request.setVenue("Busy Venue");
        request.setStartDateTime(LocalDateTime.now());
        request.setEndDateTime(LocalDateTime.now().plusHours(1));

        when(eventRepository.existsEventByVenueAndStartDateTimeBetween(any(), any(), any())).thenReturn(1);

        assertThrows(VenueSlotNotAvailableException.class, () -> eventService.createEvent(request));
    }

    @Test
    public void testUpdateEvent() {
        EventRequest request = new EventRequest();
        request.setName("Updated Event");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event updatedEvent = eventService.updateEvent(1L, request);
        assertEquals("Updated Event", updatedEvent.getName());
    }

    @Test
    public void testUpdateEvent_EventNotFound() {
        EventRequest request = new EventRequest();

        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.updateEvent(1L, request));
    }

    @Test
    public void testDeleteEvent() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        eventService.deleteEvent(1L);
        verify(eventRepository).deleteById(1L);
    }

    @Test
    public void testDeleteEvent_EventNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.deleteEvent(1L));
    }

    @Test
    public void testSearchByName() {
        EventResponse response =  EventResponse.builder().build();


        when(eventRepository.findAllByNameLike(any(), any(PageRequest.class))).thenReturn(new PageImpl<>(Arrays.asList(event)));
        
        Page<EventResponse> result = eventService.searchByName(0, 10, "Sample");

        assertEquals(1, result.getContent().size());
        assertEquals(event.getName(), result.getContent().get(0).name());
    }
}

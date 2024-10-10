package ae.rakbank.eventmanagementservice;

import ae.rakbank.eventmanagementservice.controller.EventController;
import ae.rakbank.eventmanagementservice.dtos.request.EventRequest;
import ae.rakbank.eventmanagementservice.dtos.response.EventResponse;
import ae.rakbank.eventmanagementservice.exceptions.EventNotFoundException;
import ae.rakbank.eventmanagementservice.model.Event;
import ae.rakbank.eventmanagementservice.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    void testCreateEvent() throws Exception {
        EventRequest eventRequest = new EventRequest();
        // Set necessary fields for the EventRequest object

        Event event = new Event();
        // Set necessary fields for the Event object

        when(eventService.createEvent(any(EventRequest.class))).thenReturn(event);

        mockMvc.perform(post("/v1/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetEventByIdSuccess() throws Exception {
        Event event = new Event();
        event.setId(1L);

        when(eventService.getEventById(anyLong())).thenReturn(Optional.of(event));

        mockMvc.perform(get("/v1/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetEventByIdNotFound() throws Exception {
        when(eventService.getEventById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/events/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchEvent() throws Exception {
        // Create mock Page object for EventResponse
        Page<EventResponse> eventPage = Page.empty(); // Mock an empty page
        when(eventService.searchByName(anyInt(), anyInt(), anyString())).thenReturn(eventPage);

        mockMvc.perform(get("/v1/events/search")
                .param("name", "Test Event")
                .param("pageNo", "0")
                .param("pageSize", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateEvent() throws Exception {
        EventRequest eventRequest = new EventRequest();
        // Set necessary fields for the EventRequest object

        Event event = new Event();
        event.setId(1L);

        when(eventService.updateEvent(anyLong(), any(EventRequest.class))).thenReturn(event);

        mockMvc.perform(put("/v1/events")
                .param("eventId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDeleteEvent() throws Exception {
        mockMvc.perform(delete("/v1/events/1"))
                .andExpect(status().isNoContent());
    }
}

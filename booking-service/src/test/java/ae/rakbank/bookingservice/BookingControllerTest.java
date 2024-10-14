package ae.rakbank.bookingservice;

import ae.rakbank.bookingservice.controller.BookingController;
import ae.rakbank.bookingservice.dto.request.BookingRequest;
import ae.rakbank.bookingservice.dto.request.UpdateBookingRequest;
import ae.rakbank.bookingservice.dto.response.BookingResponse;
import ae.rakbank.bookingservice.model.Booking;
import ae.rakbank.bookingservice.service.impl.BookingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Mock
    private BookingServiceImpl bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void testCreateBooking() throws Exception {
        BookingRequest bookingRequest = new BookingRequest();
        // Set necessary fields for the BookingRequest

        Booking booking = new Booking();
        // Set necessary fields for the Booking object

        when(bookingService.createBooking(any(BookingRequest.class))).thenReturn(booking);

        mockMvc.perform(post("/v1/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetBookingByIdSuccess() throws Exception {
        Booking booking = new Booking();
        booking.setId(1L);
        // Set other necessary booking fields

        when(bookingService.getBookingById(anyLong())).thenReturn(Optional.of(booking));

        mockMvc.perform(get("/v1/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetBookingByIdNotFound() throws Exception {
        when(bookingService.getBookingById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/bookings/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllBookings() throws Exception {
        Booking booking1 = new Booking();
        booking1.setId(1L);
        Booking booking2 = new Booking();
        booking2.setId(2L);
        List<Booking> bookingList = Arrays.asList(booking1, booking2);

        when(bookingService.getAllBookings()).thenReturn(bookingList);

        mockMvc.perform(get("/v1/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void testUpdateBookingSuccess() throws Exception {
        UpdateBookingRequest updateBookingRequest = new UpdateBookingRequest();
        // Set necessary fields for UpdateBookingRequest

        Booking updatedBooking = new Booking();
        updatedBooking.setId(1L);

        when(bookingService.updateBooking(anyLong(), any(UpdateBookingRequest.class))).thenReturn(updatedBooking);

        mockMvc.perform(put("/v1/bookings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBookingRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateBookingNotFound() throws Exception {
        UpdateBookingRequest updateBookingRequest = new UpdateBookingRequest();
        // Set necessary fields

        when(bookingService.updateBooking(anyLong(), any(UpdateBookingRequest.class))).thenThrow(new IllegalArgumentException("Booking not found"));

        mockMvc.perform(put("/v1/bookings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBookingRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteBooking() throws Exception {
        mockMvc.perform(delete("/v1/bookings/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetTicketsByBookingId() throws Exception {
        BookingResponse bookingResponse = null;

        when(bookingService.getBookingsWithTickets(anyLong())).thenReturn(bookingResponse);

        mockMvc.perform(get("/v1/bookings/1/with/tickets"))
                .andExpect(status().isOk());
    }

    @Test
    void testCache() throws Exception {
        // Test if cache retrieval works properly

        mockMvc.perform(get("/v1/bookings/cache"))
                .andExpect(status().isOk());
    }
}

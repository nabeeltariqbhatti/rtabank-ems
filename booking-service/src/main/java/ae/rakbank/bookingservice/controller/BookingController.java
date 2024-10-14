package ae.rakbank.bookingservice.controller;

import ae.rakbank.bookingservice.cache.EventCache;
import ae.rakbank.bookingservice.dto.event.EventMetadata;
import ae.rakbank.bookingservice.dto.request.BookingRequest;
import ae.rakbank.bookingservice.dto.request.UpdateBookingRequest;
import ae.rakbank.bookingservice.dto.response.BookingResponse;
import ae.rakbank.bookingservice.exceptions.BookingNotFoundException;
import ae.rakbank.bookingservice.model.Booking;
import ae.rakbank.bookingservice.service.impl.BookingServiceImpl;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/bookings")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:9091", "http://booking-service:9091"})
public class BookingController {

    private final BookingServiceImpl bookingService;

    public BookingController(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Create a new booking",
            description = "This endpoint allows you to create a new booking by providing the necessary details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    @Observed(name = "create.booking", contextualName = "creating-booking")
    public ResponseEntity<Booking> createBooking(
            @Parameter(description = "Booking request details", required = true)
            @Valid @RequestBody BookingRequest booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve booking by ID",
            description = "Fetch booking details using the booking ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/{bookingId}")
    @Observed(name = "retrieve.booking", contextualName = "retrieving-booking")
    public ResponseEntity<Booking> getBookingById(
            @Parameter(description = "ID of the booking", required = true)
            @PathVariable Long bookingId) {
        Optional<Booking> booking = bookingService.getBookingById(bookingId);
        return booking.map(ResponseEntity::ok)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id " + bookingId));
    }

    @Operation(summary = "Retrieve all bookings",
            description = "Fetch all the bookings in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of bookings retrieved")
    })
    @GetMapping
    @Observed(name = "retrieve.bookings", contextualName = "retrieving-bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @Operation(summary = "Update booking by ID",
            description = "Update an existing booking by providing its ID and the updated details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking successfully updated"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PutMapping("/{bookingId}")
    @Observed(name = "update.booking", contextualName = "update-booking")
    public ResponseEntity<Booking> updateBooking(
            @Parameter(description = "ID of the booking", required = true)
            @PathVariable Long bookingId,
            @RequestBody UpdateBookingRequest updatedBooking) {
        try {
            Booking booking = bookingService.updateBooking(bookingId, updatedBooking);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Retrieve tickets for a booking",
            description = "Fetch booking details along with associated tickets using the booking ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking and tickets found"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/{bookingId}/with/tickets")
    @Observed(name = "retrieve.tickets.booking", contextualName = "retrieving-tickets-booking")
    public ResponseEntity<BookingResponse> getTicketsByBookingId(
            @Parameter(description = "ID of the booking", required = true)
            @PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBookingsWithTickets(bookingId));
    }

    @Operation(summary = "Delete a booking",
            description = "Delete an existing booking using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @DeleteMapping("/{bookingId}")
    @Observed(name = "delete.booking", contextualName = "deleting-booking")
    public ResponseEntity<Void> deleteBooking(
            @Parameter(description = "ID of the booking to delete", required = true)
            @PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get cached events",
            description = "Fetch all cached events.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cached events retrieved")
    })
    @GetMapping("/cache")
    public List<EventMetadata> cache() {
        return EventCache.getAll().values()
                .parallelStream().toList();
    }
}

package ae.rakbank.eventbookingservice.controller;

import ae.rakbank.eventbookingservice.cache.EventCache;
import ae.rakbank.eventbookingservice.dto.event.EventMetadata;
import ae.rakbank.eventbookingservice.dto.request.BookingRequest;
import ae.rakbank.eventbookingservice.dto.request.UpdateBookingRequest;
import ae.rakbank.eventbookingservice.dto.response.BookingResponse;
import ae.rakbank.eventbookingservice.exceptions.BookingNotFoundException;
import ae.rakbank.eventbookingservice.model.Booking;
import ae.rakbank.eventbookingservice.model.Ticket;
import ae.rakbank.eventbookingservice.service.impl.BookingServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/bookings")
public class BookingController {

    private final BookingServiceImpl bookingService;

    public BookingController(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequest booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        Optional<Booking> booking = bookingService.getBookingById(bookingId);
        return booking.map(ResponseEntity::ok).orElseThrow(() ->
                new BookingNotFoundException("booking not found with id " + bookingId));
    }


    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }


    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @RequestBody UpdateBookingRequest updatedBooking) {
        try {
            Booking booking = bookingService.updateBooking(bookingId, updatedBooking);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{bookingId}/with/tickets")
    public ResponseEntity<BookingResponse> getTicketsByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBookingsWithTickets(bookingId));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/cache")
    public List cache(){
        return EventCache.getAll().values()
                .parallelStream().toList();
    }
}

package ae.rakbank.eventbookingservice.service;

import ae.rakbank.eventbookingservice.dto.request.BookingRequest;
import ae.rakbank.eventbookingservice.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    Booking createBooking(BookingRequest booking);

    Booking updateBooking(Long bookingId, Booking updatedBooking);

    Optional<Booking> getBookingById(Long bookingId);

    List<Booking> getAllBookings();

    void deleteBooking(Long bookingId);
}
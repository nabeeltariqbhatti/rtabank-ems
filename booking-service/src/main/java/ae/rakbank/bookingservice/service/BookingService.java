package ae.rakbank.bookingservice.service;

import ae.rakbank.bookingservice.dto.request.BookingRequest;
import ae.rakbank.bookingservice.dto.request.UpdateBookingRequest;
import ae.rakbank.bookingservice.dto.response.BookingResponse;
import ae.rakbank.bookingservice.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    Booking createBooking(BookingRequest booking);

    Booking updateBooking(Long bookingId, UpdateBookingRequest updatedBooking);
    Booking updateBooking(Long bookingId, Booking updatedBooking);

    Optional<Booking> getBookingById(Long bookingId);

    List<Booking> getAllBookings();

    void deleteBooking(Long bookingId);

    BookingResponse getBookingsWithTickets(Long bookingId);

    Booking getByBookingCode(String bookingCode);
}
package ae.rakbank.eventbookingservice.service.impl;

import ae.rakbank.eventbookingservice.dto.request.BookingRequest;
import ae.rakbank.eventbookingservice.mapper.BookingMapper;
import ae.rakbank.eventbookingservice.model.Booking;
import ae.rakbank.eventbookingservice.model.Ticket;
import ae.rakbank.eventbookingservice.repository.BookingRepository;
import ae.rakbank.eventbookingservice.service.BookingService;
import ae.rakbank.eventbookingservice.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Booking createBooking(BookingRequest booking) {
        Booking bookingEntity = BookingMapper.toBooking(booking);
        bookingEntity.setBookingCode(Utils.generateBookingCode(booking.getEventCode()));
        for(int i =0; i< booking.getNumberOfTickets(); i++){
            Ticket ticket = Ticket.builder()
                    .ticketType(booking.getTicketType())
                    .ticketNumber(Utils.generateTicketNumber(booking.getEventId(), booking.getEventCode(),
                            booking.getTicketType()))
                    .build();
            bookingEntity.addTicket(ticket);
        }
        return bookingRepository.save(bookingEntity);
    }

    @Transactional
    public Booking updateBooking(Long bookingId, Booking updatedBooking) {
        Optional<Booking> existingBooking = bookingRepository.findById(bookingId);
        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            booking.setStatus(updatedBooking.getStatus());
            booking.setPaymentStatus(updatedBooking.getPaymentStatus());
            booking.setTickets(updatedBooking.getTickets());
            return bookingRepository.save(booking);
        } else {
            throw new IllegalArgumentException("Booking not found");
        }
    }

    public Optional<Booking> getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId);
    }
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    @Transactional
    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}

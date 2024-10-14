package ae.rakbank.bookingservice.service.impl;

import ae.rakbank.bookingservice.cache.EventCache;
import ae.rakbank.bookingservice.dto.event.EventMetadata;
import ae.rakbank.bookingservice.dto.request.BookingRequest;
import ae.rakbank.bookingservice.dto.request.UpdateBookingRequest;
import ae.rakbank.bookingservice.dto.response.BookingResponse;
import ae.rakbank.bookingservice.exceptions.BookingCreationException;
import ae.rakbank.bookingservice.exceptions.BookingNotFoundException;
import ae.rakbank.bookingservice.exceptions.BookingTimePassedException;
import ae.rakbank.bookingservice.mapper.BookingMapper;
import ae.rakbank.bookingservice.model.Booking;
import ae.rakbank.bookingservice.model.Ticket;
import ae.rakbank.bookingservice.repository.BookingRepository;
import ae.rakbank.bookingservice.service.BookingService;
import ae.rakbank.bookingservice.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Booking createBooking(BookingRequest booking) {
        EventMetadata event = EventCache.getEvent(booking.getEventCode());
        if(event==null){
            throw  new BookingCreationException("Event is not present please try again shortly");
        }else{
            log.info("event with code {} found creating if time remains ", booking.getEventCode());
            long abs = Math.abs(Duration.between(event.getEndDateTime(), LocalDateTime.now()).toMinutes());
            if(abs < 20){
                throw  new BookingTimePassedException("Booking for this event has been closed.");
            }
        }
        Booking bookingEntity = BookingMapper.toBooking(booking);
        bookingEntity.setBookingCode(Utils.generateBookingCode(booking.getEventCode()));
        bookingEntity.setTicketPrice(event.getTicketPrice());
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
    public Booking updateBooking(Long bookingId, UpdateBookingRequest updatedBooking) {
        Optional<Booking> existingBooking = bookingRepository.findById(bookingId);
        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            if (updatedBooking.getStatus() != null) {
                booking.setStatus(updatedBooking.getStatus());
            }
            if (updatedBooking.getReservedForMinutes() > 0) {
                booking.setInvalidAfter(booking.getCreatedDate().plusMinutes(updatedBooking.getReservedForMinutes()));
            }
            if (updatedBooking.getBookingType() != null) {
                booking.setBookingType(updatedBooking.getBookingType());
            }
            return bookingRepository.save(booking);
        } else {
            throw new BookingNotFoundException("booking not found with id " + bookingId);
        }
    }

    @Transactional
    public Booking updateBooking(Long bookingId, Booking updatedBooking) {
        Optional<Booking> existingBooking = bookingRepository.findById(bookingId);
        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            if (updatedBooking.getStatus() != null) {
                booking.setStatus(updatedBooking.getStatus());
            }
            if (updatedBooking.getBookingType() != null) {
                booking.setBookingType(updatedBooking.getBookingType());
            }
            if(updatedBooking.getPaymentStatus()!=null){
                booking.setPaymentStatus(updatedBooking.getPaymentStatus());
            }
            return bookingRepository.save(booking);
        } else {
            throw new BookingNotFoundException("booking not found with id " + bookingId);
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

    @Override
    public BookingResponse getBookingsWithTickets(Long bookingId) {
        Booking booking = bookingRepository.getBookingsWithTickets(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("booking not found with id " + bookingId));
        BookingResponse bookingResponse = BookingMapper.toBookingResponse(booking);
        return  bookingResponse;
    }

    @Override
    public Booking getByBookingCode(String bookingCode) {
        return bookingRepository.findBookingByBookingCode(bookingCode);
    }
}

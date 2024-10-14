package ae.rakbank.bookingservice.mapper;

import ae.rakbank.bookingservice.dto.event.BookingEvent;
import ae.rakbank.bookingservice.dto.event.EventMetadata;
import ae.rakbank.bookingservice.dto.event.UpdateEvent;
import ae.rakbank.bookingservice.dto.request.BookingRequest;
import ae.rakbank.bookingservice.dto.response.BookingResponse;
import ae.rakbank.bookingservice.model.Booking;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class BookingMapper {


    public static Booking toBooking(BookingRequest bookingRequest) {
        return Booking.builder()
                .customerId(bookingRequest.getCustomerId())
                .username(bookingRequest.getUsername())
                .fullName(bookingRequest.getFullName())
                .eventId(bookingRequest.getEventId())
                .status(bookingRequest.getStatus())
                .eventCode(bookingRequest.getEventCode())
                .paymentStatus(bookingRequest.getPaymentStatus())
                .bookingType(bookingRequest.getBookingType())
                .invalidAfter(LocalDateTime.now().plusMinutes(bookingRequest.getReserveForMinutes()))
                .build();
    }

    /**
     * Converts Booking entity to BookingResponse record.
     *
     * @param booking the Booking entity
     * @return BookingResponse record
     */
    public static BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getFullName(),
                booking.getUsername(),
                booking.getCustomerId(),
                booking.getEventId(),
                booking.getStatus(),
                booking.getPaymentStatus(),
                booking.getCreatedDate(),
                booking.getTickets().size(),
                booking.getTickets().stream()
                        .map(ticket -> new BookingResponse.TicketResponse(
                                ticket.getTicketNumber(),
                                ticket.getTicketType()
                        ))
                        .collect(Collectors.toSet())
        );
    }

    public static EventMetadata mapToEventMetadata(UpdateEvent updateEvent) {
        return EventMetadata.builder()
                .eventCode(updateEvent.getCode())
                .eventName(updateEvent.getEventName())
                .eventId(updateEvent.getEventId())
                .status(updateEvent.getStatus())
                .startDateTime(LocalDateTime.parse(updateEvent.getStartDateTime()))
                .ticketPrice(updateEvent.getTicketPrice())
                .venue(updateEvent.getVenue())
                .endDateTime(LocalDateTime.parse(updateEvent.getEndDateTime()))
                .build();
    }

    public static BookingEvent toBookingEvent(Booking booking) {
        return BookingEvent.builder()
                .bookingId(booking.getId())
                .fullName(booking.getFullName())
                .username(booking.getUsername())
                .bookingCode(booking.getBookingCode())
                .eventId(booking.getEventId())
                .eventCode(booking.getEventCode())
                .bookingType(booking.getBookingType())
                .status(booking.getStatus())
                .ticketPrice(booking.getTicketPrice())
                .ticketQuantity(booking.getTicketQuantity())
                .paymentStatus(booking.getPaymentStatus())
                .createdDate(booking.getCreatedDate())
                .invalidAfter(booking.getInvalidAfter())
                .build();
    }
}

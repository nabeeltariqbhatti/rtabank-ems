package ae.rakbank.eventbookingservice.mapper;

import ae.rakbank.eventbookingservice.dto.request.BookingRequest;
import ae.rakbank.eventbookingservice.dto.response.BookingResponse;
import ae.rakbank.eventbookingservice.model.Booking;

import java.util.stream.Collectors;

public class BookingMapper {


    public static Booking toBooking(BookingRequest bookingRequest) {
        return Booking.builder()
                .eventId(bookingRequest.getEventId())
                .status(bookingRequest.getStatus())
                .paymentStatus(bookingRequest.getPaymentStatus())
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
}

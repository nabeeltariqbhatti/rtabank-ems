package ae.rakbank.bookingservice.dto.request;

import ae.rakbank.bookingservice.model.Booking;
import ae.rakbank.bookingservice.model.Ticket;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingRequest implements Serializable {

    private Booking.Status status;
    private Ticket.TicketType ticketType;
    private int reservedForMinutes;
    private Booking.BookingType bookingType;
}

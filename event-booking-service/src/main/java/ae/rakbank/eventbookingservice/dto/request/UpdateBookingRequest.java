package ae.rakbank.eventbookingservice.dto.request;

import ae.rakbank.eventbookingservice.model.Booking;
import ae.rakbank.eventbookingservice.model.Ticket;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

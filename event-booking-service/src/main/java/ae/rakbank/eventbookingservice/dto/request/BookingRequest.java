package ae.rakbank.eventbookingservice.dto.request;

import ae.rakbank.eventbookingservice.model.Booking;
import ae.rakbank.eventbookingservice.model.Ticket;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class BookingRequest implements Serializable {

    @NotNull(message = "Event ID must not be null")
    private Long eventId;
    @NotNull(message = "Booking status must not be null")
    private Booking.Status status;
    @NotNull(message = "Payment status must not be null")
    private Booking.PaymentStatus paymentStatus;
    @Min(value = 1, message = "At least one ticket must be booked")
    private int numberOfTickets;
    @NotNull(message = "Ticket type must not be null")
    private Ticket.TicketType ticketType;



}

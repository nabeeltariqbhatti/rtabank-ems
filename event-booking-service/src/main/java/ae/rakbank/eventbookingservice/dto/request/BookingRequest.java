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
    @NotNull(message = "Event ID must not be null")
    private String eventCode;
    private Booking.Status status=Booking.Status.PENDING;
    private Booking.PaymentStatus paymentStatus= Booking.PaymentStatus.UNPAID;
    @Min(value = 1, message = "At least one ticket must be booked")
    private int numberOfTickets;
    private Booking.BookingType bookingType;
    @NotNull(message = "Ticket type must not be null")
    private Ticket.TicketType ticketType;
    private int reserveForMinutes;



}

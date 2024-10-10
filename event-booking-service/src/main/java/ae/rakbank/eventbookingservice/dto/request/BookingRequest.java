package ae.rakbank.eventbookingservice.dto.request;

import ae.rakbank.eventbookingservice.model.Booking;
import ae.rakbank.eventbookingservice.model.Ticket;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class BookingRequest implements Serializable {

    @NotNull(message = "Event ID must not be null")
    private Long eventId;
    @NotBlank(message = "Event code is missing")
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

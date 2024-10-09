package ae.rakbank.eventbookingservice.dto.response;

import ae.rakbank.eventbookingservice.model.Booking;
import ae.rakbank.eventbookingservice.model.Ticket;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public record BookingResponse(
        Long bookingId,
        Long eventId,
        Booking.Status status,
        Booking.PaymentStatus paymentStatus,
        LocalDateTime bookingDate,
        int numberOfTickets,
        Set<TicketResponse> tickets
) implements Serializable {
    public record TicketResponse(
            String ticketNumber,
            Ticket.TicketType ticketType
    ) implements Serializable {
    }
}

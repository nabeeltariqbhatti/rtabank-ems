package ae.rakbank.bookingservice.dto.response;

import ae.rakbank.bookingservice.model.Booking;
import ae.rakbank.bookingservice.model.Ticket;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public record BookingResponse(
        Long bookingId,
        String fullName,
        String username,
        Long customerId,
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

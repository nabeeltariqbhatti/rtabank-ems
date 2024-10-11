package ae.rakbank.eventbookingservice.dto.event;

import ae.rakbank.eventbookingservice.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private String eventName;
    private LocalDateTime eventDate;
    private String eventLocation;
    private String userName;
    private String ticketType;
    private int numberOfTickets;
    private BigDecimal paymentAmount;
    private NotificationType notificationType;

    public enum NotificationType {
        PENDING,
        CONFIRMED,
        CANCELED,
        COMPLETED,
        GONE
    }

}

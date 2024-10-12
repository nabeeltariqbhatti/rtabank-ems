package ae.rakbank.notificationsservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity  {
    

    private String eventName;
    private LocalDateTime eventDate;
    private String bookingCode;

    private String eventLocation;

    private String userName;
    private String fullName;
    private String ticketType;

    private int numberOfTickets;
    @Enumerated(value = EnumType.STRING)
    private NotificationType notificationType;
     @Enumerated(value = EnumType.STRING)
    private Status status;

    private BigDecimal paymentAmount;

    public enum NotificationType {
        PENDING,
        CONFIRMED,
        CANCELED,
        COMPLETED,
        GONE
    }

    public enum Status {
        PENDING,
        SENT,
        FAILED
    }


}

package ae.rakbank.eventpaymentservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent implements Serializable {

    private Long bookingId;
    private String bookingCode;
    private Long eventId;
    private String eventCode;
    private BookingType bookingType;
    private Status status;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdDate;
    private LocalDateTime invalidAfter;

    public enum BookingType {
        ONLINE,
        OFFLINE,
        CORPORATE,
        GROUP
    }
    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELED,
        COMPLETED
    }
    public enum PaymentStatus {
        PAID,
        UNPAID,
        REFUNDED,
        FAILED
    }
}
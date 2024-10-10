package ae.rakbank.eventpaymentservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent implements Serializable {

    //TODO add missing fields
    private Long bookingId;
    private String bookingCode;
    private Long eventId;
    private String eventCode;
    private BookingType bookingType;
    private Status status;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdDate;
    private LocalDateTime invalidAfter;
    private int ticketQuantity;
    private BigDecimal ticketPrice;
    private Long purchaseId;
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
        COMPLETED,
        GONE
    }
    public enum PaymentStatus {
        PAID,
        UNPAID,
        REFUNDED,
        FAILED,
        NOT_RECEIVED
    }
}
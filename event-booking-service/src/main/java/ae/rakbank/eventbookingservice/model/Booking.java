package ae.rakbank.eventbookingservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "bookings", indexes ={
        @Index(name = "booking_created_at_idx", columnList = "createdDate"),
        @Index(name = "booking_code_idx", columnList = "bookingCode"),
        @Index(name = "booking_event_idx", columnList = "eventId")

})
public class Booking extends BaseEntity {

    private Long eventId;

    private String bookingCode;

    @Enumerated(EnumType.STRING)
    private BookingType bookingType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private List<Ticket> tickets;

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

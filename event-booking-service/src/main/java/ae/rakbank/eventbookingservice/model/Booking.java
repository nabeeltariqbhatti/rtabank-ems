package ae.rakbank.eventbookingservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private String eventCode;

    @Enumerated(EnumType.STRING)
    private BookingType bookingType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "booking")
    @JsonBackReference
    private List<Ticket> tickets;

    private LocalDateTime invalidAfter;
    public void addTicket(Ticket ticket){
        if(this.tickets == null) this.tickets = new ArrayList<>();
        tickets.add(ticket);
        ticket.setBooking(this);
    }

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

package ae.rakbank.bookingservice.model;

import ae.rakbank.bookingservice.events.BookingEntityListener;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners(BookingEntityListener.class)
public class Booking extends BaseEntity {



    private String bookingCode;
    private String eventCode;
    private Long eventId;
    private String username;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private BookingType bookingType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private BigDecimal ticketPrice;
    private int ticketQuantity;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "booking")
    @JsonBackReference
    private List<Ticket> tickets;

    private LocalDateTime invalidAfter;
    private String purchaseId;
    private Long customerId;
    public void addTicket(Ticket ticket){
        if(this.tickets == null) this.tickets = new ArrayList<>();
        tickets.add(ticket);
        ticket.setBooking(this);
        this.ticketQuantity++;
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

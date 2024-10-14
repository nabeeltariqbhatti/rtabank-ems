package ae.rakbank.bookingservice.dto.event;

import ae.rakbank.bookingservice.model.Booking;
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

    private Long bookingId;
    private String bookingCode;
    private String eventCode;
    private Long eventId;
    private String username;
    private String fullName;
    private Booking.BookingType bookingType;
    private Booking.Status status;
    private Booking.PaymentStatus paymentStatus;
    private LocalDateTime createdDate;
    private LocalDateTime invalidAfter;
    private int ticketQuantity;
    private BigDecimal ticketPrice;
}
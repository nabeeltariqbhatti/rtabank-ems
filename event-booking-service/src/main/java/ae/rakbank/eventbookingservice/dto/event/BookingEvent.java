package ae.rakbank.eventbookingservice.dto.event;

import ae.rakbank.eventbookingservice.model.Booking;
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
    private Booking.BookingType bookingType;
    private Booking.Status status;
    private Booking.PaymentStatus paymentStatus;
    private LocalDateTime createdDate;
    private LocalDateTime invalidAfter;
}
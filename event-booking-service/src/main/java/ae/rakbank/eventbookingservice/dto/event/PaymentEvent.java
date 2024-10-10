package ae.rakbank.eventbookingservice.dto.event;

import ae.rakbank.eventbookingservice.model.Booking;
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
public class PaymentEvent implements Serializable {

    private String purchaseCode;
    private Long customerId;
    private String bookingCode;
    private String eventCode;
    private Booking.PaymentStatus paymentStatus;
    private LocalDateTime purchaseDate;
    private BigDecimal totalAmount;


}

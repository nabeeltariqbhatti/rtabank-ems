package ae.rakbank.eventpaymentservice.mapper;

import ae.rakbank.eventpaymentservice.dto.event.BookingEvent;
import ae.rakbank.eventpaymentservice.models.Purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentMapper {

    public static Purchase toPurchase(BookingEvent bookingEvent) {
        if (bookingEvent == null) {
            return null;
        }
       return Purchase.builder()
                .purchaseCode(UUID.randomUUID().toString())
                .customerId(1L)
                .purchaseDate(LocalDateTime.now())
                .paymentStatus(Purchase.PaymentStatus.UNPAID)
               .bookingCode(bookingEvent.getBookingCode())
               .eventCode(bookingEvent.getEventCode())
                .totalAmount(BigDecimal.valueOf(bookingEvent.getTicketPrice().doubleValue()* bookingEvent.getTicketQuantity()))
                .build();
    }

}

package ae.rakbank.eventpaymentservice.mapper;

import ae.rakbank.eventpaymentservice.dto.event.BookingEvent;
import ae.rakbank.eventpaymentservice.dto.event.PaymentEvent;
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

    public static PaymentEvent toPaymentEvent(Purchase purchase) {
        if (purchase == null) {
            return null;
        }

        return PaymentEvent.builder()
                .purchaseCode(purchase.getPurchaseCode())
                .customerId(purchase.getCustomerId())
                .bookingCode(purchase.getBookingCode())
                .eventCode(purchase.getEventCode())
                .paymentStatus(purchase.getPaymentStatus())
                .purchaseDate(purchase.getPurchaseDate())
                .totalAmount(purchase.getTotalAmount())
                .build();
    }

    public static Purchase toPurchase(PaymentEvent paymentEvent) {
        if (paymentEvent == null) {
            return null;
        }

        return Purchase.builder()
                .purchaseCode(paymentEvent.getPurchaseCode())
                .customerId(paymentEvent.getCustomerId())
                .bookingCode(paymentEvent.getBookingCode())
                .eventCode(paymentEvent.getEventCode())
                .paymentStatus(paymentEvent.getPaymentStatus())
                .purchaseDate(paymentEvent.getPurchaseDate())
                .totalAmount(paymentEvent.getTotalAmount())
                .build();
    }

}

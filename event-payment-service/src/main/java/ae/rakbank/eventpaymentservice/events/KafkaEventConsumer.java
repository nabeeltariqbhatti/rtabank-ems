package ae.rakbank.eventpaymentservice.events;


import ae.rakbank.eventpaymentservice.cache.EventCache;
import ae.rakbank.eventpaymentservice.dto.event.BookingEvent;
import ae.rakbank.eventpaymentservice.models.Purchase;
import ae.rakbank.eventpaymentservice.service.PurchaseService;
import ae.rakbank.eventpaymentservice.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@Slf4j
public final class KafkaEventConsumer implements EventListener<Object> {
    private final PurchaseService purchaseService;

    private final KafkaEventProducer kafkaEventProducer;

    public KafkaEventConsumer(PurchaseService purchaseService, KafkaEventProducer kafkaEventProducer) {
        this.purchaseService = purchaseService;
        this.kafkaEventProducer = kafkaEventProducer;
    }

    @KafkaListener(topics = "${rakbank.events.topic.booking.to.payment.topic}", groupId = "event-management")
    public void consumeEvent(Object event) {
        var consumerRecord = (ConsumerRecord<String, String>) event;
        BookingEvent bookingEvent = Utils.toObject(consumerRecord.value(), BookingEvent.class);

        log.info("Received event: " + bookingEvent);
        switch (Objects.requireNonNull(bookingEvent).getStatus()) {
            case PENDING -> {
                log.info("create purchase for the booking as its reserved");
                Purchase purchaseForBooking = purchaseService.createPurchaseForBooking(bookingEvent);
                bookingEvent.setPurchaseId(purchaseForBooking.getId());
                EventCache.updateEvent(bookingEvent.getBookingCode(), bookingEvent);
                log.info("booking service will receive update shortly.");
            }
            case CANCELED -> {
                Purchase purchase = purchaseService.getByBookingCode(bookingEvent.getBookingCode());
                if(purchase == null) return;
                if (Purchase.PaymentStatus.PAID.equals(purchase.getPaymentStatus())) {
                    log.info("process refund as booking is cancelled");
                    purchase.setPaymentStatus(Purchase.PaymentStatus.PAID);
                    purchaseService.updateStatus(purchase);
                } else {
                    log.info("no refund is needed just evicting from cache");

                }
                EventCache.removeEvent(bookingEvent.getEventCode());

            }
            default -> log.info("not a valid action to do with booking");
        }
        log.info("update cache");
    }


}

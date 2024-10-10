package ae.rakbank.eventpaymentservice.events;


import ae.rakbank.eventpaymentservice.cache.EventCache;
import ae.rakbank.eventpaymentservice.dto.event.BookingEvent;
import ae.rakbank.eventpaymentservice.dto.request.PaymentRequest;
import ae.rakbank.eventpaymentservice.exception.PaymentFailedException;
import ae.rakbank.eventpaymentservice.models.Purchase;
import ae.rakbank.eventpaymentservice.service.PurchaseService;
import ae.rakbank.eventpaymentservice.utils.Utils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
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
        System.out.println("Received event: " + bookingEvent);
        switch (Objects.requireNonNull(bookingEvent).getStatus()){
            case PENDING ->{
                System.out.println("create purchase for the booking as its reserved");
                Purchase purchaseForBooking = purchaseService.createPurchaseForBooking(bookingEvent);
                bookingEvent.setPurchaseId(purchaseForBooking.getId());
                EventCache.updateEvent(bookingEvent.getBookingCode(), bookingEvent );
                //TODO: send purchase id back to booking or after creation,

            }
            case CANCELED -> System.out.println("process refund as booking is cancelled");
            default -> System.out.println("not a valid action to do with booking");
        }
        System.out.println("update cache");
    }


}

package ae.rakbank.eventpaymentservice.events;


import ae.rakbank.eventpaymentservice.dto.event.BookingEvent;
import ae.rakbank.eventpaymentservice.service.PurchaseService;
import ae.rakbank.eventpaymentservice.utils.Utils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public final class KafkaEventConsumer implements EventListener<Object> {
    private final PurchaseService purchaseService;

    public KafkaEventConsumer(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @KafkaListener(topics = "${rakbank.events.topic.booking.to.payment.topic}", groupId = "event-management")
    public void consumeEvent(Object event) {
        var consumerRecord = (ConsumerRecord<String, String>) event;
        BookingEvent bookingEvent = Utils.toObject(consumerRecord.value(), BookingEvent.class);
        System.out.println("Received event: " + bookingEvent);
        switch (Objects.requireNonNull(bookingEvent).getStatus()){
            case PENDING ->{
                System.out.println("create purchase for the booking as its reserved");
                purchaseService.
            }
            case CANCELED -> System.out.println("process refund as booking is cancelled");
            default -> System.out.println("not a valid action to do with booking");
        }
        System.out.println("update cache");
    }


}

package ae.rakbank.eventbookingservice.events;

import ae.rakbank.eventbookingservice.cache.EventCache;
import ae.rakbank.eventbookingservice.dto.event.EventMetadata;
import ae.rakbank.eventbookingservice.dto.event.PaymentEvent;
import ae.rakbank.eventbookingservice.dto.event.UpdateEvent;
import ae.rakbank.eventbookingservice.mapper.BookingMapper;
import ae.rakbank.eventbookingservice.utils.Utils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public final class PaymentToBookingConsumer implements EventListener<ConsumerRecord<String,String>> {

    @KafkaListener(topics = "${rakbank.events.topic.payment.to.booking.topic}", groupId = "event-management")
    public void consumeEvent(ConsumerRecord<String,String> event) {
        if(event == null) return;
        PaymentEvent paymentEvent = Utils.toObject(event.value(), PaymentEvent.class);
        System.out.println("Received event: " + paymentEvent);
    }



}

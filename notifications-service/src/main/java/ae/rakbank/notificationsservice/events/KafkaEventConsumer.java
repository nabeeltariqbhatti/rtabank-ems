package ae.rakbank.notificationsservice.events;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public final class KafkaEventConsumer implements EventListener<Object> {


    private final KafkaEventProducer kafkaEventProducer;

    public KafkaEventConsumer(KafkaEventProducer kafkaEventProducer) {
        this.kafkaEventProducer = kafkaEventProducer;
    }

//    @KafkaListener(topics = "${rakbank.events.topic.booking.to.notifications.topic}", groupId = "notifications")
    public void consumeEvent(Object event) {
        log.info("event received {} " ,event);
    }


}

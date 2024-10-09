package ae.rakbank.eventbookingservice.events;

import ae.rakbank.eventbookingservice.dto.event.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class KafkaEventConsumer implements EventListener<Object> {

//    private static final Logger log = LoggerFactory.getLogger(KafkaEventConsumer.class);

//    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Value("${kafka.topic.events:event-updates-topic}")
    private String topic;

    @Override
    public void consumeEvent(Object event) {

    }
//
//    public KafkaEventConsumer(KafkaTemplate<String, Event> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }


}

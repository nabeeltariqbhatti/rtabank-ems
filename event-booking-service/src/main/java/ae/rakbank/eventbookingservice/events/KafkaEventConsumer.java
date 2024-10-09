package ae.rakbank.eventbookingservice.events;

import ae.rakbank.eventmanagementservice.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class KafkaEventConsumer implements EventListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventConsumer.class);

    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Value("${kafka.topic.events}")
    private String topic;
//
//    public KafkaEventConsumer(KafkaTemplate<String, Event> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }

    @Override
    public void consumeEvent(Event event) {
        // Add your Kafka-based consumption logic here
    }
}

package ae.rakbank.eventmanagementservice.events;

import ae.rakbank.eventmanagementservice.dtos.event.UpdateEvent;
import ae.rakbank.eventmanagementservice.model.Event;
import org.springframework.stereotype.Component;

@Component("kafkaEventProducer")
public final class KafkaEventProducer implements EventProducer {
    @Override
    public void produce(UpdateEvent event) {

    }

//    private final KafkaTemplate<String, Event> kafkaTemplate;
//    private final String topic;
//
//    public KafkaEventProducer(KafkaTemplate<String, Event> kafkaTemplate, String topic) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.topic = topic;
//    }
//
//    @Override
//    public void produce(Event event) {
//        // Logic to send event to Kafka
//        kafkaTemplate.send(topic, event);
//        System.out.println("Event sent to Kafka: " + event);
//    }
}

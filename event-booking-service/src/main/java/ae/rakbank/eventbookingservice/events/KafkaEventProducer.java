package ae.rakbank.eventbookingservice.events;

import org.springframework.stereotype.Component;

/**
 * for the simplicity kafka is not being used hower after adding dep or enabling config it will
 * easy be used by adding relevant queues
 */
@Component("kafkaEventProducer")
public final class KafkaEventProducer implements EventProducer<Object> {
    @Override
    public void produce(Object event) {

    }

}

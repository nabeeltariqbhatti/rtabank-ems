package ae.rakbank.eventmanagementservice.events;

import ae.rakbank.eventmanagementservice.model.Event;

public sealed interface EventProducer permits RestEventProducer, KafkaEventProducer {

    void produce(Event event);
}

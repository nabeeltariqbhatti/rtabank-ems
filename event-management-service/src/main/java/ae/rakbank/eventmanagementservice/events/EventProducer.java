package ae.rakbank.eventmanagementservice.events;

import ae.rakbank.eventmanagementservice.dtos.event.UpdateEvent;
import ae.rakbank.eventmanagementservice.model.Event;

public sealed interface EventProducer permits RestEventProducer, KafkaEventProducer {

   <T> void produce(T event, String target);
}

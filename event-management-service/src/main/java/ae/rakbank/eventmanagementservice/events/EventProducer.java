package ae.rakbank.eventmanagementservice.events;

public sealed interface EventProducer permits KafkaEventProducer {

   <T> void produce(T event, String target);
}

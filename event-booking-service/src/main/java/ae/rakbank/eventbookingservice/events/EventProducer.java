package ae.rakbank.eventbookingservice.events;

public sealed interface EventProducer permits KafkaEventProducer {

   <T> void produce(T event, String target);
}

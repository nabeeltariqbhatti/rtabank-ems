package ae.rakbank.notificationsservice.events;

public sealed interface EventProducer permits KafkaEventProducer {

   <T> void produce(T event, String target);
}

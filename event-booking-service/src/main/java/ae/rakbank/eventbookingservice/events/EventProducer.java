package ae.rakbank.eventbookingservice.events;

;

public sealed interface EventProducer<T> permits RestEventProducer, KafkaEventProducer {

    void produce(T event);
}

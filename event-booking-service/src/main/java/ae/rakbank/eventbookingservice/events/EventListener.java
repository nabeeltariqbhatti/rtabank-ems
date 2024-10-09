package ae.rakbank.eventbookingservice.events;


import ae.rakbank.eventbookingservice.dto.event.Event;

public sealed interface EventListener<T> permits RestEventConsumer, KafkaEventConsumer {

    /**
     * Method to consume an Event.
     * 
     * @param event The event to be consumed
     */
    void consumeEvent(T event);
}

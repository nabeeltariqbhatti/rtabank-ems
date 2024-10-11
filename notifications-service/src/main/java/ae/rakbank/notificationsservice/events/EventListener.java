package ae.rakbank.notificationsservice.events;


public sealed interface EventListener<T> permits KafkaEventConsumer {

    /**
     * Method to consume an Event.
     * 
     * @param event The event to be consumed
     */
    void consumeEvent(T event);
}

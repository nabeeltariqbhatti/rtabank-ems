package ae.rakbank.eventbookingservice.events;



public sealed interface EventListener permits RestEventConsumer, KafkaEventConsumer {

    /**
     * Method to consume an Event.
     * 
     * @param event The event to be consumed
     */
    void consumeEvent(Event event);
}

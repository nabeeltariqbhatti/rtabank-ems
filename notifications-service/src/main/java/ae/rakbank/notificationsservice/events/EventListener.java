package ae.rakbank.notificationsservice.events;


public interface EventListener<T> {

    /**
     * Method to consume an Event.
     * 
     * @param event The event to be consumed
     */
    void consumeEvent(T event);
}

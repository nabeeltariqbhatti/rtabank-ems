package ae.rakbank.bookingservice.events;


public sealed interface EventListener<T> permits EventsServiceToBookingConsumer, PaymentToBookingConsumer {

    /**
     * Method to consume an Event.
     * 
     * @param event The event to be consumed
     */
    void consumeEvent(T event);
}

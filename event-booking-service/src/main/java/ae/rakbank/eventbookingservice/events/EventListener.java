package ae.rakbank.eventbookingservice.events;


public sealed interface EventListener<T> permits BookingToPaymentConsumer, PaymentToBookingConsumer {

    /**
     * Method to consume an Event.
     * 
     * @param event The event to be consumed
     */
    void consumeEvent(T event);
}

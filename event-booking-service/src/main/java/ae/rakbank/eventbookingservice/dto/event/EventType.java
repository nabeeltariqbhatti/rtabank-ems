package ae.rakbank.eventbookingservice.dto.event;

public sealed interface EventType permits BookingEventType, PaymentEventType {
    String getType();
}

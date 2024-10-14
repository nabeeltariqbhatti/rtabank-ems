package ae.rakbank.bookingservice.dto.event;

public sealed interface EventType permits BookingEventType, PaymentEventType {
    String getType();
}

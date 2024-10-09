package ae.rakbank.eventbookingservice.dto.event;

public enum BookingEventType implements EventType {
    BOOKING_CREATED,
    BOOKING_UPDATED,
    BOOKING_CANCELLED;

    @Override
    public String getType() {
        return this.name();
    }
}

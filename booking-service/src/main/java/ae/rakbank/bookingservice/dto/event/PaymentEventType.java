package ae.rakbank.bookingservice.dto.event;

public enum PaymentEventType implements EventType {
    PAYMENT_SUCCESS,
    PAYMENT_FAILED,
    PAYMENT_PENDING;

    @Override
    public String getType() {
        return this.name();
    }
}

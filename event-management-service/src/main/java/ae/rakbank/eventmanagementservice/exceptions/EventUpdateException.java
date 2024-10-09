package ae.rakbank.eventmanagementservice.exceptions;

public class EventUpdateException extends RuntimeException {
    public EventUpdateException(String message) {
        super(message);
    }

    public EventUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}

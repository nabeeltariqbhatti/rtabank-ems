package ae.rakbank.eventmanagementservice.exceptions;

public class EventCreationException extends RuntimeException {
    public EventCreationException(String message) {
        super(message);
    }

    public EventCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

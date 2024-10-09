package ae.rakbank.eventmanagementservice.exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {
        super(message);
    }

    public EventNotFoundException(Long eventId) {
        super("Event with ID " + eventId + " not found.");
    }
}

package ae.rakbank.eventmanagementservice.exceptions;

public class InvalidEventDataException extends RuntimeException {
    public InvalidEventDataException(String message) {
        super(message);
    }
}

package ae.rakbank.bookingservice.exceptions;

public class BookingCreationException extends RuntimeException {
    public BookingCreationException(String message) {
        super(message);
    }

    public BookingCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

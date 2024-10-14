package ae.rakbank.bookingservice.exceptions;

public class BookingCancellationException extends RuntimeException {
    public BookingCancellationException(String message) {
        super(message);
    }

    public BookingCancellationException(String message, Throwable cause) {
        super(message, cause);
    }
}

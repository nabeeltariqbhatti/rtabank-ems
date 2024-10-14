package ae.rakbank.bookingservice.exceptions;

public class BookingUpdateException extends RuntimeException {
    public BookingUpdateException(String message) {
        super(message);
    }

    public BookingUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}

package ae.rakbank.bookingservice.exceptions;

public class BookingTimePassedException extends RuntimeException {
    public BookingTimePassedException(String message) {
        super(message);
    }

    public BookingTimePassedException(String message, Throwable cause) {
        super(message, cause);
    }
}

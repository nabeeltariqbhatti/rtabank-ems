package ae.rakbank.eventpaymentservice.exception;

public class RetryLaterException extends RuntimeException {


    public RetryLaterException(String message) {
        super(message);

    }

}

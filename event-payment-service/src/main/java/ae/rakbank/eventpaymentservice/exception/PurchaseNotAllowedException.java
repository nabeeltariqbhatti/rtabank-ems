package ae.rakbank.eventpaymentservice.exception;

public class PurchaseNotAllowedException extends RuntimeException {

    public PurchaseNotAllowedException(String message) {
        super(message);
    }
}

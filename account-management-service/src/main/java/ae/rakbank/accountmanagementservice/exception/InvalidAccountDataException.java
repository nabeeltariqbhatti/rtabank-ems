package ae.rakbank.accountmanagementservice.exception;

public class InvalidAccountDataException extends RuntimeException {
    public InvalidAccountDataException(String message) {
        super("Invalid account data: " + message);
    }
}

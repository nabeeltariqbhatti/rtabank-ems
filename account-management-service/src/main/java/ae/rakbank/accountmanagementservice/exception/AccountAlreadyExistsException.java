package ae.rakbank.accountmanagementservice.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String email) {
        super("Account already exists with email: " + email);
    }
}
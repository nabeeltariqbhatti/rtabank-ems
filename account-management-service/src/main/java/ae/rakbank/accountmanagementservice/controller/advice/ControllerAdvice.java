package ae.rakbank.accountmanagementservice.controller.advice;


import ae.rakbank.accountmanagementservice.exception.AccountAlreadyExistsException;
import ae.rakbank.accountmanagementservice.exception.AccountNotFoundException;
import ae.rakbank.accountmanagementservice.exception.InvalidAccountDataException;
import ae.rakbank.accountmanagementservice.model.Account;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    private final HttpServletRequest request;

    public ControllerAdvice(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        return getProblemDetail(HttpStatus.BAD_REQUEST, String.join(",", errorMessages));

    }




    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralExceptions(Exception ex) {
        return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
    }

    private ProblemDetail getProblemDetail(HttpStatus internalServerError, String ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(internalServerError);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex);
        return problemDetail;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFoundException(AccountNotFoundException ex) {
        return getProblemDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidAccountDataException.class)
    public ProblemDetail handleInvalidAccountDataException(AccountNotFoundException ex) {
        return getProblemDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ProblemDetail handleAccountAlreadyExistsException(AccountAlreadyExistsException ex) {
        return getProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }



}

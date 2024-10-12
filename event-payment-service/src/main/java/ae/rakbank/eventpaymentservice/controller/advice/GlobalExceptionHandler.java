package ae.rakbank.eventpaymentservice.controller.advice;

import ae.rakbank.eventpaymentservice.exception.PaymentFailedException;
import ae.rakbank.eventpaymentservice.exception.PaymentNotFoundException;
import ae.rakbank.eventpaymentservice.exception.PurchaseNotAllowedException;
import ae.rakbank.eventpaymentservice.exception.RetryLaterException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(PurchaseNotAllowedException.class)
    public ProblemDetail handlePurchaseNotAllowedException(PurchaseNotAllowedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(RetryLaterException.class)
    public  ProblemDetail handleRetryLaterException(RetryLaterException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }
    @ExceptionHandler(PaymentFailedException.class)
    public  ProblemDetail paymentFailed(PaymentFailedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public  ProblemDetail paymentNotFoundException(PaymentNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(String.join(",",errorMessages));
        return  problemDetail;

    }
}

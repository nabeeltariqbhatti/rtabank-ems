package ae.rakbank.eventbookingservice.controller.advice;

import ae.rakbank.eventbookingservice.exceptions.BookingNotFoundException;
import ae.rakbank.eventbookingservice.exceptions.BookingTimePassedException;
import ae.rakbank.eventbookingservice.exceptions.BookingCreationException;
import ae.rakbank.eventbookingservice.exceptions.BookingUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    public GlobalExceptionHandler(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(BookingUpdateException.class)
    public ProblemDetail handleBookingUpdateException(BookingUpdateException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(BookingTimePassedException.class)
    public ProblemDetail handleBookingTimePassedException(BookingTimePassedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(BookingCreationException.class)
    public ProblemDetail handleBookingCreationException(BookingCreationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ProblemDetail handleBookingNotFoundException(BookingNotFoundException ex) {
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
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralExceptions(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail("An unexpected error occurred: " + ex.getMessage());
        return problemDetail;
    }
}

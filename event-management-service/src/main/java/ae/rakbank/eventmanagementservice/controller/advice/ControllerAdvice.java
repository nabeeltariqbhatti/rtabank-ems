package ae.rakbank.eventmanagementservice.controller.advice;

import ae.rakbank.eventmanagementservice.exceptions.EventCreationException;
import ae.rakbank.eventmanagementservice.exceptions.EventNotFoundException;
import ae.rakbank.eventmanagementservice.exceptions.EventUpdateException;
import ae.rakbank.eventmanagementservice.exceptions.VenueSlotNotAvailableException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
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
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(String.join(",",errorMessages));
       return  problemDetail;

    }

    @ExceptionHandler(VenueSlotNotAvailableException.class)
    public ProblemDetail handleVenueSlotNotAvailableException(VenueSlotNotAvailableException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }


    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralExceptions(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail("An unexpected error occurred: " + ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ProblemDetail handleEventNotFoundException(EventNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(EventCreationException.class)
    public ProblemDetail handleEventCreationException(EventCreationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(EventUpdateException.class)
    public ProblemDetail handleEventUpdateException(EventUpdateException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create(request.getRequestURL().toString()));
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

}

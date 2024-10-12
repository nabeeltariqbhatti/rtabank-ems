package ae.rakbank.eventpaymentservice.controller;


import ae.rakbank.eventpaymentservice.cache.EventCache;
import ae.rakbank.eventpaymentservice.dto.event.BookingEvent;
import ae.rakbank.eventpaymentservice.dto.request.PaymentRequest;
import ae.rakbank.eventpaymentservice.exception.PaymentFailedException;
import ae.rakbank.eventpaymentservice.exception.PaymentNotFoundException;
import ae.rakbank.eventpaymentservice.exception.PurchaseNotAllowedException;
import ae.rakbank.eventpaymentservice.exception.RetryLaterException;
import ae.rakbank.eventpaymentservice.models.Purchase;
import ae.rakbank.eventpaymentservice.models.Transaction;
import ae.rakbank.eventpaymentservice.service.PurchaseService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;

import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/purchases")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PurchaseService purchaseService;

    @PostMapping("/pay")
    @Retryable(
            retryFor = {
                    SocketTimeoutException.class,
                    DataAccessResourceFailureException.class,
                    DataAccessException.class,
                    RetryLaterException.class
            },
            maxAttempts = 4,
            recover = "recover",
            backoff = @Backoff(delay = 2000)
    )
    @CircuitBreaker(name = "payment")
    public ResponseEntity<?> paymentForBooking(@RequestParam String bookingCode,
                                               @Valid @RequestBody PaymentRequest paymentRequest,
                                               @RequestHeader(name = "Idempotency-Key") String idempotencyKey) {
        BookingEvent bookingEvent = EventCache.getEvent(bookingCode);
        if (bookingEvent == null) {
            throw new RetryLaterException("Please try later");
        }
        if (LocalDateTime.now().isAfter(bookingEvent.getInvalidAfter())) {
            EventCache.removeEvent(bookingCode);
            throw new PurchaseNotAllowedException("Booking has been expired.");
        }
        Long purchaseId = bookingEvent.getPurchaseId();
        Purchase purchaseById = purchaseService.getPurchaseById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("No payment is expected found to be made."));
        Transaction transaction = new Transaction();
        try {
            purchaseById.setPurchaseDate(LocalDateTime.now());
            purchaseById.setPaymentStatus(Purchase.PaymentStatus.PAID);
            transaction.setAmount(purchaseById.getTotalAmount());
            transaction.setTransactionType(Transaction.TransactionType.PAYMENT);
            transaction.setTransactionStatus(Transaction.TransactionStatus.SUCCESS);
            purchaseById.addTransaction(transaction);
            purchaseById = purchaseService.updatePurchase(purchaseById.getId(), purchaseById, idempotencyKey);
        } catch (Exception e) {
            transaction.setTransactionStatus(Transaction.TransactionStatus.FAILED);
            throw new PaymentFailedException("payment failed ");
        }
        return ResponseEntity.ok(purchaseById);
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchases);
    }


    @GetMapping("/{purchaseId}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Long purchaseId) {
        Optional<Purchase> purchase = purchaseService.getPurchaseById(purchaseId);
        return purchase.map(ResponseEntity::ok)
                .orElseThrow(() ->
                        new PaymentNotFoundException("payment for %d not found".formatted(purchaseId)));
    }


}

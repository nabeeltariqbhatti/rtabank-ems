package ae.rakbank.eventpaymentservice.controller;


import ae.rakbank.eventpaymentservice.cache.EventCache;
import ae.rakbank.eventpaymentservice.dto.event.BookingEvent;
import ae.rakbank.eventpaymentservice.dto.request.PaymentRequest;
import ae.rakbank.eventpaymentservice.exception.PaymentFailedException;
import ae.rakbank.eventpaymentservice.exception.PurchaseNotAllowedException;
import ae.rakbank.eventpaymentservice.exception.RetryLaterException;
import ae.rakbank.eventpaymentservice.models.Purchase;
import ae.rakbank.eventpaymentservice.models.Transaction;
import ae.rakbank.eventpaymentservice.service.PurchaseService;
import ae.rakbank.eventpaymentservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(@Valid @RequestBody Purchase purchase) {
        Purchase savedPurchase = purchaseService.createPurchase(purchase);
        return new ResponseEntity<>(savedPurchase, HttpStatus.CREATED);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> paymentForBooking(@RequestParam String bookingCode,
                                               @Valid @RequestBody PaymentRequest paymentRequest,
                                               @RequestHeader(name = "Idempotency-Key") String idempotencyKey ) {
        BookingEvent bookingEvent = EventCache.getEvent(bookingCode);
        if (bookingEvent == null) {
            throw new RetryLaterException("Please try later or booking has not arrived yet.");
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
            transaction.setPurchase(purchaseById);
            purchaseById.addTransaction(transaction);
            transaction.setAmount(purchaseById.getTotalAmount());
            transaction.setTransactionType(Transaction.TransactionType.PAYMENT);
            transaction.setTransactionStatus(Transaction.TransactionStatus.SUCCESS);
            purchaseById = purchaseService.updatePurchase(purchaseById.getId(), purchaseById,idempotencyKey );
        } catch (Exception e) {
            transaction.setTransactionStatus(Transaction.TransactionStatus.FAILED);
            throw  new PaymentFailedException("payment failed ");
        } finally {

        }
        return ResponseEntity.ok(purchaseById);

    }

    // Get all Purchases
    @GetMapping
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchases);
    }

    // Get a specific Purchase by ID
    @GetMapping("/{purchaseId}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Long purchaseId) {
        Optional<Purchase> purchase = purchaseService.getPurchaseById(purchaseId);
        return purchase.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // Delete a Purchase by ID
    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long purchaseId) {
        purchaseService.deletePurchase(purchaseId);
        return ResponseEntity.noContent().build();
    }

    // Create a Transaction for a specific Purchase
    @PostMapping("/{purchaseId}/transactions")
    public ResponseEntity<Transaction> createTransaction(@PathVariable Long purchaseId, @Valid @RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.createTransaction(purchaseId, transaction);
        if (savedTransaction != null) {
            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all Transactions for a specific Purchase
    @GetMapping("/{purchaseId}/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactionsByPurchase(@PathVariable Long purchaseId) {
        List<Transaction> transactions = transactionService.getAllTransactionsByPurchase(purchaseId);
        if (transactions != null) {
            return ResponseEntity.ok(transactions);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get a specific Transaction by ID
    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long transactionId) {
        Optional<Transaction> transaction = transactionService.getTransactionById(transactionId);
        return transaction.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update a Transaction by ID
    @PutMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long transactionId, @Valid @RequestBody Transaction updatedTransaction) {
        Transaction savedTransaction = transactionService.updateTransaction(transactionId, updatedTransaction);
        if (savedTransaction != null) {
            return ResponseEntity.ok(savedTransaction);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Transaction by ID
    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}

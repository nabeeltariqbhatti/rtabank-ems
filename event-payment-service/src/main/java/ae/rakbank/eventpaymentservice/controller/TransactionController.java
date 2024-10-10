package ae.rakbank.eventpaymentservice.controller;

import ae.rakbank.eventpaymentservice.models.Transaction;
import ae.rakbank.eventpaymentservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/transactions")
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{purchaseId}/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactionsByPurchase(@PathVariable Long purchaseId) {
        List<Transaction> transactions = transactionService.getAllTransactionsByPurchase(purchaseId);
        if (transactions != null) {
            return ResponseEntity.ok(transactions);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long transactionId) {
        Optional<Transaction> transaction = transactionService.getTransactionById(transactionId);
        return transaction.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long transactionId, @Valid @RequestBody Transaction updatedTransaction) {
        Transaction savedTransaction = transactionService.updateTransaction(transactionId, updatedTransaction);
        if (savedTransaction != null) {
            return ResponseEntity.ok(savedTransaction);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}

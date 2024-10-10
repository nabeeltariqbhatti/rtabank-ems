package ae.rakbank.eventpaymentservice.service;



import ae.rakbank.eventpaymentservice.models.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction createTransaction(Long purchaseId, Transaction transaction);
    List<Transaction> getAllTransactionsByPurchase(Long purchaseId);
    Optional<Transaction> getTransactionById(Long transactionId);
    Transaction updateTransaction(Long transactionId, Transaction updatedTransaction);
    void deleteTransaction(Long transactionId);
}

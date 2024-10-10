package ae.rakbank.eventpaymentservice.service.impl;


import ae.rakbank.eventpaymentservice.models.Purchase;
import ae.rakbank.eventpaymentservice.models.Transaction;
import ae.rakbank.eventpaymentservice.repository.PurchaseRepository;
import ae.rakbank.eventpaymentservice.repository.TransactionRepository;
import ae.rakbank.eventpaymentservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final PurchaseRepository purchaseRepository;

    @Override
    public Transaction createTransaction(Long purchaseId, Transaction transaction) {
        Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);
        if (purchase.isPresent()) {
            transaction.setPurchase(purchase.get());
            return transactionRepository.save(transaction);
        }
        return null;
    }

    @Override
    public List<Transaction> getAllTransactionsByPurchase(Long purchaseId) {
        Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);
        return purchase.map(transactionRepository::findByPurchase).orElse(null);
    }

    @Override
    public Optional<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    @Override
    public Transaction updateTransaction(Long transactionId, Transaction updatedTransaction) {
        Optional<Transaction> existingTransaction = transactionRepository.findById(transactionId);
        if (existingTransaction.isPresent()) {
            Transaction transaction = existingTransaction.get();
            transaction.setTransactionStatus(updatedTransaction.getTransactionStatus());
            transaction.setAmount(updatedTransaction.getAmount());
            return transactionRepository.save(transaction);
        }
        return null;
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        if (transactionRepository.existsById(transactionId)) {
            transactionRepository.deleteById(transactionId);
        }
    }
}

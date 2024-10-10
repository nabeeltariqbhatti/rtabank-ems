package ae.rakbank.eventpaymentservice.repository;


import ae.rakbank.eventpaymentservice.models.Purchase;
import ae.rakbank.eventpaymentservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByPurchase(Purchase purchase);
}

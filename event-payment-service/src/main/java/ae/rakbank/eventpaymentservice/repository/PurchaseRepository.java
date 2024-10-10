package ae.rakbank.eventpaymentservice.repository;


import ae.rakbank.eventpaymentservice.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}

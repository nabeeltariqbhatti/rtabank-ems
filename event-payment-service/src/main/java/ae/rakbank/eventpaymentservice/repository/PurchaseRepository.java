package ae.rakbank.eventpaymentservice.repository;


import ae.rakbank.eventpaymentservice.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Purchase findByBookingCode(@Param("bookingCode") String bookingCode);

    @Query("""
            SELECT COUNT(p) > 0 FROM Purchase p WHERE
            p.bookingCode =:bookingCode AND p.paymentStatus = :paymentStatus
             """)
    boolean isStatus(@Param("paymentStatus") Purchase.PaymentStatus paymentStatus,
                     @Param("bookingCode") String bookingCode);

    @Query("""
            UPDATE  Purchase p  set p.paymentStatus = :status WHERE p.bookingCode =:bookingCode
            """)
    void updatePurchaseStatusByBookingCode(String bookingCode, Purchase.PaymentStatus status);
}

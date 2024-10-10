package ae.rakbank.eventpaymentservice.models;

import ae.rakbank.eventpaymentservice.events.EventEntityListener;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "purchases")
@DynamicUpdate
@EntityListeners(EventEntityListener.class)
public class Purchase extends BaseEntity {

    private String purchaseCode = UUID.randomUUID().toString();
    private Long customerId;
    private String bookingCode;
    private String eventCode;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private LocalDateTime purchaseDate;
    private BigDecimal totalAmount;
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Transaction> transactions;


    public void addTransaction(Transaction transaction) {
        if(transaction ==null) return;
        if (this.transactions == null) this.transactions = new HashSet<>();
        transactions.add(transaction);
        transaction.setPurchase(this);


    }

    public enum PaymentStatus {
        PAID,
        UNPAID,
        REFUNDED,
        FAILED
    }
}

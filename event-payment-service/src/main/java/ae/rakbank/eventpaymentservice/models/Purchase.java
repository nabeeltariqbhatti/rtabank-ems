package ae.rakbank.eventpaymentservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "purchases")
public class Purchase extends BaseEntity{

    private String purchaseCode= UUID.randomUUID().toString();
    private Long customerId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime purchaseDate;
    
    private Double totalAmount;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();



    public void addTransaction(Transaction transaction) {
        if (this.transactions == null) this.transactions = new ArrayList<>();
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

package ae.rakbank.eventpaymentservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "transactions")
public class Transaction extends BaseEntity  {



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    @JsonBackReference
    private Purchase purchase;

    private String transactionCode= UUID.randomUUID().toString();

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    public enum TransactionType {
        PAYMENT,
        REFUND
    }

    public enum TransactionStatus {
        SUCCESS,
        FAILED,
        PENDING
    }
}

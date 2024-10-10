package ae.rakbank.eventpaymentservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "idempotency_records")
public class IdempotencyRecord extends  BaseEntity {



    private String idempotencyKey;
    private Long purchaseId;
}

package ae.rakbank.eventpaymentservice.dto;

import ae.rakbank.eventpaymentservice.models.PurchaseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PurchaseRequest(
        Long ticketId,
        Integer quantity,
        @JsonProperty("status")
        PurchaseStatus status,
        Long customerId
) {
}

package ae.rakbank.eventpaymentservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotNull(message = "Expiration month is required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer expirationMonth;

    @NotNull(message = "Expiration year is required")
    @Min(value = 2024, message = "Year must be a valid future year")
    private Integer expirationYear;

    @NotBlank(message = "CVV is required")
    @Min(value = 100, message = "CVV must be a 3 or 4 digit number")
    @Max(value = 9999, message = "CVV must be a 3 or 4 digit number")
    private String cvv;

    @NotNull(message = "Payment amount is required")
    @Min(value = 0, message = "Amount must be greater than zero")
    private Double amount;

    @NotBlank(message = "Currency is required")
    private String currency;
}

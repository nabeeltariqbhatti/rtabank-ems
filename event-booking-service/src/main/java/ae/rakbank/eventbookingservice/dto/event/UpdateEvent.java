package ae.rakbank.eventbookingservice.dto.event;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Event code must not be blank")
    private String code;

    private Status status;
    private String startDateTime;
    private String endDateTime;
    private int stopBookingBeforeMinutes;

    @Min(value = 0, message = "Capacity must be a non-negative integer")
    private Integer capacity;

    public enum Status {
        ACTIVE, DONE, SOLD_OUT, ON_HOLD, POSTPONED, CANCELED
    }
}


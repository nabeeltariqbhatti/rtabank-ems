package ae.rakbank.eventmanagementservice.dtos.event;

import ae.rakbank.eventmanagementservice.model.Event;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UpdateEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Event code must not be blank")
    private String code;

    private Event.Status status;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
    private int stopBookingBeforeMinutes;

    @Min(value = 0, message = "Capacity must be a non-negative integer")
    private Integer capacity;

}

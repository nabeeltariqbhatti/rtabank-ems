package ae.rakbank.eventmanagementservice.dtos.request;

import ae.rakbank.eventmanagementservice.model.Event;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "Event name must not be blank")
    private String name;
    @Size(max = 200, message = "Description must not exceed 200 characters")
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Venue is  required")
    private String venue;
    private String organizer;
    @NotNull(message = "Event start date time is required")
    @Future
    private LocalDateTime startDateTime;
    @NotNull(message = "Event end  date time  is required")
    @Future
    private LocalDateTime endDateTime;
    @Min(value = 20, message = "Minimum capacity is 20 ")
    private int capacity;
    private BigDecimal ticketPrice;
    private int stopBookingsBeforeMinutes=20;
    private Event.Status status;
    private Set<String> tags;

}

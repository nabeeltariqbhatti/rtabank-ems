package ae.rakbank.eventmanagementservice.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class EventRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Event name must not be blank")
    private String name;

    @Size(max = 200, message = "Description must not exceed 200 characters")
    @NotBlank
    private String description;

    private String venue;
    private String organizer;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Set<String> tags;

}

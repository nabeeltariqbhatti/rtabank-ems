package ae.rakbank.eventbookingservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class EventMetadata {

    private String eventCode;
    private int availableTickets;
    private int reservedSeats;
    private int confirmedSeats;
    private UpdateEvent.Status status;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String venue;


}

package ae.rakbank.bookingservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class EventMetadata {

    private String eventCode;
    private String eventName;
    private int availableTickets;
    private int reservedSeats;
    private int confirmedSeats;
    private int capacity;
    private BigDecimal ticketPrice;
    private UpdateEvent.Status status;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String venue;
    private Long eventId;


}

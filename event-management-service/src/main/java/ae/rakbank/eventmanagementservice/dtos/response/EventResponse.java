package ae.rakbank.eventmanagementservice.dtos.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record EventResponse(
        Long id,
        String name,
        String code,
        String description,
        String venue,
        String organizer,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        int capacity,
        BigDecimal ticketPrice,
        Set<String> tags,
        String status,
        int stopBookingsBeforeMinutes
) {
}

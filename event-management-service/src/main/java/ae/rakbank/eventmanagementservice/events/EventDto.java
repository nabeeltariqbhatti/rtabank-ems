package ae.rakbank.eventmanagementservice.events;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventDto {
    private String eventId;
    private String eventData;
    private EventType eventType;

}

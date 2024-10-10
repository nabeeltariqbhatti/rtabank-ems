package ae.rakbank.eventmanagementservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericEvent<T> {
    private String eventId; // Unique identifier for the event
    private T payload;      // Generic payload to hold any message data
    private EventType eventType; // Enum to define the type of event



}

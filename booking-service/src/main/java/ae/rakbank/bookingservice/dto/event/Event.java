package ae.rakbank.bookingservice.dto.event;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class Event<T> {
    private String eventId;
    private T eventData;
    private EventType eventType;
}

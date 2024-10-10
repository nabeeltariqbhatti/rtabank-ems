package ae.rakbank.eventmanagementservice.model;


import ae.rakbank.eventmanagementservice.events.EventEntityListener;
import ae.rakbank.eventmanagementservice.sequences.AlphanumericSequenceGenerator;
import ae.rakbank.eventmanagementservice.utils.Utils;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IdGeneratorType;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table(name = "events", indexes ={
        @Index(name = "event_idx", columnList = "name"),
        @Index(name = "event_code_idx", columnList = "code")

})
@EntityListeners(EventEntityListener.class)
@DynamicUpdate
public class Event extends BaseEntity {

    private String name;
    @Column(unique = true)
    private String code;
    private String description;
    private String venue;
    private String organizer;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int capacity;
    private BigDecimal ticketPrice=BigDecimal.ZERO;
    private String bookedBy;
    @ElementCollection
    private Set<String> tags;
    @Enumerated(EnumType.STRING)
    private Status status=Status.ACTIVE;
    private int stopBookingsBeforeMinutes=20;




    public enum Status {
        ACTIVE, DONE, SOLD_OUT, ON_HOLD, POSTPONED, CANCELED
    }
}

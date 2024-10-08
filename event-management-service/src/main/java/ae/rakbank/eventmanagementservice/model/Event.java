package ae.rakbank.eventmanagementservice.model;


import ae.rakbank.eventmanagementservice.sequences.AlphanumericSequenceGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IdGeneratorType;

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

}
)
public class Event extends BaseEntity {

    private String name;
    @GenericGenerator(name = "alphanumeric-sequence-generator", strategy = "ae.rakbank.evenmanagementservice.sequences.AlphanumericSequence")
    @Column(unique = true)
    private String code;
    private String description;
    private String venue;
    private String organizer;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @ElementCollection
    private Set<String> tags;
    @Enumerated(EnumType.STRING)
    private Status status=Status.ACTIVE;

}

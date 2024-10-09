package ae.rakbank.eventmanagementservice.events;

import ae.rakbank.eventmanagementservice.model.Event;
import ae.rakbank.eventmanagementservice.utils.Utils;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@NoArgsConstructor
public class EventEntityListener {

    @Autowired
    private   EventProducer eventProducer;


    @PrePersist
    public void beforeCreate(Event event) {
        System.out.println("Event is about to be created: " + event.getName());
        List<String> strings = Arrays.stream(event.getName().split("\\s"))
                .toList();
        event.setCode(Utils.generateEventCode(strings.get(strings.size()-1), "RB"));
    }

    @PostPersist
    public void afterCreate(Event event) {
        System.out.println("Event created successfully: " + event.getName());
    }

    @PreUpdate
    public void beforeUpdate(Event event) {
        System.out.println("Event is about to be updated: " + event.getName());
    }

    @PostUpdate
    public void afterUpdate(Event event) {
        System.out.println("Event updated successfully: " + event.getName());
    }
}

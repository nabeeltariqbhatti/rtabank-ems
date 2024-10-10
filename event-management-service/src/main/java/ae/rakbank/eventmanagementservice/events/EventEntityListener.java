package ae.rakbank.eventmanagementservice.events;

import ae.rakbank.eventmanagementservice.dtos.event.UpdateEvent;
import ae.rakbank.eventmanagementservice.mapper.EventMapper;
import ae.rakbank.eventmanagementservice.model.Event;
import ae.rakbank.eventmanagementservice.utils.Utils;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@NoArgsConstructor
public class EventEntityListener {

    @Autowired
    private   EventProducer eventProducer;

    @Value(value = "${rakbank.events.topic.event.updates.topic}")
    private String topic;


    @PrePersist
    public void beforeCreate(Event event) {
        System.out.println("Event is about to be created: " + event.getName());
        List<String> strings = Arrays.stream(event.getName().split("\\s"))
                .toList();
        event.setCode(Utils.generateEventCode(strings.get(strings.size()-1), "RB"));
    }

    @PostPersist
    @Async
    public void afterCreate(Event event) {
        UpdateEvent updateEvent = EventMapper.toUpdateEvent(event);
        eventProducer.produce(updateEvent,topic);

    }

    @PreUpdate
    public void beforeUpdate(Event event) {
        System.out.println("Event is about to be updated: " + event.getName());
    }

    @PostUpdate
    public void afterUpdate(Event event) {
        UpdateEvent updateEvent = EventMapper.toUpdateEvent(event);
        eventProducer.produce(updateEvent,topic);
    }
}

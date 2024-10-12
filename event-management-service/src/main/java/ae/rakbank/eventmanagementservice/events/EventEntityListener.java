package ae.rakbank.eventmanagementservice.events;

import ae.rakbank.eventmanagementservice.dtos.event.UpdateEvent;
import ae.rakbank.eventmanagementservice.mapper.EventMapper;
import ae.rakbank.eventmanagementservice.model.Event;
import ae.rakbank.eventmanagementservice.utils.Utils;
import io.micrometer.observation.annotation.Observed;
import jakarta.persistence.*;
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
    private EventProducer eventProducer;


    @Value(value = "${rakbank.events.topic.event.updates.topic}")
    private String topic;


    @PrePersist
    @Observed(name = "create.event",
            contextualName = "creating-event"
    )
    public void beforeCreate(Event event) {
        log.info("Event is about to be created: {}", event.getName());
        List<String> strings = Arrays.stream(event.getName().split("\\s"))
                .toList();
        event.setCode(Utils.generateEventCode(strings.get(strings.size() - 1), "RB"));
    }

    @PostPersist
    @Observed(name = "create.event",
            contextualName = "creating-event"
    )
    public void afterCreate(Event event) {
        UpdateEvent updateEvent = EventMapper.toUpdateEvent(event);
        eventProducer.produce(updateEvent, topic);

    }

    @PreUpdate
    @Observed(name = "update.events",
            contextualName = "updating-event"
    )
    public void beforeUpdate(Event event) {
        log.info("Event is about to be updated: {}", event.getName());
    }

    @PostUpdate
    @Observed(name = "update.events",
            contextualName = "updating-event"
    )
    public void afterUpdate(Event event) {
        UpdateEvent updateEvent = EventMapper.toUpdateEvent(event);
        eventProducer.produce(updateEvent, topic);
    }

    @PostRemove
    @Observed(name = "delete.events",
            contextualName = "deleting-event"
    )
    public void afterDelete(Event event) {
        UpdateEvent updateEvent = EventMapper.toUpdateEvent(event);
        eventProducer.produce(updateEvent, topic);
    }
}

package ae.rakbank.eventbookingservice.events;

import ae.rakbank.eventbookingservice.cache.EventCache;
import ae.rakbank.eventbookingservice.dto.event.EventMetadata;
import ae.rakbank.eventbookingservice.dto.event.UpdateEvent;
import ae.rakbank.eventbookingservice.mapper.BookingMapper;
import ae.rakbank.eventbookingservice.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public final class KafkaEventConsumer implements EventListener<Object> {

    @KafkaListener(topics = "${rakbank.events.topic.event.updates.topic}", groupId = "event-management")
    public void consumeEvent(Object event) {
        if(event == null) return;
        var consumerRecord = (ConsumerRecord<String, String>) event;
        UpdateEvent updateEvent = Utils.toObject(consumerRecord.value(), UpdateEvent.class);
        System.out.println("Received event: " + updateEvent);
        EventMetadata eventMetadata = BookingMapper.mapToEventMetadata(updateEvent);
        EventMetadata metadata = EventCache.getEvent(eventMetadata.getEventCode());
        if(metadata != null){
            BeanUtils.copyProperties(eventMetadata,metadata,"availableTickets,reservedSeats,confirmedSeats");
            EventCache.updateEvent(eventMetadata.getEventCode(),metadata);
        }else{
            EventCache.updateEvent(eventMetadata.getEventCode(), eventMetadata);
        }

    }


}

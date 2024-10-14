package ae.rakbank.bookingservice.events;

import ae.rakbank.bookingservice.cache.EventCache;
import ae.rakbank.bookingservice.dto.event.EventMetadata;
import ae.rakbank.bookingservice.dto.event.UpdateEvent;
import ae.rakbank.bookingservice.mapper.BookingMapper;
import ae.rakbank.bookingservice.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public final class EventsServiceToBookingConsumer implements EventListener<ConsumerRecord<String,String>> {

    @KafkaListener(topics = "${rakbank.events.topic.event.updates.topic}", groupId = "event-management")
    public void consumeEvent(ConsumerRecord<String,String> event) {
        if(event == null) return;
        UpdateEvent updateEvent = Utils.toObject(event.value(), UpdateEvent.class);
       log.info("Received event: {} " , updateEvent);
        EventMetadata eventMetadata = BookingMapper.mapToEventMetadata(updateEvent);
        switch (updateEvent.getStatus()){
            case CANCELED, POSTPONED,ON_HOLD ->{
                log.info("check if booking exists then process refund..");
                EventCache.removeEvent(eventMetadata.getEventCode());

            }
            case DONE,SOLD_OUT ->{
                log.info("check if booking reservtions exists cancel and refund, remove from cache..");
                EventCache.removeEvent(eventMetadata.getEventCode());

            }
            case ACTIVE -> {
                EventMetadata metadata = EventCache.getEvent(eventMetadata.getEventCode());
                if(metadata != null){
                    BeanUtils.copyProperties(eventMetadata,metadata,"availableTickets,reservedSeats,confirmedSeats");
                    return;
                }
                EventCache.updateEvent(eventMetadata.getEventCode(),eventMetadata);
            }default -> log.info("event type is unknown");


        }


    }



}

package ae.rakbank.eventbookingservice.events;


import ae.rakbank.eventbookingservice.cache.EventCache;
import ae.rakbank.eventbookingservice.dto.event.Event;
import ae.rakbank.eventbookingservice.dto.event.UpdateEvent;
import ae.rakbank.eventbookingservice.mapper.BookingMapper;
import ae.rakbank.eventbookingservice.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
@Slf4j
public final class RestEventConsumer implements EventListener<UpdateEvent> {



    @Override
    public  void consumeEvent(UpdateEvent updateEvent) {
        log.info("Consumed event through REST: {}", updateEvent);
        log.info("{}", updateEvent);
        EventCache.updateEvent(updateEvent.getCode(),BookingMapper.mapToEventMetadata(updateEvent) );
        System.out.println(EventCache.getEvent(updateEvent.getCode()));
    }

}

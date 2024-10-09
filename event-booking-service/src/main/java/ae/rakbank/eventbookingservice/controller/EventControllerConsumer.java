package ae.rakbank.eventbookingservice.controller;

import ae.rakbank.eventbookingservice.cache.EventCache;
import ae.rakbank.eventbookingservice.dto.event.Event;
import ae.rakbank.eventbookingservice.dto.event.UpdateEvent;
import ae.rakbank.eventbookingservice.events.RestEventConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1/events/consumer")
public class EventControllerConsumer {

    private final RestEventConsumer restEventConsumer;

    public EventControllerConsumer(RestEventConsumer restEventConsumer) {
        this.restEventConsumer = restEventConsumer;
    }

    @PostMapping
    public ResponseEntity<?> updateEventCache( @RequestBody UpdateEvent event){
        log.info("event update received {} : {}", event.getCode());
        restEventConsumer.consumeEvent(event);
        return  ResponseEntity.ok().build();
    }


}

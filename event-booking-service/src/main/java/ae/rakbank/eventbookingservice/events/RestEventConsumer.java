package ae.rakbank.eventbookingservice.events;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public final class RestEventConsumer implements EventListener {

    private static final Logger log = LoggerFactory.getLogger(RestEventConsumer.class);

    @Override
    public void consumeEvent(Event event) {
        log.info("Consumed event through REST: {}", event);
        // Add your REST-based consumption logic here
    }
}

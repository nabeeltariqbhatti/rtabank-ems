package ae.rakbank.eventbookingservice.events;

import ae.rakbank.eventbookingservice.utils.RestClientUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public final class RestEventProducer implements EventProducer<Object> {

    private  final RestClientUtil restClientUtil;

    private final String bookingServiceUrl;

    public RestEventProducer( RestClientUtil restClientUtil) {
        this.restClientUtil = restClientUtil;
        this.bookingServiceUrl = "http://localhost:9091/rakbank/event-booking-service/rest/api/v1";
    }

    @Override
    public void produce(Object event) {
        // Logic to send event to booking service via REST API
        String url = bookingServiceUrl + "/events/consumer"; // Update this URL as necessary
       var responseSpec  = restClientUtil.post(url, event);
        System.out.println("Event sent to booking service via REST: " + event);
    }

}
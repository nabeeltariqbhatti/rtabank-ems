package ae.rakbank.eventmanagementservice.events;

import ae.rakbank.eventmanagementservice.model.Event;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Primary
public final class RestEventProducer implements EventProducer {

    private final RestTemplate restTemplate;
    private final String bookingServiceUrl;

    public RestEventProducer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.bookingServiceUrl = "";
    }

    @Override
    public void produce(Event event) {
        // Logic to send event to booking service via REST API
        String url = bookingServiceUrl + "/events"; // Update this URL as necessary
        restTemplate.postForEntity(url, event, String.class);
        System.out.println("Event sent to booking service via REST: " + event);
    }
}

package ae.rakbank.eventmanagementservice.events;

import ae.rakbank.eventmanagementservice.dtos.event.UpdateEvent;
import ae.rakbank.eventmanagementservice.model.Event;
import ae.rakbank.eventmanagementservice.utils.RestClientUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@Primary
public final class RestEventProducer implements EventProducer {

    private  final RestClientUtil restClientUtil;

    private final String bookingServiceUrl;

    public RestEventProducer( RestClientUtil restClientUtil) {
        this.restClientUtil = restClientUtil;
        this.bookingServiceUrl = "http://localhost:9091/rakbank/event-booking-service/rest/api/v1";
    }

    @Override
    public void produce(UpdateEvent event) {
        // Logic to send event to booking service via REST API
        String url = bookingServiceUrl + "/events/consumer"; // Update this URL as necessary
       var responseSpec  = restClientUtil.post(url, event);
        System.out.println("Event sent to booking service via REST: " + event);
    }

}
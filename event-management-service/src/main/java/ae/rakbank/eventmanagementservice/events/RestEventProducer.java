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
public final class RestEventProducer implements EventProducer {

    private  final RestClientUtil restClientUtil;

    private final String bookingServiceUrl;

    public RestEventProducer( RestClientUtil restClientUtil) {
        this.restClientUtil = restClientUtil;
        this.bookingServiceUrl = "http://localhost:9091/rakbank/event-booking-service/rest/api/v1";
    }


    @Override
    public <T> void produce(T event, String target) {
        System.out.println("other producer");
    }
}
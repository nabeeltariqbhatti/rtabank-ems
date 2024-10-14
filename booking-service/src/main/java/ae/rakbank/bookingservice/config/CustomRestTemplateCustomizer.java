package ae.rakbank.bookingservice.config;


import ae.rakbank.bookingservice.constants.AppConstants;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

public class CustomRestTemplateCustomizer implements RestTemplateCustomizer {

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(Duration.ofMinutes(AppConstants.READ_TIME_OUT));
        factory.setConnectTimeout(Duration.ofMinutes(AppConstants.CONNECT_TIME_OUT));
        restTemplate.setRequestFactory(factory);
    }
}

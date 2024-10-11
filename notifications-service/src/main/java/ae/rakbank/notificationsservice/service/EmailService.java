package ae.rakbank.notificationsservice.service;

import ae.rakbank.notificationsservice.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class EmailService {

    @Value("${api.url}")
    private String apiUrl; // Inject the API URL

    @Value("${api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();


    public void sendEmail(EmailRequest emailRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("api-key", apiKey);
        headers.set("content-type", "application/json");
        HttpEntity<EmailRequest> requestEntity = new HttpEntity<>(emailRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
        System.out.println("Response: " + response.getBody());



    }
}

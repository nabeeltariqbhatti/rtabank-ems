package ae.rakbank.notificationsservice.service;

import ae.rakbank.notificationsservice.dto.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
public class EmailService {

    @Value("${api.url}")
    private String apiUrl; // Inject the API URL

    @Value("${api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();


    public void sendEmail(EmailRequest emailRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("api-key", "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");
        headers.set("content-type", "application/json");
        HttpEntity<EmailRequest> requestEntity = new HttpEntity<>(emailRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
       log.info("Response: " + response.getBody());

    }
}

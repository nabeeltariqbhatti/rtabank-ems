package ae.rakbank.notificationsservice.service;

import ae.rakbank.notificationsservice.dto.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EmailService {

    @Value("${api.url}")
    private String apiUrl; // Inject the API URL

    @Value("${api.key}")
    private String apiKey;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailRequest emailRequest) {
        MimeMessagePreparator mimeMessagePreparator =mimeMessage -> {
            MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("info@ipurvey.com");
            mimeMessageHelper.setSubject(emailRequest.getSubject());
            mimeMessageHelper.addTo(emailRequest.getTo().get(0).getEmail());
            mimeMessageHelper.setText(emailRequest.getHtmlContent(),true);
        };
        mailSender.send(mimeMessagePreparator);
       log.info("Response: email sent " );

    }
}

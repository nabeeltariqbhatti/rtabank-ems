package ae.rakbank.notificationsservice.service;

import ae.rakbank.notificationsservice.dto.EmailRequest;
import ae.rakbank.notificationsservice.model.Notification;
import ae.rakbank.notificationsservice.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
class MessageService {


    private final EmailService emailService;

    @KafkaListener(topics = "${rakbank.events.topic.booking.to.notifications.topic}", groupId = "notifications")
    public void send(ConsumerRecord<String, String> consumerRecord)  {
        log.info("event received {} ", consumerRecord);
        try{
            Notification notification = Utils.toObject(consumerRecord.value(), Notification.class);

            String template =Utils.readAsString("notification-template.html");
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("{name}", notification.getFullName());
            placeholders.put("{eventName}", notification.getEventName());
            placeholders.put("{eventDate}", notification.getEventDate().toString());
            placeholders.put("{status}", notification.getNotificationType().toString());
            placeholders.put("{location}", notification.getEventLocation());
            placeholders.put("{numberOfTickets}", String.valueOf(notification.getNumberOfTickets()));
            placeholders.put("{paymentAmount}", notification.getPaymentAmount().toString());
            placeholders.put("{reservationStatus}",Utils.getNotificationMessage(notification.getNotificationType(), notification.getBookingCode()) );
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                template = template.replace(entry.getKey(), entry.getValue());
            }
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setSender(new EmailRequest.Sender("Rak Bank Event Management", "nabeeltaariq@gmail.com"));
            emailRequest.setTo(Collections.singletonList(new EmailRequest.Recipient(notification.getUserName(),notification.getFullName())));
            emailRequest.setSubject("Status of your booking  " + notification.getBookingCode());
            emailRequest.setHtmlContent(template);
            emailService.sendEmail(emailRequest);

        }catch (Exception exception){
            exception.printStackTrace();
        }

    }




}

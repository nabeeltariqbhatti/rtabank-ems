package ae.rakbank.notificationsservice.service.impl;

import ae.rakbank.notificationsservice.model.Notification;
import ae.rakbank.notificationsservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl {

    @Autowired
    private NotificationRepository notificationRepository;


    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification updateNotification(Long id, Notification updatedNotification) {
        return notificationRepository.findById(id).map(notification -> {
            notification.setEventName(updatedNotification.getEventName());
            notification.setEventDate(updatedNotification.getEventDate());
            notification.setBookingCode(updatedNotification.getBookingCode());
            notification.setEventLocation(updatedNotification.getEventLocation());
            notification.setUserName(updatedNotification.getUserName());
            notification.setTicketType(updatedNotification.getTicketType());
            notification.setNumberOfTickets(updatedNotification.getNumberOfTickets());
            notification.setNotificationType(updatedNotification.getNotificationType());
            notification.setStatus(updatedNotification.getStatus());
            notification.setPaymentAmount(updatedNotification.getPaymentAmount());
            return notificationRepository.save(notification);
        }).orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    // Delete a notification by its ID
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}

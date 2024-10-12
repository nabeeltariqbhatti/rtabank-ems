package ae.rakbank.notificationsservice.service;

import ae.rakbank.notificationsservice.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {


    Notification save(Notification notification);


    Optional<Notification> getNotificationById(Long id);

    Notification updateNotification(Long id, Notification updatedNotification);


    void deleteNotification(Long id);

    List<Notification> getAllNotifications();
}

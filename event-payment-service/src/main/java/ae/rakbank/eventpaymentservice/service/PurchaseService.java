package ae.rakbank.eventpaymentservice.service;

import ae.rakbank.eventpaymentservice.dto.event.BookingEvent;
import ae.rakbank.eventpaymentservice.models.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    Purchase createPurchase(Purchase purchase);
    List<Purchase> getAllPurchases();
    Optional<Purchase> getPurchaseById(Long purchaseId);
    Purchase updatePurchase(Long purchaseId, Purchase updatedPurchase,String key);
    void deletePurchase(Long purchaseId);
    Purchase createPurchaseForBooking(BookingEvent bookingEvent);
}

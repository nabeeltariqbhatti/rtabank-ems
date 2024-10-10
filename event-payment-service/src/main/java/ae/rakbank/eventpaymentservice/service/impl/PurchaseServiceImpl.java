package ae.rakbank.eventpaymentservice.service.impl;

import ae.rakbank.eventpaymentservice.dto.event.BookingEvent;
import ae.rakbank.eventpaymentservice.mapper.PaymentMapper;
import ae.rakbank.eventpaymentservice.models.IdempotencyRecord;
import ae.rakbank.eventpaymentservice.models.Purchase;
import ae.rakbank.eventpaymentservice.repository.IdempotencyRecordRepository;
import ae.rakbank.eventpaymentservice.repository.PurchaseRepository;
import ae.rakbank.eventpaymentservice.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final IdempotencyRecordRepository idempotencyKeyRepository;

    @Override
    public Purchase createPurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Purchase update(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public Optional<Purchase> getPurchaseById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId);
    }

    @Override
    public Purchase updatePurchase(Long purchaseId, Purchase updatedPurchase,String idempotencyKey) {

        Optional<IdempotencyRecord> existingRecord =idempotencyKeyRepository.findByIdempotencyKey(idempotencyKey);
        if (existingRecord.isPresent()) {
            return purchaseRepository.findById(existingRecord.get().getPurchaseId()).orElse(null);
        }
        Optional<Purchase> existingPurchase = purchaseRepository.findById(purchaseId);

        if (existingPurchase.isPresent()) {
            Purchase purchase = existingPurchase.get();
            if (updatedPurchase.getPaymentStatus() != null) {
                purchase.setPaymentStatus(updatedPurchase.getPaymentStatus());
            }
            if (updatedPurchase.getTotalAmount() != null) {
                purchase.setTotalAmount(updatedPurchase.getTotalAmount());
            }

            if (updatedPurchase.getBookingCode() != null) {
                purchase.setBookingCode(updatedPurchase.getBookingCode());
            }

            if (updatedPurchase.getEventCode() != null) {
                purchase.setEventCode(updatedPurchase.getEventCode());
            }

            if (updatedPurchase.getCustomerId() != null) {
                purchase.setCustomerId(updatedPurchase.getCustomerId());
            }
            purchase.getTransactions().addAll(updatedPurchase.getTransactions());
            purchase =  purchaseRepository.save(purchase);
            IdempotencyRecord record = new IdempotencyRecord();
            record.setIdempotencyKey(idempotencyKey);
            record.setPurchaseId(purchase.getId());
            idempotencyKeyRepository.save(record);
        }

        return null;
    }

    @Override
    public void deletePurchase(Long purchaseId) {
        if (purchaseRepository.existsById(purchaseId)) {
            purchaseRepository.deleteById(purchaseId);
        }
    }

    @Override
    public Purchase createPurchaseForBooking(BookingEvent bookingEvent) {
        Purchase purchase = PaymentMapper.toPurchase(bookingEvent);
        return purchaseRepository.save(purchase);
    }
}

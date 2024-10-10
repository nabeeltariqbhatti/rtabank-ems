package ae.rakbank.eventpaymentservice.service.impl;

import ae.rakbank.eventpaymentservice.models.Purchase;
import ae.rakbank.eventpaymentservice.repository.PurchaseRepository;
import ae.rakbank.eventpaymentservice.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Override
    public Purchase createPurchase(Purchase purchase) {
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
    public Purchase updatePurchase(Long purchaseId, Purchase updatedPurchase) {
        Optional<Purchase> existingPurchase = purchaseRepository.findById(purchaseId);
        if (existingPurchase.isPresent()) {
            Purchase purchase = existingPurchase.get();
            purchase.setPaymentStatus(updatedPurchase.getPaymentStatus());
            purchase.setTotalAmount(updatedPurchase.getTotalAmount());
            return purchaseRepository.save(purchase);
        }
        return null;
    }

    @Override
    public void deletePurchase(Long purchaseId) {
        if (purchaseRepository.existsById(purchaseId)) {
            purchaseRepository.deleteById(purchaseId);
        }
    }
}

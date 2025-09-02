package com.guama.purchases.app.service.impl;

import com.guama.purchases.app.dto.PaymentRequestDto;
import com.guama.purchases.app.dto.PurchaseRequestDto;
import com.guama.purchases.app.dto.PurchaseResponseDto;
import com.guama.purchases.app.service.PurchaseService;
import com.guama.purchases.domain.entity.Purchase;
import com.guama.purchases.domain.enums.PurchaseFilters;
import com.guama.purchases.domain.enums.PurchaseStatus;
import com.guama.purchases.domain.repository.PurchaseRepository;
import com.guama.purchases.shared.exception.ProcessPaymentException;
import com.guama.purchases.shared.exception.ResourceNotFoundException;
import com.guama.purchases.shared.mapper.PurchaseMapper;
import com.guama.purchases.shared.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Service implementation for managing purchases.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final RateLimiterService rateLimiterService;
    private final PurchaseRepository purchaseRepository;

    @Override
    @Transactional
    public PurchaseResponseDto createPurchase(PurchaseRequestDto purchaseRequestDto, String customerId) {

        log.info("[INI] Creating purchase for user {}", customerId);
        rateLimiterService.checkRateLimit(customerId); //check rate limit

        Purchase purchase = PurchaseMapper.INSTANCE.toPurchase(purchaseRequestDto);
        purchase.setCustomerId(customerId);


        Purchase createdPurchase = purchaseRepository.save(purchase);
        PurchaseResponseDto purchaseResponseDto = PurchaseMapper.INSTANCE.toPurchaseResponseDto(createdPurchase);
        log.info("[END] Purchase created with ID: {}", purchaseResponseDto.purchaseId());

        return purchaseResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseResponseDto> getAllPurchases(String customerId) {

        log.info("[INI] Retrieving all purchases");
        List<Purchase> purchases;
        if (StringUtils.hasText(customerId)) {

            log.info("Retrieving purchases for customerId: {}", customerId);
            purchases = purchaseRepository.findAllByCustomerIdOrderByCreatedAscCreatedAtAsc(customerId);

        } else {

            log.info("No customerId provided, retrieving all purchases");
            purchases = purchaseRepository.findAll();
        }
        log.info("[END] Retrieved {} purchases", purchases.size());

        return purchases.stream()
                .map(PurchaseMapper.INSTANCE::toPurchaseResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseResponseDto> getPurchasesByFilter(PurchaseFilters filter, String value) {

        log.info("[INI] Retrieving purchases with filter: {} and value: {}", filter.name(), value);
        List<Purchase> filteredPurchases = switch (filter) {
            case STATUS ->
                    purchaseRepository.findAllByStatusOrderByCreatedAscCreatedAtAsc(PurchaseStatus.valueOf(value.toUpperCase()));
            case DESCRIPTION -> purchaseRepository.findByDescriptionContainingIgnoreCase(value);
            case DATE -> purchaseRepository.findAllByCreatedOrderByCreatedAsc(DateUtil.toLocalDate(value));
            case CUSTOMER_ID -> purchaseRepository.findAllByCustomerIdOrderByCreatedAscCreatedAtAsc(value);
        };
        log.info("[END] Retrieved {} purchases with filter: {} and value: {}", filteredPurchases.size(), filter.name(), value);

        return filteredPurchases.stream()
                .map(PurchaseMapper.INSTANCE::toPurchaseResponseDto)
                .toList();
    }

    @Override
    public void deletePurchase(UUID id) {
        throw new UnsupportedOperationException("Delete purchase operation is not supported");
    }

    @Override
    @Transactional
    public List<PurchaseResponseDto> processPayment(PaymentRequestDto paymentRequestDto, String customerId) {

        AtomicReference<Double> amount = new AtomicReference<>(paymentRequestDto.amount());

        log.info("[INI] getting pending purchases to process payment of amount: {}", amount.get());
        List<Purchase> pendingPurchases = purchaseRepository.findAllByStatusAndCustomerIdOrderByCreatedAscCreatedAtAsc(
                PurchaseStatus.PENDING, customerId);
        log.info("[END] found {} pending purchases to process payment", pendingPurchases.size());

        if (pendingPurchases.isEmpty()) {
            log.info("No pending purchases found to process payment");
            throw new ProcessPaymentException("No pending purchases found to process payment");
        }

        log.info("[INI] processing payment of amount: {}", amount.get());
        List<Purchase> paidPurchases = pendingPurchases.stream()
                .takeWhile(purchase -> {
                    if (amount.get() >= purchase.getPrice()) {
                        amount.updateAndGet(paymentAmount -> paymentAmount - purchase.getPrice());
                        purchase.setStatus(PurchaseStatus.PAID);
                        return true;
                    } else {
                        return false;
                    }
                })
                .toList();
        log.info("[END] processed payment, paid {} purchases, remaining amount: {}", paidPurchases.size(), amount.get());

        if (paidPurchases.isEmpty()) {
            log.info("Insufficient amount to process any purchase");
            throw new ProcessPaymentException("Insufficient amount to process any purchase");
        }
        log.info("[INI] saving {} paid purchases to database", paidPurchases.size());
        List<Purchase> paidPurchasesListSaved = purchaseRepository.saveAll(paidPurchases);
        log.info("[END] saved {} paid purchases to database", paidPurchasesListSaved.size());

        return paidPurchasesListSaved.stream()
                .map(PurchaseMapper.INSTANCE::toPurchaseResponseDto)
                .toList();
    }

    @Override
    public PurchaseResponseDto cancelPurchase(UUID purchaseId) {

        log.info("[INI] Cancelling purchase with ID: {}", purchaseId);
        Purchase purchase = Optional.of(purchaseRepository.findById(purchaseId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(p -> p.getStatus() == PurchaseStatus.PENDING)
                .orElseThrow(() -> {
                    log.error("Purchase with ID {} not found", purchaseId);
                    return new ResourceNotFoundException("Purchase cannot "
                            + "be cancelled because it is not in PENDING status or does not exist");
                });

        purchase.setStatus(PurchaseStatus.CANCELLED);
        purchase.setUpdated(LocalDate.now());
        purchase.setUpdatedAt(LocalTime.now());
        Purchase cancelledPurchase = purchaseRepository.save(purchase);
        log.info("[END] Cancelled purchase with ID: {}", purchaseId);

        return PurchaseMapper.INSTANCE.toPurchaseResponseDto(cancelledPurchase);
    }
}

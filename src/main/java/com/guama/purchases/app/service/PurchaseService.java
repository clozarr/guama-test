package com.guama.purchases.app.service;

import com.guama.purchases.app.dto.PaymentRequestDto;
import com.guama.purchases.app.dto.PurchaseRequestDto;
import com.guama.purchases.app.dto.PurchaseResponseDto;
import com.guama.purchases.domain.enums.PurchaseFilters;

import java.util.List;
import java.util.UUID;

public interface PurchaseService {

    PurchaseResponseDto createPurchase(PurchaseRequestDto purchaseRequestDto, String customerId);
    List<PurchaseResponseDto> getAllPurchases(String customerId);
    List<PurchaseResponseDto> getPurchasesByFilter(PurchaseFilters filter,
                                                   String value, String customerId);
    void deletePurchase(UUID id);
    List<PurchaseResponseDto>  processPayment(PaymentRequestDto paymentRequestDto,
                                              String customerId);
}

package com.guama.purchases.app.controller;


import com.guama.purchases.app.dto.PaymentRequestDto;
import com.guama.purchases.app.dto.PurchaseRequestDto;
import com.guama.purchases.app.dto.PurchaseResponseDto;
import com.guama.purchases.app.service.PurchaseService;
import com.guama.purchases.domain.enums.PurchaseFilters;
import com.guama.purchases.shared.validator.annotation.ValidEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing purchases.
 */
@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseResponseDto> createPurchase(
            @Valid @RequestBody PurchaseRequestDto request,
            @RequestHeader("X-User-ID") String customerId) {

        PurchaseResponseDto response = purchaseService.createPurchase(request, customerId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponseDto>> getAllPurchases(
             @RequestHeader(name = "X-User-ID",required = false) String customerId) {

        List<PurchaseResponseDto> purchases = purchaseService.getAllPurchases(customerId);
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity<List<PurchaseResponseDto>> getPurchasesByFilter(
            @RequestParam(name = "filterName")
                @Valid @ValidEnum(enumClass = PurchaseFilters.class)  PurchaseFilters filter,
            @RequestParam(name = "value") String value) {
        List<PurchaseResponseDto> purchases = purchaseService.getPurchasesByFilter(filter,value);
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @PatchMapping("/process-payments")
    public ResponseEntity<List<PurchaseResponseDto>> processPayment(
            @RequestHeader("X-User-ID") String customerId,
            @Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        List<PurchaseResponseDto> purchases = purchaseService.processPayment(paymentRequestDto, customerId);
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @PatchMapping("/{purchaseId}/cancel")
    public ResponseEntity<PurchaseResponseDto> cancelPurchase(
            @PathVariable("purchaseId") String purchaseId) {
        PurchaseResponseDto purchaseResponseDto = purchaseService.cancelPurchase(UUID.fromString(purchaseId));
        return new ResponseEntity<>(purchaseResponseDto, HttpStatus.OK);
    }

}

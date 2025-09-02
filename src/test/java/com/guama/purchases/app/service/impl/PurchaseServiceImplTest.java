package com.guama.purchases.app.service.impl;

import com.guama.purchases.app.dto.PaymentRequestDto;
import com.guama.purchases.app.dto.PurchaseRequestDto;
import com.guama.purchases.shared.exception.ProcessPaymentException;
import com.guama.purchases.shared.exception.ResourceNotFoundException;
import com.guama.purchases.app.dto.PurchaseResponseDto;
import com.guama.purchases.domain.entity.Purchase;
import com.guama.purchases.domain.enums.PurchaseFilters;
import com.guama.purchases.domain.enums.PurchaseStatus;
import com.guama.purchases.domain.repository.PurchaseRepository;
import com.guama.purchases.shared.exception.RateLimitException;
import com.guama.purchases.shared.mapper.PurchaseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private RateLimiterService rateLimiterService;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Test
    void createPurchase_Success() {
        // Data
        String customerId = "CLA649";
        PurchaseRequestDto requestDto =  PurchaseRequestDto.builder()
                .description("Test Purchase")
                .totalPrice(100.0)
                .details(new ArrayList<>())
                .build();
        Purchase purchase = PurchaseMapper.INSTANCE.toPurchase(requestDto);
        purchase.setCustomerId(customerId);

        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        // Execution
        PurchaseResponseDto responseDto = purchaseService.createPurchase(requestDto, customerId);

        // Assertion
        assertNotNull(responseDto);
        assertEquals(purchase.getDescription(), responseDto.description());
        assertEquals(purchase.getPrice(), responseDto.totalPrice());

        verify(rateLimiterService,times(1)).checkRateLimit(customerId);
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
    }

    @Test
    void createPurchase_WhenRateLimitExceeded_ShouldThrowRateLimitException() {

        // Data
        String customerId = "CLA649";
        PurchaseRequestDto requestDto =  PurchaseRequestDto.builder()
                .description("Test Purchase")
                .totalPrice(100.0)
                .details(new ArrayList<>())
                .build();

        doThrow(new RateLimitException("Rate limit exceeded")).when(rateLimiterService).checkRateLimit(customerId);

       // Execution
        assertThrows(RateLimitException.class, () -> {
            purchaseService.createPurchase(requestDto, customerId);
        });

        // Assertion
        verify(rateLimiterService).checkRateLimit(customerId);
        verify(purchaseRepository, never()).save(any(Purchase.class));
    }

    @Test
    void getAllPurchases_WithCustomerId() {

        String customerId = "CLA649";
        List<Purchase> purchases = List.of(new Purchase(), new Purchase());
        when(purchaseRepository.findAllByCustomerIdOrderByCreatedAscCreatedAtAsc(customerId)).thenReturn(purchases);


        List<PurchaseResponseDto> responseDtos = purchaseService.getAllPurchases(customerId);


        assertNotNull(responseDtos);
        assertEquals(2, responseDtos.size());
        verify(purchaseRepository).findAllByCustomerIdOrderByCreatedAscCreatedAtAsc(customerId);
        verify(purchaseRepository, never()).findAll();
    }

    @Test
    void getAllPurchases_WithoutCustomerId() {

        List<Purchase> purchases = List.of(new Purchase(), new Purchase());
        when(purchaseRepository.findAll()).thenReturn(purchases);


        List<PurchaseResponseDto> responseDtos = purchaseService.getAllPurchases(null);


        assertNotNull(responseDtos);
        assertEquals(2, responseDtos.size());
        verify(purchaseRepository, never()).findAllByCustomerIdOrderByCreatedAscCreatedAtAsc(anyString());
        verify(purchaseRepository).findAll();
    }

    @Test
    void getPurchasesByFilter_ByStatus() {
        // Data
        List<Purchase> purchases = List.of(Purchase.builder()
                .status(PurchaseStatus.PENDING).build());
        when(purchaseRepository.findAllByStatusOrderByCreatedAscCreatedAtAsc(PurchaseStatus.PENDING)).thenReturn(purchases);

        // Execution
        List<PurchaseResponseDto> result = purchaseService.getPurchasesByFilter(PurchaseFilters.STATUS, "PENDING");

        // Assertion
        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(p -> p.status().name().equals("PENDING")));
        verify(purchaseRepository).findAllByStatusOrderByCreatedAscCreatedAtAsc(PurchaseStatus.PENDING);
    }

    @Test
    void getPurchasesByFilter_ByDescription() {
        // Data
        List<Purchase> purchases = List.of(Purchase.builder()
                .description("Test Purchase").build());
        when(purchaseRepository.findByDescriptionContainingIgnoreCase("Test Purchase")).thenReturn(purchases);

        // Execution
        List<PurchaseResponseDto> result = purchaseService.getPurchasesByFilter(PurchaseFilters.DESCRIPTION, "Test Purchase");

        // Assertion
        assertEquals(1, result.size());
        verify(purchaseRepository).findByDescriptionContainingIgnoreCase("Test Purchase");
    }

    @Test
    void getPurchasesByFilter_ByDate() {
        // Data
        List<Purchase> purchases = List.of(new Purchase());
        LocalDate date = LocalDate.now();
        when(purchaseRepository.findAllByCreatedOrderByCreatedAsc(date)).thenReturn(purchases);

        // Execution
        List<PurchaseResponseDto> result = purchaseService.getPurchasesByFilter(PurchaseFilters.DATE, date.toString());

        // Assertion
        assertEquals(1, result.size());
        verify(purchaseRepository).findAllByCreatedOrderByCreatedAsc(date);
    }

    @Test
    void getPurchasesByFilter_ByCustomerId() {
        // Data
        List<Purchase> purchases = List.of(new Purchase());
        when(purchaseRepository.findAllByCustomerIdOrderByCreatedAscCreatedAtAsc("CLA649")).thenReturn(purchases);

        // Execution
        List<PurchaseResponseDto> result = purchaseService.getPurchasesByFilter(PurchaseFilters.CUSTOMER_ID, "CLA649");

        // Assertion
        assertEquals(1, result.size());
        verify(purchaseRepository).findAllByCustomerIdOrderByCreatedAscCreatedAtAsc("CLA649");
    }

    @Test
    void deletePurchase_ShouldThrowUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            purchaseService.deletePurchase(java.util.UUID.randomUUID());
        });
    }

    @Test
    void processPayment_NoPendingPurchases_ShouldThrowException() {
        // Data
        String customerId = "CLA649";
        PaymentRequestDto requestDto = new PaymentRequestDto(100.0);
        when(purchaseRepository.findAllByStatusAndCustomerIdOrderByCreatedAscCreatedAtAsc(PurchaseStatus.PENDING, customerId))
                .thenReturn(new ArrayList<>());

        // Execution
        assertThrows(ProcessPaymentException.class, () -> {
            purchaseService.processPayment(requestDto, customerId);
        });

        // Assertion
        verify(purchaseRepository).findAllByStatusAndCustomerIdOrderByCreatedAscCreatedAtAsc(PurchaseStatus.PENDING, customerId);

    }

    @Test
    void processPayment_InsufficientAmount_ShouldThrowException() {
        // Data
        String customerId = "CLA649";
        PaymentRequestDto requestDto = new PaymentRequestDto(50.0);

        Purchase purchase = Purchase.builder()
                .price(100.0)
                .status(PurchaseStatus.PENDING)
                .build();
        List<Purchase> pendingPurchases = List.of(purchase);

        when(purchaseRepository.findAllByStatusAndCustomerIdOrderByCreatedAscCreatedAtAsc(PurchaseStatus.PENDING, customerId))
                .thenReturn(pendingPurchases);

        // Execution
        assertThrows(ProcessPaymentException.class, () -> {
            purchaseService.processPayment(requestDto, customerId);
        });

        // Assertion
        verify(purchaseRepository).findAllByStatusAndCustomerIdOrderByCreatedAscCreatedAtAsc(PurchaseStatus.PENDING, customerId);
    }

    @Test
    void processPayment_Success() {
        // Data
        String customerId = "CLA649";
        PaymentRequestDto requestDto = new PaymentRequestDto(150.0);

        Purchase purchase1 = Purchase.builder()
                .price(50.0)
                .status(PurchaseStatus.PENDING)
                .build();

        Purchase purchase2 = Purchase.builder()
                .price(100.0)
                .status(PurchaseStatus.PENDING)
                .build();

        List<Purchase> pendingPurchases = List.of(purchase1, purchase2);

        when(purchaseRepository.findAllByStatusAndCustomerIdOrderByCreatedAscCreatedAtAsc(PurchaseStatus.PENDING, customerId))
                .thenReturn(pendingPurchases);
        when(purchaseRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // Execution
        List<PurchaseResponseDto> result = purchaseService.processPayment(requestDto, customerId);

        // Assertion
        assertEquals(2, result.size());
        assertEquals(PurchaseStatus.PAID, purchase1.getStatus());
        assertEquals(PurchaseStatus.PAID, purchase2.getStatus());
        verify(purchaseRepository).findAllByStatusAndCustomerIdOrderByCreatedAscCreatedAtAsc(PurchaseStatus.PENDING, customerId);
        verify(purchaseRepository).saveAll(anyList());
    }

    @Test
    void cancelPurchase_Success() {
        // Data
        UUID purchaseId = UUID.randomUUID();
        Purchase purchase = Purchase.builder()
                .status(PurchaseStatus.PENDING)
                .build();

        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        // Execution
        PurchaseResponseDto result = purchaseService.cancelPurchase(purchaseId);

        // Assertion
        assertNotNull(result);
        assertEquals(PurchaseStatus.CANCELLED.toString(), result.status().toString());
        verify(purchaseRepository).findById(purchaseId);
        verify(purchaseRepository).save(purchase);
    }

    @Test
    void cancelPurchase_NotFound_ShouldThrowException() {
        // Arrange
        UUID purchaseId = UUID.randomUUID();
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.empty());

        // Execution
        assertThrows(ResourceNotFoundException.class, () -> {
            purchaseService.cancelPurchase(purchaseId);
        });

        // Assertion
        verify(purchaseRepository).findById(purchaseId);
    }

    @Test
    void cancelPurchase_WrongStatus_ShouldThrowException() {
        // Data
        UUID purchaseId = UUID.randomUUID();
        Purchase purchase = Purchase.builder()
                .status(PurchaseStatus.PAID)
                .build();

        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));

        // Execution
        assertThrows(ResourceNotFoundException.class, () -> {
            purchaseService.cancelPurchase(purchaseId);
        });

        // Assertion
        verify(purchaseRepository).findById(purchaseId);
    }
}

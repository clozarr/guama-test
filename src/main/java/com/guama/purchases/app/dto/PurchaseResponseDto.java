package com.guama.purchases.app.dto;

import com.guama.purchases.domain.enums.PurchaseStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record PurchaseResponseDto(
        UUID purchaseId,
        LocalDate created,
        LocalDate updated,
        String description,
        Double totalPrice,
        PurchaseStatus status,
        List<PurchaseDetailResponseDto> details

){ }

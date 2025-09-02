package com.guama.purchases.app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;


@Builder
public record PaymentRequestDto (
        @NotNull(message = "Amount must not be null")
        @Positive(message = "Amount must be positive")
        Double amount

) { }

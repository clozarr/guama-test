package com.guama.purchases.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record PurchaseDetailRequestDto(
        @NotBlank(message = "Item name is mandatory")
        String itemName,
        @NotNull(message = "Unit price is mandatory")
        @Positive(message = "Unit price must be positive")
        Double unitPrice,
        @NotNull(message = "Quantity is mandatory")
        @Positive(message = "Quantity must be positive")
        Short quantity,
        @NotNull(message = "Total price is mandatory")
        @Positive(message = "Total price must be positive")
        Double totalPrice
)
{ }

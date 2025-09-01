package com.guama.purchases.app.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import java.util.List;



/**
 * Data Transfer Object for creating a new purchase.
 */
@Builder
public record PurchaseRequestDto(
    @NotBlank(message = "Description is mandatory")
    String description,
    @NotNull(message = "Total price is mandatory")
    @Positive(message = "Total price must be positive")
    Double totalPrice,
    @NotEmpty (message = "Purchase must have at least one detail")
    @Valid List<PurchaseDetailRequestDto> details

) { }

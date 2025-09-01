package com.guama.purchases.app.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record PurchaseDetailResponseDto (

       UUID detailId,
       String itemName,
       Double unitPrice,
       Integer quantity,
       Double totalPrice

){ }

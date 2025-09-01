package com.guama.purchases.domain.enums;


import lombok.Getter;

/**
 * Enum representing the status of a purchases item.
 */
@Getter
public enum PurchaseStatus {


    PENDING("Transaction is PENDING"),
    PAID("Transaction is PAID"),
    CANCELLED("Transaction is CANCELLED"),
    DECLINED("Transaction is DECLINED");

    private final String description;
    PurchaseStatus(String description){
        this.description = description;
    }


}

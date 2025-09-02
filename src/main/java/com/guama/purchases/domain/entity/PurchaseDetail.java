package com.guama.purchases.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase_details")
public class PurchaseDetail {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "detail_id", columnDefinition = "BINARY(16)")
    private UUID detailId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private Short quantity;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", referencedColumnName = "purchase_id", insertable = false, updatable = false)
    @JsonIgnore // Evita recursión infinita en la serialización JSON
    private Purchase purchase;


    protected void onCreate() {
        this.detailId = UUID.randomUUID();
    }
    @PrePersist
    @PreUpdate
    protected void calculateTotalPrice() {
        if (quantity > 0) {
            this.totalPrice = unitPrice * quantity;
        }
    }
}

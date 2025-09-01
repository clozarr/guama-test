package com.guama.purchases.domain.entity;

import com.guama.purchases.domain.enums.PurchaseStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity class representing a purchases item.
 */
@Builder
@Entity
@Table(name = "purchases")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "purchase_id", columnDefinition = "BINARY(16)")
    private UUID purchaseId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    @Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate created;

    @Column(name = "created_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalTime createdAt;

    @Column(name = "updated" , nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate updated;

    @Column(name = "updated_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalTime updatedAt;

    @Column(name = "customerId", nullable = false)
    private String customerId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    @JoinColumn(name = "purchase_id", nullable = false, updatable = false)
    private List<PurchaseDetail> details = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        created = LocalDate.now();
        updated = LocalDate.now();
        createdAt = LocalTime.now();
        updatedAt = LocalTime.now();
        status = PurchaseStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updated = LocalDate.now();
        updatedAt = LocalTime.now();
    }
    public void addDetail(PurchaseDetail detail) {
        details.add(detail);
        detail.setPurchase(this);
    }

}

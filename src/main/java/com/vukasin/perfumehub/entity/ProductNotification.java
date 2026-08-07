package com.vukasin.perfumehub.entity;

import com.vukasin.perfumehub.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_notification", uniqueConstraints = @UniqueConstraint(
        columnNames = {"app_user_id", "product_variant_id", "type"}
))
public class ProductNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;

    @Column(name = "active", nullable = false)
    private boolean active;

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Positive
    @Column(name = "last_known_price", precision = 10, scale = 2)
    private BigDecimal lastKnownPrice;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    protected ProductNotification() {

    }

    public ProductNotification(
            NotificationType type,
            boolean active,
            BigDecimal lastKnownPrice,
            AppUser user,
            ProductVariant productVariant
    ) {
        this.type = type;
        this.active = active;
        this.lastKnownPrice = lastKnownPrice;
        this.user = user;
        this.productVariant = productVariant;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BigDecimal getLastKnownPrice() {
        return lastKnownPrice;
    }

    public void setLastKnownPrice(BigDecimal lastKnownPrice) {
        this.lastKnownPrice = lastKnownPrice;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }
}

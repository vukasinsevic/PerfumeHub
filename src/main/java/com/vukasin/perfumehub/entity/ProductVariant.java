package com.vukasin.perfumehub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Entity
@Table(name = "product_variant")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "volume_ml", nullable = false)
    @Positive
    private Integer volumeMl;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    @Positive
    private BigDecimal price;

    @NotNull
    @Column(name = "stock", nullable = false)
    @PositiveOrZero
    private Integer stock;

    @Column(name = "active", nullable = false)
    private boolean active;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "perfume_id", nullable = false)
    private Perfume perfume;

    protected ProductVariant() {

    }

    public ProductVariant(
            Integer volumeMl,
            BigDecimal price,
            Integer stock,
            boolean active,
            Perfume perfume
    ) {
        this.volumeMl = volumeMl;
        this.price = price;
        this.stock = stock;
        this.active = active;
        this.perfume = perfume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(Integer volumeMl) {
        this.volumeMl = volumeMl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Perfume getPerfume() {
        return perfume;
    }

    public void setPerfume(Perfume perfume) {
        this.perfume = perfume;
    }
}

package com.vukasin.perfumehub.entity;

import com.vukasin.perfumehub.enums.CollectionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "collection_item", uniqueConstraints = @UniqueConstraint(
        columnNames = {"app_user_id", "perfume_id"}
))
public class CollectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CollectionStatus status;

    @NotNull
    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDateTime addedAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "perfume_id", nullable = false)
    private Perfume perfume;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser user;

    protected CollectionItem() {

    }

    public CollectionItem(
            CollectionStatus status,
            Perfume perfume,
            AppUser user
    ) {
        this.status = status;
        this.perfume = perfume;
        this.user = user;
        this.addedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CollectionStatus getStatus() {
        return status;
    }

    public void setStatus(CollectionStatus status) {
        this.status = status;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public Perfume getPerfume() {
        return perfume;
    }

    public void setPerfume(Perfume perfume) {
        this.perfume = perfume;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}

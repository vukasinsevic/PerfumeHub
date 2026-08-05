package com.vukasin.perfumehub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "perfume")
public class Perfume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "concentration_id", nullable = false)
    private Concentration concentration;

    @ManyToMany
    @JoinTable(
            name = "perfume_note",
            joinColumns = @JoinColumn(name = "perfume_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id"),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"perfume_id", "note_id"}
            )
    )
    private Set<Note> notes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "perfume_accord",
            joinColumns = @JoinColumn(name = "perfume_id"),
            inverseJoinColumns = @JoinColumn(name = "accord_id"),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"perfume_id", "accord_id"}
            )
    )
    private Set<Accord> accords = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "perfume_season",
            joinColumns = @JoinColumn(name = "perfume_id"),
            inverseJoinColumns = @JoinColumn(name = "season_id"),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"perfume_id", "season_id"}
            )
    )
    private Set<Season> seasons = new HashSet<>();

    protected Perfume() {

    }

    public Perfume(
            String name,
            String description,
            Integer releaseYear,
            String imageUrl,
            Brand brand,
            Gender gender,
            Concentration concentration) {
        this.name = name;
        this.description = description;
        this.releaseYear = releaseYear;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.gender = gender;
        this.concentration = concentration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Concentration getConcentration() {
        return concentration;
    }

    public void setConcentration(Concentration concentration) {
        this.concentration = concentration;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }

    public Set<Accord> getAccords() {
        return accords;
    }

    public void addAccord(Accord accord) {
        accords.add(accord);
    }

    public void removeAccord(Accord accord) {
        accords.remove(accord);
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public void addSeason(Season season) {
        seasons.add(season);
    }

    public void removeSeason(Season season) {
        seasons.remove(season);
    }
}

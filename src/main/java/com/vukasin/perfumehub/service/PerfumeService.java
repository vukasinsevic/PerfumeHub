package com.vukasin.perfumehub.service;

import com.vukasin.perfumehub.dto.request.CreatePerfumeRequest;
import com.vukasin.perfumehub.dto.request.UpdatePerfumeRequest;
import com.vukasin.perfumehub.dto.response.PerfumeDetailsResponse;
import com.vukasin.perfumehub.dto.response.PerfumeSummaryResponse;
import com.vukasin.perfumehub.entity.*;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.mapper.PerfumeMapper;
import com.vukasin.perfumehub.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PerfumeService {

    private final PerfumeRepository perfumeRepository;
    private final BrandRepository brandRepository;
    private final GenderRepository genderRepository;
    private final ConcentrationRepository concentrationRepository;
    private final NoteRepository noteRepository;
    private final AccordRepository accordRepository;
    private final SeasonRepository seasonRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ReviewRepository reviewRepository;
    private final PerfumeMapper perfumeMapper;

    public PerfumeService(
            PerfumeRepository perfumeRepository,
            BrandRepository brandRepository,
            GenderRepository genderRepository,
            ConcentrationRepository concentrationRepository,
            NoteRepository noteRepository,
            AccordRepository accordRepository,
            SeasonRepository seasonRepository,
            ProductVariantRepository productVariantRepository,
            ReviewRepository reviewRepository,
            PerfumeMapper perfumeMapper
    ) {
        this.perfumeRepository = perfumeRepository;
        this.brandRepository = brandRepository;
        this.genderRepository = genderRepository;
        this.concentrationRepository = concentrationRepository;
        this.noteRepository = noteRepository;
        this.accordRepository = accordRepository;
        this.seasonRepository = seasonRepository;
        this.productVariantRepository = productVariantRepository;
        this.reviewRepository = reviewRepository;
        this.perfumeMapper = perfumeMapper;
    }

    @Transactional(readOnly = true)
    public List<PerfumeSummaryResponse> getPerfumes() {

        List<Perfume> perfumes = perfumeRepository.findAll();

        List<PerfumeSummaryResponse> responses = new ArrayList<>();

        for (Perfume perfume : perfumes) {

            List<ProductVariant> productVariants = productVariantRepository.findByPerfumeIdAndActiveTrue(perfume.getId());
            List<Review> reviews = reviewRepository.findByPerfumeIdOrderByCreatedAtDesc(perfume.getId());

            BigDecimal lowestPrice = productVariants.stream()
                    .map(ProductVariant::getPrice)
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);

            long reviewCount = reviews.size();

            double averageRating = calculateAverageRating(reviews);

            PerfumeSummaryResponse response = perfumeMapper.toSummaryResponse(
                    perfume,
                    lowestPrice,
                    averageRating,
                    reviewCount
            );

            responses.add(response);
        }

        return responses;
    }

    @Transactional(readOnly = true)
    public PerfumeDetailsResponse getPerfumeDetails(Long perfumeId) {

        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Perfume not found"));

        List<ProductVariant> productVariants =
                productVariantRepository.findByPerfumeIdAndActiveTrue(perfumeId);

        List<Review> reviews = reviewRepository.findByPerfumeIdOrderByCreatedAtDesc(perfumeId);

        long count = reviews.size();
        double averageRating = calculateAverageRating(reviews);

        return perfumeMapper.toDetailsResponse(
                perfume,
                productVariants,
                averageRating,
                count
        );
    }

    @Transactional
    public PerfumeDetailsResponse createPerfume(CreatePerfumeRequest request) {

        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Brand not found"));

        Gender gender = genderRepository.findById(request.genderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Gender not found"));

        Concentration concentration = concentrationRepository.findById(request.concentrationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Concentration not found"));

        Perfume perfume = new Perfume(
                request.name(),
                request.description(),
                request.releaseYear(),
                request.imageUrl(),
                brand,
                gender,
                concentration
        );

        if (request.noteIds() != null) {
            for (Long noteId: request.noteIds()) {
                Note note = noteRepository.findById(noteId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Note not found"));

                perfume.addNote(note);
            }
        }

        if (request.accordIds() != null) {
            for (Long accordId: request.accordIds()) {
                Accord accord = accordRepository.findById(accordId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Accord not found"));

                perfume.addAccord(accord);
            }
        }

        if (request.seasonIds() != null) {
            for (Long seasonId: request.seasonIds()) {
                Season season = seasonRepository.findById(seasonId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Season not found"));

                perfume.addSeason(season);
            }
        }

        Perfume savedPerfume = perfumeRepository.save(perfume);

        return perfumeMapper.toDetailsResponse(
                savedPerfume,
                List.of(),
                0.0,
                0L
        );
    }

    @Transactional
    public PerfumeDetailsResponse updatePerfume(
            Long perfumeId,
            UpdatePerfumeRequest request
    ) {

        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Perfume not found"));

        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Brand not found"));

        Gender gender = genderRepository.findById(request.genderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Gender not found"));

        Concentration concentration = concentrationRepository
                .findById(request.concentrationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Concentration not found"));

        perfume.setName(request.name());
        perfume.setDescription(request.description());
        perfume.setReleaseYear(request.releaseYear());
        perfume.setImageUrl(request.imageUrl());
        perfume.setBrand(brand);
        perfume.setGender(gender);
        perfume.setConcentration(concentration);

        perfume.getNotes().clear();
        perfume.getAccords().clear();
        perfume.getSeasons().clear();

        if (request.noteIds() != null) {
            for (Long noteId : request.noteIds()) {

                Note note = noteRepository.findById(noteId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Note not found"
                                ));

                perfume.addNote(note);
            }
        }

        if (request.accordIds() != null) {
            for (Long accordId : request.accordIds()) {

                Accord accord = accordRepository.findById(accordId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Accord not found"
                                ));

                perfume.addAccord(accord);
            }
        }

        if (request.seasonIds() != null) {
            for (Long seasonId : request.seasonIds()) {

                Season season = seasonRepository.findById(seasonId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Season not found"
                                ));

                perfume.addSeason(season);
            }
        }

        Perfume savedPerfume = perfumeRepository.save(perfume);

        List<ProductVariant> productVariants =
                productVariantRepository.findByPerfumeId(perfumeId);

        List<Review> reviews =
                reviewRepository.findByPerfumeIdOrderByCreatedAtDesc(perfumeId);

        long reviewCount = reviews.size();

        double averageRating = calculateAverageRating(reviews);

        return perfumeMapper.toDetailsResponse(
                savedPerfume,
                productVariants,
                averageRating,
                reviewCount
        );
    }

    @Transactional
    public void deletePerfume(Long perfumeId) {

        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Perfume not found"));


        perfumeRepository.delete(perfume);
    }

    private double calculateAverageRating(List<Review> reviews) {

        if (reviews.isEmpty()) {
            return 0.0;
        }

        long sum = 0;

        for (Review review : reviews) {
            sum += review.getRating();
        }

        return (double) sum / reviews.size();
    }

}

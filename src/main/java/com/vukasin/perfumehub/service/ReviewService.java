package com.vukasin.perfumehub.service;

import com.vukasin.perfumehub.dto.request.CreateReviewRequest;
import com.vukasin.perfumehub.dto.request.UpdateReviewRequest;
import com.vukasin.perfumehub.dto.response.ReviewResponse;
import com.vukasin.perfumehub.entity.AppUser;
import com.vukasin.perfumehub.entity.Perfume;
import com.vukasin.perfumehub.entity.Review;
import com.vukasin.perfumehub.exception.DuplicateResourceException;
import com.vukasin.perfumehub.exception.ForbiddenOperationException;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.mapper.ReviewMapper;
import com.vukasin.perfumehub.repository.AppUserRepository;
import com.vukasin.perfumehub.repository.PerfumeRepository;
import com.vukasin.perfumehub.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PerfumeRepository perfumeRepository;
    private final AppUserRepository userRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(
            ReviewRepository reviewRepository,
            PerfumeRepository perfumeRepository,
            AppUserRepository userRepository,
            ReviewMapper reviewMapper
    ) {
        this.reviewRepository = reviewRepository;
        this.perfumeRepository = perfumeRepository;
        this.userRepository = userRepository;
        this.reviewMapper = reviewMapper;
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsForPerfume(Long perfumeId) {

        if (!perfumeRepository.existsById(perfumeId))
            throw new ResourceNotFoundException("Perfume not found");

        List<Review> reviews =
                reviewRepository.findByPerfumeIdOrderByCreatedAtDesc(perfumeId);

        return reviewMapper.toResponseList(reviews);
    }

    @Transactional
    public ReviewResponse createReview(
            Long perfumeId,
            Long userId,
            CreateReviewRequest request
    ) {

        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow( () ->
                        new ResourceNotFoundException("Perfume not found"));

        AppUser user = userRepository.findById(userId)
                .orElseThrow( () ->
                        new ResourceNotFoundException("User not found"));

        if (reviewRepository.existsByUserIdAndPerfumeId(userId,perfumeId))
            throw new DuplicateResourceException("User has already reviewed this perfume");

        Review review = new Review(
                request.rating(),
                request.text(),
                perfume,
                user
        );

        Review savedReview = reviewRepository.save(review);

        return reviewMapper.toResponse(savedReview);
    }

    @Transactional
    public ReviewResponse updateReview(
            Long reviewId,
            Long userId,
            UpdateReviewRequest request
    ) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow( () ->
                        new ResourceNotFoundException("Review not found"));

        if (!review.getUser().getId().equals(userId))
            throw new ForbiddenOperationException("You cannot update another user's review");

        review.setRating(request.rating());
        review.setText(request.text());
        review.setUpdatedAt(LocalDateTime.now());

        Review updateReview = reviewRepository.save(review);

        return reviewMapper.toResponse(updateReview);
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow( () ->
                        new ResourceNotFoundException("Review not found"));

        if (!review.getUser().getId().equals(userId)) {
            throw new ForbiddenOperationException(
                    "You cannot delete another user's review"
            );
        }

        reviewRepository.delete(review);
    }

}

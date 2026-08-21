package com.vukasin.perfumehub.controller;

import com.vukasin.perfumehub.dto.request.CreateReviewRequest;
import com.vukasin.perfumehub.dto.request.UpdateReviewRequest;
import com.vukasin.perfumehub.dto.response.ReviewResponse;
import com.vukasin.perfumehub.security.CurrentUserProvider;
import com.vukasin.perfumehub.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final CurrentUserProvider currentUserProvider;

    public ReviewController(
            ReviewService reviewService,
            CurrentUserProvider currentUserProvider
    ) {
        this.reviewService = reviewService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping("/perfumes/{perfumeId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsForPerfume(
            @PathVariable Long perfumeId
    ) {

        List<ReviewResponse> reviews = reviewService.getReviewsForPerfume(perfumeId);

        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/perfumes/{perfumeId}/reviews")
    public ResponseEntity<ReviewResponse> createReview(
            @PathVariable Long perfumeId,
            @Valid @RequestBody CreateReviewRequest request,
            Authentication authentication
            ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        ReviewResponse response = reviewService.createReview(
                perfumeId,
                userId,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody UpdateReviewRequest request,
            Authentication authentication
            ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        ReviewResponse response = reviewService.updateReview(
                reviewId,
                userId,
                request
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        reviewService.deleteReview(reviewId,userId);

        return ResponseEntity.noContent().build();
    }

}

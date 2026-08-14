package com.vukasin.perfumehub.mapper;

import com.vukasin.perfumehub.dto.response.ReviewResponse;
import com.vukasin.perfumehub.entity.Review;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewMapper {

    public ReviewResponse toResponse(Review review) {

        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getText(),
                review.getCreatedAt(),
                review.getUpdatedAt(),
                review.getUser().getId(),
                review.getUser().getName()
        );
    }

    public List<ReviewResponse> toResponseList(List<Review> reviews) {
        return reviews.stream()
                .map(this::toResponse)
                .toList();
    }
}
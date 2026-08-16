package com.vukasin.perfumehub.service;

import com.vukasin.perfumehub.dto.request.AddWishlistItemRequest;
import com.vukasin.perfumehub.dto.response.WishlistItemResponse;
import com.vukasin.perfumehub.entity.AppUser;
import com.vukasin.perfumehub.entity.Perfume;
import com.vukasin.perfumehub.entity.WishlistItem;
import com.vukasin.perfumehub.exception.DuplicateResourceException;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.mapper.WishlistItemMapper;
import com.vukasin.perfumehub.repository.AppUserRepository;
import com.vukasin.perfumehub.repository.PerfumeRepository;
import com.vukasin.perfumehub.repository.WishlistItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    private final WishlistItemRepository wishlistItemRepository;
    private final PerfumeRepository perfumeRepository;
    private final AppUserRepository userRepository;
    private final WishlistItemMapper wishlistItemMapper;

    public WishlistService(
            WishlistItemRepository wishlistItemRepository,
            PerfumeRepository perfumeRepository,
            AppUserRepository userRepository,
            WishlistItemMapper wishlistItemMapper
    ) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.perfumeRepository = perfumeRepository;
        this.userRepository = userRepository;
        this.wishlistItemMapper = wishlistItemMapper;
    }

    @Transactional(readOnly = true)
    public List<WishlistItemResponse> getWishlistItems(Long userId) {

        List<WishlistItem> wishlistItems =
                wishlistItemRepository.findByUserId(userId);

        return wishlistItemMapper.toResponseList(wishlistItems);
    }

    @Transactional
    public WishlistItemResponse addPerfumeToWishlist(
            Long userId,
            AddWishlistItemRequest request
    ) {

        Perfume perfume = perfumeRepository.findById(request.perfumeId())
                .orElseThrow( () ->
                        new ResourceNotFoundException("Perfume not found"));

        AppUser user = userRepository.findById(userId)
                .orElseThrow( () ->
                        new ResourceNotFoundException("User not found"));

        if (wishlistItemRepository.existsByUserIdAndPerfumeId(userId, request.perfumeId())) {
            throw new DuplicateResourceException("User has already wishlisted this perfume");
        }

        WishlistItem wishlistItem = new WishlistItem(
                perfume,
                user
        );

        WishlistItem savedItem = wishlistItemRepository.save(wishlistItem);

        return wishlistItemMapper.toResponse(savedItem);
    }

    @Transactional
    public void removePerfumeFromWishlist(Long perfumeId, Long userId) {

        WishlistItem item = wishlistItemRepository
                .findByUserIdAndPerfumeId(userId, perfumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Perfume is not in user's wishlist"
                        )
                );

        wishlistItemRepository.delete(item);
    }

}

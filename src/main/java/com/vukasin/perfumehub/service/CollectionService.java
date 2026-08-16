package com.vukasin.perfumehub.service;

import com.vukasin.perfumehub.dto.request.AddCollectionItemRequest;
import com.vukasin.perfumehub.dto.request.UpdateCollectionStatusRequest;
import com.vukasin.perfumehub.dto.response.CollectionItemResponse;
import com.vukasin.perfumehub.entity.AppUser;
import com.vukasin.perfumehub.entity.CollectionItem;
import com.vukasin.perfumehub.entity.Perfume;
import com.vukasin.perfumehub.exception.DuplicateResourceException;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.mapper.CollectionItemMapper;
import com.vukasin.perfumehub.repository.AppUserRepository;
import com.vukasin.perfumehub.repository.CollectionItemRepository;
import com.vukasin.perfumehub.repository.PerfumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CollectionService {

    private final CollectionItemRepository collectionItemRepository;
    private final PerfumeRepository perfumeRepository;
    private final AppUserRepository userRepository;
    private final CollectionItemMapper collectionItemMapper;

    public CollectionService(
            CollectionItemRepository collectionItemRepository,
            PerfumeRepository perfumeRepository,
            AppUserRepository userRepository,
            CollectionItemMapper collectionItemMapper
    ) {
        this.collectionItemRepository = collectionItemRepository;
        this.perfumeRepository = perfumeRepository;
        this.userRepository = userRepository;
        this.collectionItemMapper = collectionItemMapper;
    }

    @Transactional(readOnly = true)
    public List<CollectionItemResponse> getCollectionItems(Long userId) {

        List<CollectionItem> collectionItems =
                collectionItemRepository.findByUserId(userId);

        return collectionItemMapper.toResponseList(collectionItems);
    }

    @Transactional
    public CollectionItemResponse addPerfumeToCollection(
            Long userId,
            AddCollectionItemRequest request
    ) {

        Perfume perfume = perfumeRepository.findById(request.perfumeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Perfume not found"));

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (collectionItemRepository.existsByUserIdAndPerfumeId(userId, request.perfumeId())) {
            throw new DuplicateResourceException("Perfume is already in collection");
        }

        CollectionItem collectionItem = new CollectionItem(
                request.status(),
                perfume,
                user
        );

        CollectionItem savedItem = collectionItemRepository.save(collectionItem);

        return collectionItemMapper.toResponse(savedItem);
    }

    @Transactional
    public CollectionItemResponse updateCollectionStatus(
            Long userId,
            Long perfumeId,
            UpdateCollectionStatusRequest request
    ) {

        CollectionItem collectionItem = collectionItemRepository.findByUserIdAndPerfumeId(userId,perfumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Perfume is not in collection"));

        collectionItem.setStatus(request.status());

        CollectionItem updatedItem = collectionItemRepository.save(collectionItem);

        return collectionItemMapper.toResponse(updatedItem);
    }

    @Transactional
    public void removePerfumeFromCollection(Long userId, Long perfumeId) {

        CollectionItem item = collectionItemRepository.findByUserIdAndPerfumeId(userId,perfumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Perfume is not in user's collection"));

        collectionItemRepository.delete(item);
    }

}

package com.vukasin.perfumehub.controller;

import com.vukasin.perfumehub.dto.request.AddCollectionItemRequest;
import com.vukasin.perfumehub.dto.request.UpdateCollectionStatusRequest;
import com.vukasin.perfumehub.dto.response.CollectionItemResponse;
import com.vukasin.perfumehub.security.CurrentUserProvider;
import com.vukasin.perfumehub.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collection")
public class CollectionController {

    private final CollectionService collectionService;
    private final CurrentUserProvider currentUserProvider;

    public CollectionController(
            CollectionService collectionService,
            CurrentUserProvider currentUserProvider
    ) {
        this.collectionService = collectionService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping()
    public ResponseEntity<List<CollectionItemResponse>> getCollection(
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        List<CollectionItemResponse> items = collectionService.getCollectionItems(userId);

        return ResponseEntity.ok(items);
    }

    @PostMapping("/items")
    public ResponseEntity<CollectionItemResponse> addPerfumeToCollection(
            @Valid @RequestBody AddCollectionItemRequest request,
            Authentication authentication
            ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        CollectionItemResponse response = collectionService.addPerfumeToCollection(
                userId,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/items/{perfumeId}")
    public ResponseEntity<CollectionItemResponse> updateCollectionItem(
            @PathVariable Long perfumeId,
            @Valid @RequestBody UpdateCollectionStatusRequest request,
            Authentication authentication
            ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        CollectionItemResponse response = collectionService.updateCollectionStatus(
                userId,
                perfumeId,
                request
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{perfumeId}")
    public ResponseEntity<Void> removePerfumeFromCollection(
            @PathVariable Long perfumeId,
            Authentication authentication
    ) {

        Long userId = currentUserProvider.getCurrentUserId(authentication);

        collectionService.removePerfumeFromCollection(userId,perfumeId);

        return ResponseEntity.noContent().build();
    }

}

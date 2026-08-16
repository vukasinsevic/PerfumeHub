package com.vukasin.perfumehub.service;

import com.vukasin.perfumehub.dto.request.CreateProductNotificationRequest;
import com.vukasin.perfumehub.dto.request.UpdateProductNotificationRequest;
import com.vukasin.perfumehub.dto.response.ProductNotificationResponse;
import com.vukasin.perfumehub.entity.AppUser;
import com.vukasin.perfumehub.entity.ProductNotification;
import com.vukasin.perfumehub.entity.ProductVariant;
import com.vukasin.perfumehub.exception.DuplicateResourceException;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.mapper.ProductNotificationMapper;
import com.vukasin.perfumehub.repository.AppUserRepository;
import com.vukasin.perfumehub.repository.ProductNotificationRepository;
import com.vukasin.perfumehub.repository.ProductVariantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductNotificationService {

    private final ProductNotificationRepository productNotificationRepository;
    private final ProductVariantRepository productVariantRepository;
    private final AppUserRepository userRepository;
    private final ProductNotificationMapper productNotificationMapper;

    public ProductNotificationService(
            ProductNotificationRepository productNotificationRepository,
            ProductVariantRepository productVariantRepository,
            AppUserRepository userRepository,
            ProductNotificationMapper productNotificationMapper
    ) {
        this.productNotificationRepository = productNotificationRepository;
        this.productVariantRepository = productVariantRepository;
        this.userRepository = userRepository;
        this.productNotificationMapper = productNotificationMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductNotificationResponse> getProductNotifications(Long userId) {

        List<ProductNotification> productNotifications =
                productNotificationRepository.findByUserId(userId);

        return productNotificationMapper.toResponseList(productNotifications);
    }

    @Transactional
    public ProductNotificationResponse createProductNotification(
            Long userId,
            CreateProductNotificationRequest request) {

        ProductVariant productVariant = productVariantRepository.findById(request.productVariantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Perfume variant not found"));

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (productNotificationRepository.existsByUserIdAndProductVariantIdAndType(
                userId, request.productVariantId(), request.type())) {
            throw new DuplicateResourceException("Notification already exists for this product variant and type");
        }

        ProductNotification productNotification = new ProductNotification(
                request.type(),
                true,
                productVariant.getPrice(),
                user,
                productVariant
        );

        ProductNotification savedProductNotification = productNotificationRepository.save(productNotification);

        return productNotificationMapper.toResponse(savedProductNotification);
    }

    @Transactional
    public ProductNotificationResponse updateProductNotification(
            Long userId,
            Long productNotificationId,
            UpdateProductNotificationRequest request
    ) {

        ProductNotification productNotification = productNotificationRepository
                .findByIdAndUserId(productNotificationId, userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Product notification not found"));

        productNotification.setActive(request.active());

        ProductNotification savedProductNotification = productNotificationRepository.save(productNotification);

        return productNotificationMapper.toResponse(savedProductNotification);
    }

    @Transactional
    public void deleteProductNotification(
            Long userId,
            Long productNotificationId
    ) {

        ProductNotification productNotification = productNotificationRepository
                .findByIdAndUserId(productNotificationId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product notification not found"
                        ));

        productNotificationRepository.delete(productNotification);
    }

}

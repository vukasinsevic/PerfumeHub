package com.vukasin.perfumehub.service;

import com.vukasin.perfumehub.dto.request.CreateProductVariantRequest;
import com.vukasin.perfumehub.dto.request.UpdateProductVariantRequest;
import com.vukasin.perfumehub.dto.response.ProductVariantResponse;
import com.vukasin.perfumehub.entity.Perfume;
import com.vukasin.perfumehub.entity.ProductVariant;
import com.vukasin.perfumehub.exception.DuplicateResourceException;
import com.vukasin.perfumehub.exception.ResourceNotFoundException;
import com.vukasin.perfumehub.mapper.ProductVariantMapper;
import com.vukasin.perfumehub.repository.PerfumeRepository;
import com.vukasin.perfumehub.repository.ProductVariantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final PerfumeRepository perfumeRepository;
    private final ProductVariantMapper productVariantMapper;

    public ProductVariantService(
            ProductVariantRepository productVariantRepository,
            PerfumeRepository perfumeRepository,
            ProductVariantMapper productVariantMapper
    ) {
        this.productVariantRepository = productVariantRepository;
        this.perfumeRepository = perfumeRepository;
        this.productVariantMapper = productVariantMapper;
    }

    @Transactional
    public ProductVariantResponse createProductVariant(
            Long perfumeId,
            CreateProductVariantRequest request) {

        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Perfume not found"));

        if (productVariantRepository.existsByVolumeMlAndPerfumeId(request.volumeMl(), perfumeId)) {
            throw new DuplicateResourceException("Product variant already exists for this perfume and volume");
        }

        ProductVariant productVariant = new ProductVariant(
                request.volumeMl(),
                request.price(),
                request.stock(),
                request.active(),
                perfume
        );

        ProductVariant savedProductVariant = productVariantRepository.save(productVariant);

        return productVariantMapper.toResponse(savedProductVariant);
    }

    @Transactional
    public ProductVariantResponse updateProductVariant(
            Long productVariantId,
            UpdateProductVariantRequest request
    ) {

        ProductVariant productVariant = productVariantRepository.findById(productVariantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product variant not found"));

        if (productVariantRepository
                .existsByVolumeMlAndPerfumeIdAndIdNot(
                        request.volumeMl(),
                        productVariant.getPerfume().getId(),
                        productVariantId
                )) {

            throw new DuplicateResourceException(
                    "Product variant for this perfume and volume already exists"
            );
        }

        productVariant.setVolumeMl(request.volumeMl());
        productVariant.setPrice(request.price());
        productVariant.setStock(request.stock());
        productVariant.setActive(request.active());

        ProductVariant savedProductVariant = productVariantRepository.save(productVariant);

        return productVariantMapper.toResponse(savedProductVariant);
    }

    @Transactional
    public void deleteProductVariant(Long productVariantId) {

        ProductVariant productVariant = productVariantRepository.findById(productVariantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product variant not found"));

        productVariant.setActive(false);

        productVariantRepository.save(productVariant);
    }

}

package com.vukasin.perfumehub.controller.admin;

import com.vukasin.perfumehub.dto.request.CreateProductVariantRequest;
import com.vukasin.perfumehub.dto.request.UpdateProductVariantRequest;
import com.vukasin.perfumehub.dto.response.ProductVariantResponse;
import com.vukasin.perfumehub.service.ProductVariantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminProductVariantController {

    private final ProductVariantService productVariantService;

    public AdminProductVariantController(
            ProductVariantService productVariantService
    ) {
        this.productVariantService = productVariantService;
    }

    @PostMapping("/perfumes/{perfumeId}/variants")
    public ResponseEntity<ProductVariantResponse> createProductVariant(
            @PathVariable Long perfumeId,
            @Valid @RequestBody CreateProductVariantRequest request
            ) {

        ProductVariantResponse response = productVariantService.createProductVariant(
                perfumeId,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/product-variants/{variantId}")
    public ResponseEntity<ProductVariantResponse> updateProductVariant(
            @PathVariable Long variantId,
            @Valid @RequestBody UpdateProductVariantRequest request
            ) {

        ProductVariantResponse response = productVariantService.updateProductVariant(
                variantId,
                request
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/product-variants/{variantId}")
    public ResponseEntity<Void> deleteProductVariant(
            @PathVariable Long variantId
    ) {

        productVariantService.deleteProductVariant(variantId);

        return ResponseEntity.noContent().build();
    }

}

package com.vukasin.perfumehub.controller.admin;

import com.vukasin.perfumehub.dto.request.CreatePerfumeRequest;
import com.vukasin.perfumehub.dto.request.UpdatePerfumeRequest;
import com.vukasin.perfumehub.dto.response.PerfumeDetailsResponse;
import com.vukasin.perfumehub.service.PerfumeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/perfumes")
public class AdminPerfumeController {

    private final PerfumeService perfumeService;

    public AdminPerfumeController(
            PerfumeService perfumeService
    ) {
        this.perfumeService = perfumeService;
    }

    @PostMapping()
    public ResponseEntity<PerfumeDetailsResponse> createPerfume(
            @Valid @RequestBody CreatePerfumeRequest request
            ) {

        PerfumeDetailsResponse response = perfumeService.createPerfume(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{perfumeId}")
    public ResponseEntity<PerfumeDetailsResponse> updatePerfume(
            @PathVariable Long perfumeId,
            @Valid @RequestBody UpdatePerfumeRequest request
            ) {

        PerfumeDetailsResponse response = perfumeService.updatePerfume(
                perfumeId,
                request
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{perfumeId}")
    public ResponseEntity<Void> deletePerfume(
            @PathVariable Long perfumeId
    ) {

        perfumeService.deletePerfume(perfumeId);

        return ResponseEntity.noContent().build();
    }

}

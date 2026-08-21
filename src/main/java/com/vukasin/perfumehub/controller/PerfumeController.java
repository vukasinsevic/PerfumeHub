package com.vukasin.perfumehub.controller;

import com.vukasin.perfumehub.dto.response.PerfumeDetailsResponse;
import com.vukasin.perfumehub.dto.response.PerfumeSummaryResponse;
import com.vukasin.perfumehub.service.PerfumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/perfumes")
public class PerfumeController {

    private final PerfumeService perfumeService;

    public PerfumeController(
            PerfumeService perfumeService
    ) {
        this.perfumeService = perfumeService;
    }

    @GetMapping()
    public ResponseEntity<List<PerfumeSummaryResponse>> getPerfumes() {

        List<PerfumeSummaryResponse> response = perfumeService.getPerfumes();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{perfumeId}")
    public ResponseEntity<PerfumeDetailsResponse> getPerfumeDetails(
            @PathVariable Long perfumeId
    ) {

        PerfumeDetailsResponse response = perfumeService.getPerfumeDetails(perfumeId);

        return ResponseEntity.ok(response);
    }

}

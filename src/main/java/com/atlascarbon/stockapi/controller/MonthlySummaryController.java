package com.atlascarbon.stockapi.controller;

import com.atlascarbon.stockapi.dto.MonthlySummariesResponse;
import com.atlascarbon.stockapi.dto.MonthlySummary;
import com.atlascarbon.stockapi.service.MonthlySummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MonthlySummaryController {

    private final MonthlySummaryService service;

    @Autowired
    public MonthlySummaryController(MonthlySummaryService service) {
        this.service = service;
    }

    @GetMapping("/monthly-summary/{stockId}")
    public ResponseEntity<MonthlySummariesResponse> getMonthlySummaryForStock(@PathVariable long stockId) {
        List<MonthlySummary> monthlySummaries = service.getMonthlySummaryForStock(stockId);
        return ResponseEntity.ok(new MonthlySummariesResponse(monthlySummaries));
    }
}

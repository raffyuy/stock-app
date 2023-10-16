package com.atlascarbon.stockapi.controller;

import com.atlascarbon.stockapi.dto.FinalReadings;
import com.atlascarbon.stockapi.dto.MonthlySummariesResponse;
import com.atlascarbon.stockapi.dto.MonthlySummary;
import com.atlascarbon.stockapi.service.MonthlySummaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MonthlySummaryControllerTest {

    @InjectMocks
    private MonthlySummaryController controller;

    @Mock
    private MonthlySummaryService service;

    @Test
    public void getMonthlySummaryForStock_resultsInOKStatusAndReturnsCorrectNumberOfSummaries() {
        // Given
        long stockId = 1L;
        List<MonthlySummary> summaries = Arrays.asList(
                new MonthlySummary(YearMonth.of(2020, 12), 8.0, 100, 1000, new FinalReadings(100, 1000.0, new BigDecimal(100))),
                new MonthlySummary(YearMonth.of(2020, 11), 7.5, 90, 900, new FinalReadings(90, 900.0, new BigDecimal(120)))
        );
        when(service.getMonthlySummaryForStock(stockId)).thenReturn(summaries);

        // When
        ResponseEntity<MonthlySummariesResponse> responseEntity = controller.getMonthlySummaryForStock(stockId);

        // Then
        verify(service, times(1)).getMonthlySummaryForStock(stockId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        MonthlySummariesResponse response = responseEntity.getBody();
        assertEquals(2, response.summaries().size());
    }
}
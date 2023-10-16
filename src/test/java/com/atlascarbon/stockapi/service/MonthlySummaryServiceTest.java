package com.atlascarbon.stockapi.service;

import com.atlascarbon.stockapi.domain.Stock;
import com.atlascarbon.stockapi.dto.MonthlySummary;
import com.atlascarbon.stockapi.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MonthlySummaryServiceTest {

    @InjectMocks
    private MonthlySummaryService monthlySummaryService;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private Stock stock;


    @Test
    public void getMonthlySummaryForStock() {
        // Given
        long stockId = 1L;
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));
        when(stock.generateMonthlySummaryList()).thenReturn(new ArrayList<>());

        // When
        List<MonthlySummary> summaries = monthlySummaryService.getMonthlySummaryForStock(stockId);

        // Then
        verify(stockRepository, times(1)).findById(stockId);
        verify(stock, times(1)).generateMonthlySummaryList();
        assertEquals(0, summaries.size());
    }

}
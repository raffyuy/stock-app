package com.atlascarbon.stockapi.service;

import com.atlascarbon.stockapi.dto.MonthlySummary;
import com.atlascarbon.stockapi.domain.Stock;
import com.atlascarbon.stockapi.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MonthlySummaryService {

    private final StockRepository stockRepository;

    @Autowired
    public MonthlySummaryService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<MonthlySummary> getMonthlySummaryForStock(long stockId) {
        Optional<Stock> stock = stockRepository.findById(stockId);

        if(stock.isPresent()) {
            return stock.get().generateMonthlySummaryList();
        }

        return new ArrayList<>();
    }
}

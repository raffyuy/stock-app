package com.atlascarbon.stockapi.service;

import com.atlascarbon.stockapi.domain.Stock;
import com.atlascarbon.stockapi.domain.StockRecord;
import com.atlascarbon.stockapi.repository.StockRecordRepository;
import com.atlascarbon.stockapi.repository.StockRepository;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This service is used to initialise test data.
 */
@Service
@Profile("dev")
public class DataInitialisationService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockRecordRepository stockRecordRepository;

    @Value("${app.data.init-csv-path}")
    private String csvFilePath;


    public enum Headers {
        date,head,rating,weight,price,stockId,index;
    }


    @PostConstruct
    public void initialiseData() {
        Iterable<Stock> stocks = parseCSVFile();
        stockRepository.saveAll(stocks);
    }

    private Iterable<Stock> parseCSVFile() {
        CSVFormat format = CSVFormat.DEFAULT.builder().setHeader(Headers.class).setSkipHeaderRecord(true).build();
        Map<Long, Stock> stockMap = new HashMap<>();
        try (FileReader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, format)) {


            for(CSVRecord record : csvParser) {
                Long stockId = Long.parseLong(record.get(Headers.stockId));
                Stock stock = stockMap.computeIfAbsent(stockId, this::createNewStock);

                StockRecord stockRecord = createStockRecord(record, stock);
                stock.getStockRecordList().add(stockRecord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockMap.values();

    }

    private StockRecord createStockRecord(CSVRecord record, Stock stock) {
        return StockRecord.builder()
                .date(LocalDate.parse(record.get(Headers.date)))
                .head(Integer.parseInt(record.get(Headers.head)))
                .rating(Double.parseDouble(record.get(Headers.rating)))
                .weight(Double.parseDouble(record.get(Headers.weight)))
                .price(new BigDecimal(record.get(Headers.price)))
                .stock(stock)
                .index(Integer.parseInt(record.get(Headers.index)))
                .build();
    }

    private Stock createNewStock(Long stockId) {
        Stock stock = new Stock();
        stock.setStockId(stockId);
        stock.setStockRecordList(new ArrayList<>());
        return stock;
    }
}

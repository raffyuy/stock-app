package com.atlascarbon.stockapi.domain;

import com.atlascarbon.stockapi.dto.MonthlySummary;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StockTest {

    private Stock getTestStock() {
        Stock stock = new Stock();
        List<StockRecord> stockRecordList = List.of(
                new StockRecord(1L, stock, LocalDate.parse("2020-12-30"), 4, 5, 30, BigDecimal.valueOf(4), 1),
                new StockRecord(1L, stock, LocalDate.parse("2020-12-03"), 3, 5, 15, BigDecimal.valueOf(1), 2),
                new StockRecord(1L, stock, LocalDate.parse("2020-12-03"), 5, 10, 20, BigDecimal.valueOf(2), 1),
                new StockRecord(1L, stock, LocalDate.parse("2020-12-01"), 1, 10, 10, BigDecimal.valueOf(3), 1)
        );
        stock.setStockRecordList(stockRecordList);
        return stock;
    }

    /*
      Note: No need to implement tests with same date, different index since the @OrderBy annotation guarantees the
      array is sorted by date desc, then index desc.
     */
    @Test
    void generateMonthlySummaryList_CalculatesSummariesCorrectly() {
        //Given
        Stock stock = new Stock();
        List<StockRecord> stockRecordList = List.of(
                new StockRecord(1L, stock, LocalDate.parse("2020-12-30"), 4, 5, 30, BigDecimal.valueOf(4), 1),
                new StockRecord(1L, stock, LocalDate.parse("2020-12-03"), 3, 5, 15, BigDecimal.valueOf(1), 2),
                new StockRecord(1L, stock, LocalDate.parse("2020-12-03"), 5, 10, 20, BigDecimal.valueOf(2), 1),
                new StockRecord(1L, stock, LocalDate.parse("2020-12-01"), 1, 10, 10, BigDecimal.valueOf(3), 1)
        );
        stock.setStockRecordList(stockRecordList);

        //When
        List<MonthlySummary> monthlySummaries = stock.generateMonthlySummaryList();

        //Then
        assertThat(monthlySummaries).isNotEmpty();
        assertEquals(4, monthlySummaries.get(0).finalReadings().head(), "Incorrect final head");
        assertEquals(30, monthlySummaries.get(0).finalReadings().weight(), "Incorrect final weight");
        assertEquals(BigDecimal.valueOf(4), monthlySummaries.get(0).finalReadings().price(), "Incorrect final price");
        assertEquals(7.5, monthlySummaries.get(0).averageMonthlyRating(), "Incorrect average rating");
        assertEquals(4, monthlySummaries.get(0).recordCount(), "Incorrect record count");
        assertEquals(3, monthlySummaries.get(0).headChange(), "Incorrect head change");
    }


    @Test
    void generateMonthlySummaryList_GroupsSummariesByYearMonthInDescendingOrder() {
        //Given
        Stock stock = new Stock();
        List<StockRecord> stockRecordList = List.of(
                new StockRecord(1L, stock, LocalDate.parse("2020-12-30"), 4, 5, 30, BigDecimal.valueOf(4), 1),
                new StockRecord(1L, stock, LocalDate.parse("2020-12-03"), 3, 5, 15, BigDecimal.valueOf(1), 2),
                new StockRecord(1L, stock, LocalDate.parse("2020-11-03"), 5, 10, 20, BigDecimal.valueOf(2), 1),
                new StockRecord(1L, stock, LocalDate.parse("2020-11-01"), 1, 10, 10, BigDecimal.valueOf(3), 1)
        );
        stock.setStockRecordList(stockRecordList);

        //When
        List<MonthlySummary> monthlySummaries = stock.generateMonthlySummaryList();

        //Then
        assertThat(monthlySummaries).isNotEmpty();
        assertEquals(2, monthlySummaries.size());

        MonthlySummary firstSummary = monthlySummaries.get(0);
        assertEquals(YearMonth.of(2020, 12), firstSummary.month());
        assertEquals(4, firstSummary.finalReadings().head(), "Incorrect final head");
        assertEquals(30, firstSummary.finalReadings().weight(), "Incorrect final weight");
        assertEquals(BigDecimal.valueOf(4), firstSummary.finalReadings().price(), "Incorrect final price");
        assertEquals(5, firstSummary.averageMonthlyRating(), "Incorrect average rating");
        assertEquals(2, firstSummary.recordCount(), "Incorrect record count");
        assertEquals(1, firstSummary.headChange(), "Incorrect head change");

        MonthlySummary secondSummary = monthlySummaries.get(1);
        assertEquals(YearMonth.of(2020, 11), secondSummary.month());
        assertEquals(5, secondSummary.finalReadings().head(), "Incorrect final head");
        assertEquals(20, secondSummary.finalReadings().weight(), "Incorrect final weight");
        assertEquals(BigDecimal.valueOf(2), secondSummary.finalReadings().price(), "Incorrect final price");
        assertEquals(10, secondSummary.averageMonthlyRating(), "Incorrect average rating");
        assertEquals(2, secondSummary.recordCount(), "Incorrect record count");
        assertEquals(4, secondSummary.headChange(), "Incorrect head change");
    }

    @Test
    void generateMonthlySummaryList_HeadChangeIsNegativeWhenFinalHeadIsReduced() {
        Stock stock = new Stock();
        stock.setStockRecordList(new ArrayList<>());

        //When
        List<MonthlySummary> monthlySummaries = stock.generateMonthlySummaryList();

        //Then
        assertEquals(0, monthlySummaries.size());
    }


    @Test
    void generateMonthlySummaryList_EmptyRecord() {
        //Given
        Stock stock = new Stock();
        List<StockRecord> stockRecordList = List.of(
                new StockRecord(1L, stock, LocalDate.parse("2020-12-30"), 1, 5, 30, BigDecimal.valueOf(4), 1),
                new StockRecord(1L, stock, LocalDate.parse("2020-12-01"), 4, 10, 10, BigDecimal.valueOf(3), 1)
        );
        stock.setStockRecordList(stockRecordList);

        //When
        List<MonthlySummary> monthlySummaries = stock.generateMonthlySummaryList();

        //Then
        assertEquals(-3, monthlySummaries.get(0).headChange());
    }
}
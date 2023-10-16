package com.atlascarbon.stockapi.domain;

import com.atlascarbon.stockapi.dto.FinalReadings;
import com.atlascarbon.stockapi.dto.MonthlySummary;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_id", unique = true)
    private Long stockId;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.PERSIST)
    @OrderBy("date DESC, index DESC")
    @ToString.Exclude
    private List<StockRecord> stockRecordList;


    public List<MonthlySummary> generateMonthlySummaryList() {
        // Collect StockRecords into a LinkedHashMap grouped by Month-Year
        Map<YearMonth, List<StockRecord>> recordsByMonthYear = stockRecordList.stream()
                .collect(Collectors.groupingBy(record -> YearMonth.from(record.getDate()),
                        LinkedHashMap::new, // Use LinkedHashMap to maintain order from @OrderBy annotation
                        Collectors.toList()));

        return recordsByMonthYear.entrySet().stream()
                .map(this::mapStockRecordsToMonthlySummaries)
                .collect(Collectors.toList());
    }

    private MonthlySummary mapStockRecordsToMonthlySummaries(Map.Entry<YearMonth, List<StockRecord>> entry) {
            YearMonth monthYear = entry.getKey();
            List<StockRecord> records = entry.getValue();

            // Calculate average rating
            double averageRating = records.stream()
                    .mapToDouble(StockRecord::getRating)
                    .average()
                    .orElse(0.0);

            // Calculate final readings
            StockRecord lastRecord = records.get(0);
            StockRecord firstRecord = records.get(records.size() - 1);
            FinalReadings finalReadings = new FinalReadings(
                    lastRecord.getHead(),
                    lastRecord.getWeight(),
                    lastRecord.getPrice()
            );

            // Calculate number of updates
            int numberOfUpdates = records.size();

            // Calculate change in head
            int headChange = lastRecord.getHead() - firstRecord.getHead();

            // Create MonthlySummary
            return new MonthlySummary(
                    monthYear,
                    averageRating,
                    headChange,
                    numberOfUpdates,
                    finalReadings
            );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Stock stock = (Stock) o;
        return id != null && Objects.equals(id, stock.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


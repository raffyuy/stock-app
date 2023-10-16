package com.atlascarbon.stockapi.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.YearMonth;

public record MonthlySummary(@JsonFormat(pattern = "MMM-yyyy", locale = "en_US") YearMonth month,
                             double averageMonthlyRating,
                             int headChange, int recordCount, FinalReadings finalReadings) {
}
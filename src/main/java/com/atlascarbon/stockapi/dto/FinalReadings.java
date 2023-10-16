package com.atlascarbon.stockapi.dto;

import java.math.BigDecimal;

public record FinalReadings(int head, double weight, BigDecimal price) {
}

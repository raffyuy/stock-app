package com.atlascarbon.stockapi.repository;

import com.atlascarbon.stockapi.domain.StockRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRecordRepository extends JpaRepository<StockRecord, Long> {
}

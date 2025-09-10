package com.pricemanager.domain.repository;

import com.pricemanager.application.data.PriceData;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Price Repository Interface Port to define the behavior of Repository interactions
 * */
public interface PriceRepository {
    List<PriceData> findPrices(Long productId, Long brandId, LocalDateTime date);
}

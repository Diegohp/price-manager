package com.pricemanager.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Main Domain model class contract to handle Price requests
 * */
@Getter
public class Price {
    private final Long brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer priceList;
    private final Long productId;
    private final Integer priority;
    private final BigDecimal price;
    private final String currency;

    public Price(Long brandId, LocalDateTime startDate, LocalDateTime endDate, Integer priceList,
                 Long productId, Integer priority, BigDecimal price, String currency) {
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.currency = currency;
    }
}

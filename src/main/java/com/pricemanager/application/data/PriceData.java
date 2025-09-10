package com.pricemanager.application.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Price Data class to get data from H2 database
 * */
@Getter
@Builder
@AllArgsConstructor
public class PriceData {
    private Long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Long productId;
    private Integer priority;
    private BigDecimal price;
    private String currency;
}

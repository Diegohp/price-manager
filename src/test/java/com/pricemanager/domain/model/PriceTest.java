package com.pricemanager.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceTest {

    @Test
    void constructorShouldInitializeAllFieldsCorrectly() {
        Long brandId = 1L;
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        Integer priceList = 1;
        Long productId = 35455L;
        Integer priority = 0;
        BigDecimal price = BigDecimal.valueOf(35.50);
        String currency = "EUR";

        Price priceObj = new Price(brandId, startDate, endDate, priceList, productId, priority, price, currency);

        assertEquals(brandId, priceObj.getBrandId());
        assertEquals(startDate, priceObj.getStartDate());
        assertEquals(endDate, priceObj.getEndDate());
        assertEquals(priceList, priceObj.getPriceList());
        assertEquals(productId, priceObj.getProductId());
        assertEquals(priority, priceObj.getPriority());
        assertEquals(price, priceObj.getPrice());
        assertEquals(currency, priceObj.getCurrency());
    }
}

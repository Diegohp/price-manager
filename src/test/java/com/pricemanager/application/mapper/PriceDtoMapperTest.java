package com.pricemanager.application.mapper;

import com.pricemanager.application.dto.PriceDto;
import com.pricemanager.domain.model.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceDtoMapperTest {

    @Test
    void shouldMapDomainToDtoCorrectly() {
        Price price = new Price(
                1L,
                LocalDateTime.of(2020, 6, 14, 10, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                1,
                35455L,
                0,
                BigDecimal.valueOf(35.50),
                "EUR"
        );

        PriceDto dto = PriceDtoMapper.fromDomain(price);

        assertEquals(price.getBrandId(), dto.getBrandId());
        assertEquals(price.getProductId(), dto.getProductId());
        assertEquals(price.getStartDate(), dto.getStartDate());
        assertEquals(price.getEndDate(), dto.getEndDate());
        assertEquals(price.getPriceList(), dto.getPriceList());
        assertEquals(price.getPriority(), dto.getPriority());
        assertEquals(price.getPrice(), dto.getPrice());
        assertEquals(price.getCurrency(), dto.getCurrency());
    }
}

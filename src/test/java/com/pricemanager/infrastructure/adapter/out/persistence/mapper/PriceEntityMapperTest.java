package com.pricemanager.infrastructure.adapter.out.persistence.mapper;

import com.pricemanager.application.data.PriceData;
import com.pricemanager.infrastructure.adapter.out.persistence.PriceEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PriceEntityMapperTest {

    @Test
    void shouldMapEntityToDataSuccessfully() {
        // Given
        PriceEntity entity = new PriceEntity();
        entity.setBrandId(1L);
        entity.setProductId(35455L);
        entity.setStartDate(LocalDateTime.of(2020, 6, 14, 10, 0));
        entity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        entity.setPriceList(1);
        entity.setPriority(0);
        entity.setPrice(BigDecimal.valueOf(35.50));
        entity.setCurrency("EUR");

        // When
        PriceData data = PriceEntityMapper.toData(entity);

        // Then
        assertNotNull(data);
        assertEquals(entity.getBrandId(), data.getBrandId());
        assertEquals(entity.getProductId(), data.getProductId());
        assertEquals(entity.getStartDate(), data.getStartDate());
        assertEquals(entity.getEndDate(), data.getEndDate());
        assertEquals(entity.getPriceList(), data.getPriceList());
        assertEquals(entity.getPriority(), data.getPriority());
        assertEquals(entity.getPrice(), data.getPrice());
        assertEquals(entity.getCurrency(), data.getCurrency());
    }

    @Test
    void shouldReturnNullWhenMappingNullEntityToData() {
        assertNull(PriceEntityMapper.toData(null));
    }
}

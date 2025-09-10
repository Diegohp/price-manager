package com.pricemanager.infrastructure.adapter.out.persistence;

import com.pricemanager.application.data.PriceData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class JpaPriceRepositoryTest {

    @Mock
    private SpringDataPriceRepository springDataRepo;

    private JpaPriceRepository jpaPriceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jpaPriceRepository = new JpaPriceRepository(springDataRepo);
    }

    @Test
    void shouldConvertEntitiesToPriceData() {
        LocalDateTime now = LocalDateTime.of(2020, 6, 14, 10, 0);

        PriceEntity entity = new PriceEntity();
        entity.setBrandId(1L);
        entity.setStartDate(now.minusDays(1));
        entity.setEndDate(now.plusDays(1));
        entity.setPriceList(1);
        entity.setProductId(35455L);
        entity.setPriority(1);
        entity.setPrice(BigDecimal.valueOf(25.99));
        entity.setCurrency("EUR");

        when(springDataRepo.findByProductIdAndBrandIdAndDate(35455L, 1L, now))
                .thenReturn(List.of(entity));

        List<PriceData> prices = jpaPriceRepository.findPrices(35455L, 1L, now);

        assertThat(prices).hasSize(1);
        PriceData price = prices.get(0);

        assertEquals(35455L, price.getProductId());
        assertEquals(1L, price.getBrandId());
        assertThat(price.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(25.99));
        assertEquals("EUR", price.getCurrency());
        assertEquals(now.minusDays(1), price.getStartDate());
        assertEquals(now.plusDays(1), price.getEndDate());
        assertEquals(1, price.getPriceList());
        assertEquals(1, price.getPriority());
    }

    @Test
    void shouldReturnEmptyListWhenNoDataFound() {
        LocalDateTime now = LocalDateTime.of(2020, 6, 14, 10, 0);
        when(springDataRepo.findByProductIdAndBrandIdAndDate(35455L, 1L, now))
                .thenReturn(List.of());

        List<PriceData> prices = jpaPriceRepository.findPrices(35455L, 1L, now);

        assertNotNull(prices);
        assertTrue(prices.isEmpty());
    }
}

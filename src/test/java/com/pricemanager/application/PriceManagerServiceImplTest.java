package com.pricemanager.application;

import com.pricemanager.application.data.PriceData;
import com.pricemanager.application.dto.PriceDto;
import com.pricemanager.domain.repository.PriceRepository;
import com.pricemanager.infrastructure.adapter.in.rest.exception.MissingFieldsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceManagerServiceImplTest {

    private PriceRepository priceRepository;
    private PriceManagerServiceImpl service;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        service = new PriceManagerServiceImpl(priceRepository);
    }

    @Test
    void shouldReturnPriceDtoWhenValidInputsProvided() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        String dateString = "2020-06-14T10:00:00";
        LocalDateTime parsedDate = LocalDateTime.parse(dateString);

        PriceData mockPriceData = PriceData.builder()
                .brandId(brandId)
                .productId(productId)
                .startDate(parsedDate.minusHours(1))
                .endDate(parsedDate.plusHours(1))
                .priority(1)
                .build();

        when(priceRepository.findPrices(productId, brandId, parsedDate))
                .thenReturn(List.of(mockPriceData));

        // When
        Optional<PriceDto> result = service.getApplicablePrice(productId, brandId, dateString);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getBrandId()).isEqualTo(brandId);
        assertThat(result.get().getProductId()).isEqualTo(productId);
        assertThat(result.get().getPrice()).isEqualTo(mockPriceData.getPrice());
    }

    @Test
    void shouldReturnEmptyWhenNoPricesFound() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        String dateString = "2020-06-14T10:00:00";
        LocalDateTime parsedDate = LocalDateTime.parse(dateString);

        when(priceRepository.findPrices(productId, brandId, parsedDate))
                .thenReturn(List.of());

        // When
        Optional<PriceDto> result = service.getApplicablePrice(productId, brandId, dateString);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowMissingFieldsExceptionWhenBrandIdIsNull() {
        // Given
        Long productId = 35455L;
        Long brandId = null;
        String dateString = "2020-06-14T10:00:00";

        // When / Then
        assertThatThrownBy(() -> service.getApplicablePrice(productId, brandId, dateString))
                .isInstanceOf(MissingFieldsException.class)
                .hasMessageContaining("Brand ID");
    }

    @Test
    void shouldThrowMissingFieldsExceptionWhenProductIdIsNull() {
        // Given
        Long productId = null;
        Long brandId = 1L;
        String dateString = "2020-06-14T10:00:00";

        // When / Then
        assertThatThrownBy(() -> service.getApplicablePrice(productId, brandId, dateString))
                .isInstanceOf(MissingFieldsException.class)
                .hasMessageContaining("Product ID");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDateIsInvalid() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        String dateString = "invalid-date";

        // When / Then
        assertThatThrownBy(() -> service.getApplicablePrice(productId, brandId, dateString))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid date format");
    }

    @Test
    void shouldSelectPriceWithHighestPriorityWhenMultiplePricesReturned() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        String dateString = "2020-06-14T10:00:00";
        LocalDateTime parsedDate = LocalDateTime.parse(dateString);

        PriceData lowPriority = PriceData.builder()
                .priority(1)
                .productId(productId)
                .brandId(brandId)
                .startDate(parsedDate.minusHours(1))
                .endDate(parsedDate.plusHours(1))
                .build();

        PriceData highPriority = PriceData.builder()
                .priority(5)
                .productId(productId)
                .brandId(brandId)
                .startDate(parsedDate.minusHours(1))
                .endDate(parsedDate.plusHours(1))
                .build();

        when(priceRepository.findPrices(productId, brandId, parsedDate))
                .thenReturn(List.of(lowPriority, highPriority));

        // When
        Optional<PriceDto> result = service.getApplicablePrice(productId, brandId, dateString);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getPriority()).isEqualTo(5);
    }
}

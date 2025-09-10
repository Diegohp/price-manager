package com.pricemanager.infrastructure.adapter.in.rest;

import com.pricemanager.application.PriceManagerService;
import com.pricemanager.application.dto.PriceDto;
import com.pricemanager.infrastructure.adapter.in.rest.controller.PriceManagerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceManagerControllerTest {

    private PriceManagerService service;
    private PriceManagerController controller;

    @BeforeEach
    void setUp() {
        service = mock(PriceManagerService.class);
        controller = new PriceManagerController(service);
    }

    @Test
    void shouldReturnPriceDtoWhenFound() {
        PriceDto dto = new PriceDto();
        dto.setPrice(BigDecimal.valueOf(25.00));
        dto.setCurrency("EUR");

        when(service.getApplicablePrice(1L, 1L, "2020-06-14T10:00:00"))
                .thenReturn(Optional.of(dto));

        ResponseEntity<?> response = controller.getPrice(1L, 1L, "2020-06-14T10:00:00");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenNoPriceAvailable() {
        when(service.getApplicablePrice(1L, 1L, "2020-06-14T10:00:00"))
                .thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.getPrice(1L, 1L, "2020-06-14T10:00:00");

        assertEquals(404, response.getStatusCodeValue());
    }
}


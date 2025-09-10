package com.pricemanager.infrastructure.adapter.in.rest.controller;

import com.pricemanager.application.PriceManagerService;
import com.pricemanager.application.dto.PriceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controller to expose API endpoint and get input parameters for Application Service
 * */
@RestController
@RequestMapping("/prices")
public class PriceManagerController {

    private final PriceManagerService service;

    public PriceManagerController(PriceManagerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getPrice(
            @RequestParam Long productId,
            @RequestParam Long brandId,
            @RequestParam String date
    ) {
        Optional<PriceDto> price = service.getApplicablePrice(productId, brandId, date);
        return price
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

package com.pricemanager.application;

import com.pricemanager.application.dto.PriceDto;

import java.util.Optional;

/**
 * Price Manager Service Interface to define the contract for Service usage
 * */
public interface PriceManagerService {
    Optional<PriceDto> getApplicablePrice(Long productId, Long brandId, String date);
}

package com.pricemanager.application.mapper;

import com.pricemanager.domain.model.Price;
import com.pricemanager.application.dto.PriceDto;

/**
 * This class could be changed by MapStruct dependency, but it was not considered for simplicity
 */
public class PriceDtoMapper {

    public static PriceDto fromDomain(Price price) {
        PriceDto dto = new PriceDto();
        dto.setBrandId(price.getBrandId());
        dto.setProductId(price.getProductId());
        dto.setStartDate(price.getStartDate());
        dto.setEndDate(price.getEndDate());
        dto.setPriceList(price.getPriceList());
        dto.setPriority(price.getPriority());
        dto.setPrice(price.getPrice());
        dto.setCurrency(price.getCurrency());
        return dto;
    }
}

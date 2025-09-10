package com.pricemanager.infrastructure.adapter.out.persistence.mapper;

import com.pricemanager.application.data.PriceData;
import com.pricemanager.infrastructure.adapter.out.persistence.PriceEntity;

/**
 * Price Entity Mapper builder to generate PriceData object from Entity
 * */
public class PriceEntityMapper {

    public static PriceData toData(PriceEntity entity) {
        if (entity == null) {
            return null;
        }

        return PriceData.builder()
                .brandId(entity.getBrandId())
                .productId(entity.getProductId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priceList(entity.getPriceList())
                .priority(entity.getPriority())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .build();
    }
}

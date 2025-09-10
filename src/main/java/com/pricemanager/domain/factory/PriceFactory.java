package com.pricemanager.domain.factory;

import com.pricemanager.domain.model.Price;
import com.pricemanager.application.data.PriceData;

/**
 * Price Factory class to convert from PriceData object given to Price Model Object
 * */
public class PriceFactory {

    public static Price fromData(PriceData data) {
        return new Price(
                data.getBrandId(),
                data.getStartDate(),
                data.getEndDate(),
                data.getPriceList(),
                data.getProductId(),
                data.getPriority(),
                data.getPrice(),
                data.getCurrency()
        );
    }
}

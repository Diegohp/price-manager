package com.pricemanager.infrastructure.adapter.out.persistence;

import com.pricemanager.application.data.PriceData;
import com.pricemanager.domain.repository.PriceRepository;
import com.pricemanager.infrastructure.adapter.out.persistence.mapper.PriceEntityMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JPA Price Repository which implements the contract for Price Repository
 * */
@Repository
public class JpaPriceRepository implements PriceRepository {

    private final SpringDataPriceRepository jpa;

    public JpaPriceRepository(SpringDataPriceRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<PriceData> findPrices(Long productId, Long brandId, LocalDateTime date) {
        return jpa.findByProductIdAndBrandIdAndDate(productId, brandId, date)
                .stream()
                .map(PriceEntityMapper::toData)
                .collect(Collectors.toList());
    }
}

package com.pricemanager.application;

import com.pricemanager.application.data.PriceData;
import com.pricemanager.application.dto.PriceDto;
import com.pricemanager.application.mapper.PriceDtoMapper;
import com.pricemanager.domain.factory.PriceFactory;
import com.pricemanager.domain.model.Price;
import com.pricemanager.domain.repository.PriceRepository;
import com.pricemanager.infrastructure.adapter.in.rest.exception.MissingFieldsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Application Service to get applicable price based on parameters
 * given by Infrastructure layer, which validates the input and returns
 * the DTO object given by Adapter Persistence Repository
 * */
@Service
public class PriceManagerServiceImpl implements PriceManagerService {

    private final PriceRepository repository;

    public PriceManagerServiceImpl(PriceRepository repository) {
        this.repository = repository;
    }

    public Optional<PriceDto> getApplicablePrice(Long productId, Long brandId, String dateString) {

        List<String> missingFields = validateMissingFields(productId, brandId, dateString);

        // If there are any missing fields, throw an exception
        if (!missingFields.isEmpty()) {
            throw new MissingFieldsException(missingFields);
        }

        // If validation passes, proceed with the business logic
        LocalDateTime date = validateAndParseDate(dateString);

        // Find the Price applicable if exists from repository
        List<PriceData> dataList = repository.findPrices(productId, brandId, date);

        Optional<Price> price = dataList.stream()
                .map(PriceFactory::fromData)
                .max(Comparator.comparingInt(Price::getPriority));

        // Returns a DTO object to show it on the Controller
        return price.map(PriceDtoMapper::fromDomain);
    }

    // Validation logic

    // Collect missing or invalid fields
    private List<String> validateMissingFields(Long productId, Long brandId, String dateString) {
        List<String> missingFields = new ArrayList<>();

        if (productId == null || productId <= 0) {
            missingFields.add("Product ID");
        }

        if (brandId == null || brandId <= 0) {
            missingFields.add("Brand ID");
        }

        if (dateString == null || dateString.trim().isEmpty()) {
            missingFields.add("Date");
        }

        return missingFields;
    }

    // Validating and parsing the date
    private LocalDateTime validateAndParseDate(String dateString) {

        List<DateTimeFormatter> formatters = List.of(
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,            // specific format expected by H2 database (ISO 8601)
                DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss")  // Custom format given in the exercise example
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }

        throw new IllegalArgumentException(
                "Invalid date format. Accepted formats: " +
                        "yyyy-MM-dd'T'HH:mm:ss (e.g., 2020-06-14T10:00:00), " +
                        "yyyy-MM-dd-HH.mm.ss (e.g., 2020-06-14-10.00.00)"
        );
    }
}

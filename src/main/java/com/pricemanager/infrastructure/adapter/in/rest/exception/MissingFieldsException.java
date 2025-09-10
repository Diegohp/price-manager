package com.pricemanager.infrastructure.adapter.in.rest.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class MissingFieldsException extends RuntimeException {

    private final List<String> missingFields;

    public MissingFieldsException(List<String> fields) {
        super("Missing required fields: " + String.join(", ", fields));
        this.missingFields = fields;
    }
}

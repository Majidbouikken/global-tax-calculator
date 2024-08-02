package com.odealim.global_tax_calculator.infrastructure.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    String message;

    String details;

    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }
}
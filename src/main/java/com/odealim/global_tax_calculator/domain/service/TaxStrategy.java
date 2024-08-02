package com.odealim.global_tax_calculator.domain.service;

import com.odealim.global_tax_calculator.domain.model.Product;

import java.math.BigDecimal;

public interface TaxStrategy {
    BigDecimal calculateTax(Product product);
}

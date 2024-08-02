package com.odealim.global_tax_calculator.domain.product;

import com.odealim.global_tax_calculator.domain.product.Product;

import java.math.BigDecimal;

public interface TaxStrategy {
        BigDecimal calculateTax(Product product);
}

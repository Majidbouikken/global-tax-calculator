package com.odealim.global_tax_calculator.domain.product;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CanadaTaxStrategy implements TaxStrategy {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.05");

    @Override
    public BigDecimal calculateTax(Product product) {
        return product.getPrice().multiply(TAX_RATE);
    }
}

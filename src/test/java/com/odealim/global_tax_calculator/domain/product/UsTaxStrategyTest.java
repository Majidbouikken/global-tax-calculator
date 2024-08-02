package com.odealim.global_tax_calculator.domain.product;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsTaxStrategyTest {
    private final UsTaxStrategy taxStrategy = new UsTaxStrategy();

    @Test
    public void testCalculateTax() {
        // Arranger
        Product product = mock(Product.class);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(1000));
        when(product.getCountry()).thenReturn(Country.USA);

        // Agir
        BigDecimal tax = taxStrategy.calculateTax(product);

        // Affirmer
        BigDecimal expectedTax = BigDecimal.valueOf(70.00);
        assertThat(expectedTax,  Matchers.comparesEqualTo(tax));
    }
}
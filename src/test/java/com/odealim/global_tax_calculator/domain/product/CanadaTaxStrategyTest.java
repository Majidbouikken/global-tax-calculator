package com.odealim.global_tax_calculator.domain.product;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CanadaTaxStrategyTest {
    private final CanadaTaxStrategy taxStrategy = new CanadaTaxStrategy();

    @Test
    public void testCalculateTax() {
        // Arranger
        Product product = mock(Product.class);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(1000));
        when(product.getCountry()).thenReturn(Country.CANADA);

        // Agir
        BigDecimal tax = taxStrategy.calculateTax(product);

        // Affirmer
        BigDecimal expectedTax = BigDecimal.valueOf(50.00);
        assertThat(expectedTax,  Matchers.comparesEqualTo(tax));
    }
}
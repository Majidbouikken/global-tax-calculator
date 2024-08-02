package com.odealim.global_tax_calculator.domain.product;

import com.odealim.global_tax_calculator.domain.product.tax_strategy.FranceTaxStrategy;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FranceTaxStrategyTest {
    private final FranceTaxStrategy taxStrategy = new FranceTaxStrategy();

    @Test
    public void testCalculateTax() {
        // Arranger
        Product product = mock(Product.class);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(1000));
        when(product.getCountry()).thenReturn(Country.FRANCE);

        // Agir
        BigDecimal tax = taxStrategy.calculateTax(product);

        // Affirmer
        BigDecimal expectedTax = BigDecimal.valueOf(200.00);
        assertThat(expectedTax,  Matchers.comparesEqualTo(tax));
    }
}
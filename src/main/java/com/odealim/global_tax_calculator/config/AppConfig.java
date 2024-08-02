package com.odealim.global_tax_calculator.config;

import com.odealim.global_tax_calculator.domain.product.*;
import com.odealim.global_tax_calculator.domain.product.tax_strategy.CanadaTaxStrategy;
import com.odealim.global_tax_calculator.domain.product.tax_strategy.FranceTaxStrategy;
import com.odealim.global_tax_calculator.domain.product.tax_strategy.TaxStrategy;
import com.odealim.global_tax_calculator.domain.product.tax_strategy.UsTaxStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AppConfig {
    @Bean
    public Map<Country, TaxStrategy> taxStrategies() {
        return Map.of(
                Country.USA, new UsTaxStrategy(),
                Country.CANADA, new CanadaTaxStrategy(),
                Country.FRANCE, new FranceTaxStrategy()
        );
    }
}
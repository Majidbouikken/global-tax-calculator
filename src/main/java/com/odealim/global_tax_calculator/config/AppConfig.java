package com.odealim.global_tax_calculator.config;

import com.odealim.global_tax_calculator.domain.product.*;
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
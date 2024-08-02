package com.odealim.global_tax_calculator.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PriceDTO {
    private BigDecimal price;
    private BigDecimal tax;
    private BigDecimal finalPrice;
}
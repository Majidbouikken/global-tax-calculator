package com.odealim.global_tax_calculator.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    BigDecimal price;
    @Enumerated(EnumType.STRING)
    Country country;
}

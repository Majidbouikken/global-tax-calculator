package com.odealim.global_tax_calculator.application.service;

import com.odealim.global_tax_calculator.domain.product.Country;
import com.odealim.global_tax_calculator.domain.product.PriceDTO;
import com.odealim.global_tax_calculator.domain.product.Product;
import com.odealim.global_tax_calculator.domain.product.TaxStrategy;
import com.odealim.global_tax_calculator.infrastructure.exception.ProductNotFoundException;
import com.odealim.global_tax_calculator.infrastructure.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final Map<Country, TaxStrategy> taxStrategies;

    public ProductService(ProductRepository productRepository, Map<Country, TaxStrategy> taxStrategies) {
        this.productRepository = productRepository;
        this.taxStrategies = taxStrategies;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public PriceDTO getFinalPrice(Long productId) {
        Product product = getProductById(productId);

        Country country = product.getCountry();
        TaxStrategy taxStrategy = taxStrategies.get(country);

        if (taxStrategy == null) {
            throw new IllegalArgumentException("No tax strategy found for country: " + country);
        }

        BigDecimal tax = taxStrategy.calculateTax(product); // On calcule la tax
        BigDecimal finalPrice = product.getPrice().add(tax); // On ajout la tax au prix initial

        return new PriceDTO(product.getPrice(), tax, finalPrice);
    }
}

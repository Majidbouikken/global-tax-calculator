package com.odealim.global_tax_calculator.application.controller;

import com.odealim.global_tax_calculator.application.service.ProductService;
import com.odealim.global_tax_calculator.domain.product.PriceDTO;
import com.odealim.global_tax_calculator.domain.product.Product;
import com.odealim.global_tax_calculator.infrastructure.exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
//@Api(value = "Product API", tags = "")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Ajouter un nouveau Produit")
    @PostMapping
    public ResponseEntity<Object> createProduct(@Valid @RequestBody Product product, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Product savedProduct = productService.createProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer les détails d'un produit par son ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Product());
        }
    }

    @Operation(summary = "Calculer et retourner le prix final d'un produit incluant les taxes, basé sur le pays.")
    @GetMapping("/calculate-price/{id}")
    public ResponseEntity<PriceDTO> calculateFinalPrice(@PathVariable("id") Long id) {
        try {
            PriceDTO priceDTO = productService.getFinalPrice(id);
            return ResponseEntity.ok(priceDTO);
        } catch (ProductNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new PriceDTO(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
        }
    }
}

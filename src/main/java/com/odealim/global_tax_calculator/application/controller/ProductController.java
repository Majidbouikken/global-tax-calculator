package com.odealim.global_tax_calculator.application.controller;

import com.odealim.global_tax_calculator.application.service.ProductService;
import com.odealim.global_tax_calculator.domain.product.PriceDTO;
import com.odealim.global_tax_calculator.domain.product.Product;
import com.odealim.global_tax_calculator.infrastructure.exception.ErrorResponse;
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
    public ResponseEntity<Object> getProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("Product not found", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid request", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @Operation(summary = "Calculer et retourner le prix final d'un produit incluant les taxes, basé sur le pays.")
    @GetMapping("/calculate-price/{id}")
    public ResponseEntity<Object> calculateFinalPrice(@PathVariable("id") Long id) {
        try {
            PriceDTO priceDTO = productService.getFinalPrice(id);
            return ResponseEntity.ok(priceDTO);
        } catch (ProductNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("Product not found", e.getMessage());
            System.out.println(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid request", e.getMessage());
            System.out.println(errorResponse);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}

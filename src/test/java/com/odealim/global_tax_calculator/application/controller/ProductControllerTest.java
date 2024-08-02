package com.odealim.global_tax_calculator.application.controller;

import com.odealim.global_tax_calculator.application.service.ProductService;
import com.odealim.global_tax_calculator.domain.product.Country;
import com.odealim.global_tax_calculator.domain.product.PriceDTO;
import com.odealim.global_tax_calculator.domain.product.Product;
import com.odealim.global_tax_calculator.infrastructure.exception.ErrorResponse;
import com.odealim.global_tax_calculator.infrastructure.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct_ValidProduct() {
        // Arranger
        Product product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setPrice(BigDecimal.valueOf(100));
        product.setCountry(Country.USA);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.createProduct(product)).thenReturn(product);

        // Agir
        ResponseEntity<Object> responseEntity = productController.createProduct(product, bindingResult);

        // Affirmer
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode(), "HTTP status should be CREATED");
        assertEquals(product, responseEntity.getBody(), "Response body should contain the saved product");
    }

    @Test
    public void testCreateProduct_InvalidProduct() {
        // Arranger
        Product product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setPrice(BigDecimal.valueOf(100));
        product.setCountry(Country.USA);

        FieldError error = new FieldError("product", "field", "Invalid value");
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(error));

        // Agir
        ResponseEntity<Object> responseEntity = productController.createProduct(product, bindingResult);

        // Affirmer
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "HTTP status should be BAD_REQUEST");
        assertEquals(List.of(error), responseEntity.getBody(), "Response body should contain validation errors");
    }

    @Test
    public void testGetProduct_ValidId() {
        // Arranger
        Product product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setPrice(BigDecimal.valueOf(100));
        product.setCountry(Country.USA);

        when(productService.getProductById(1L)).thenReturn(product);

        // Agir
        ResponseEntity<Object> responseEntity = productController.getProduct(1L);

        // Affirmer
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "HTTP status should be OK");
        assertEquals(product, responseEntity.getBody(), "Response body should contain the product");
    }

    @Test
    public void testGetProduct_ProductNotFound() {
        // Arranger
        when(productService.getProductById(1L)).thenThrow(new ProductNotFoundException("Product not found"));

        // Agir
        ResponseEntity<Object> responseEntity = productController.getProduct(1L);

        // Affirmer
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof ErrorResponse);

        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertEquals("Product not found", errorResponse.getMessage());
        assertEquals("Product not found", errorResponse.getDetails());
    }

    @Test
    public void testGetProduct_InvalidArgument() {
        // Arranger
        when(productService.getProductById(1L)).thenThrow(new IllegalArgumentException("Invalid argument"));

        // Agir
        ResponseEntity<Object> responseEntity = productController.getProduct(1L);

        // Affirmer
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof ErrorResponse);

        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertEquals("Invalid request", errorResponse.getMessage());
        assertEquals("Invalid argument", errorResponse.getDetails());
    }

    @Test
    public void testCalculateFinalPrice() {
        // Arranger
        PriceDTO expectedPriceDTO = new PriceDTO(BigDecimal.valueOf(100), BigDecimal.valueOf(7.00), BigDecimal.valueOf(107.00));
        when(productService.getFinalPrice(1L)).thenReturn(expectedPriceDTO);

        // Agir
        ResponseEntity<Object> responseEntity = productController.calculateFinalPrice(1L);

        // Affirmer
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "HTTP status should be OK");
        assertEquals(expectedPriceDTO, responseEntity.getBody(), "PriceDTO in response should match expected value");
    }

    @Test
    public void testCalculateFinalPrice_ProductNotFound() {
        // Arranger
        when(productService.getFinalPrice(1L)).thenThrow(new ProductNotFoundException("Product not found"));

        // Agir
        ResponseEntity<Object> responseEntity = productController.calculateFinalPrice(1L);

        // Affirmer
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof ErrorResponse); }

    @Test
    public void testCalculateFinalPrice_InvalidArgument() {
        // Arranger
        when(productService.getFinalPrice(1L)).thenThrow(new IllegalArgumentException("Invalid argument"));

        // Agir
        ResponseEntity<Object> responseEntity = productController.calculateFinalPrice(1L);

        // Affirmer
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof ErrorResponse);

        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertEquals("Invalid request", errorResponse.getMessage());
        assertEquals("Invalid argument", errorResponse.getDetails());
    }
}

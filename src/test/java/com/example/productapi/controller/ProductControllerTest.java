package com.example.productapi.controller;

import com.example.productapi.facade.ProductFacade;
import com.example.productapi.model.ProductRequest;
import com.example.productapi.model.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductFacade productFacade;

    @Mock
    private Validator validator;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        // Clear any configurations or mocks if needed
    }

    @Test
    @DisplayName("Create product successfully")
    void createProductSuccessfully() {
        ProductRequest request = new ProductRequest();
        ProductResponse response = new ProductResponse();

        when(validator.validate(request)).thenReturn(Collections.emptySet());
        when(productFacade.createProduct(any())).thenReturn(Mono.just(response));

        Mono<ResponseEntity<ProductResponse>> result = productController.createProduct(Mono.just(request), mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .expectNext(ResponseEntity.ok(response))
                .verifyComplete();
    }

    @Test
    @DisplayName("Update product with error")
    void createProductValidationError() {
        ProductRequest request = new ProductRequest();
        ConstraintViolation<ProductRequest> violation = mock(ConstraintViolation.class);

        when(validator.validate(request)).thenReturn(Set.of(violation));
        when(violation.getMessage()).thenReturn("Validation error");

        Mono<ResponseEntity<ProductResponse>> result = productController.createProduct(Mono.just(request), mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertEquals(ConstraintViolationException.class, error.getClass());
                })
                .verify();
    }

    @Test
    @DisplayName("Update product successfully")
    void updateProductSuccessfully() {
        ProductRequest request = new ProductRequest();
        ProductResponse response = new ProductResponse();

        when(validator.validate(request)).thenReturn(Collections.emptySet());
        when(productFacade.updateProduct(anyInt(), any())).thenReturn(Mono.just(response));

        Mono<ResponseEntity<ProductResponse>> result = productController.updateProduct(1, Mono.just(request), mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .expectNext(ResponseEntity.ok(response))
                .verifyComplete();
    }

    @Test
    @DisplayName("Delete product not found")
    void deleteProductNotFound() {
        when(productFacade.deleteProduct(anyInt())).thenReturn(Mono.empty());
        Mono<ResponseEntity<Void>> result = productController.deleteProduct(999, mock(ServerWebExchange.class));
        StepVerifier.create(result)
                .expectNext(ResponseEntity.notFound().build())
                .verifyComplete();
    }

    @Test
    @DisplayName("Delete product successfully")
    void getAllProductsSuccessfully() {
        ProductResponse response = new ProductResponse();
        when(productFacade.getAllProducts()).thenReturn(Flux.just(response));

        Mono<ResponseEntity<Flux<ProductResponse>>> result = productController.getAllProducts(mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .assertNext(entity -> {
                    assertEquals(200, entity.getStatusCodeValue());
                    StepVerifier.create(entity.getBody())
                            .expectNext(response)
                            .verifyComplete();
                })
                .verifyComplete();
    }
}

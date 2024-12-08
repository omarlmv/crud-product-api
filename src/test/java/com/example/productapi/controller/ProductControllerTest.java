package com.example.productapi.controller;

import com.example.productapi.facade.ProductFacade;
import com.example.productapi.model.ProductRequest;
import com.example.productapi.model.ProductResponse;
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

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductFacade productFacade;

    @InjectMocks
    private ProductController productController;

    //@Test
    void createProductSuccessfully() {
        ProductRequest request = new ProductRequest();
        ProductResponse response = new ProductResponse();
        when(productFacade.createProduct(any())).thenReturn(Mono.just(response));

        Mono<ResponseEntity<ProductResponse>> result = productController.createProduct(Mono.just(request), mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .expectNext(ResponseEntity.ok(response))
                .verifyComplete();
    }

    @Test
    void updateProductSuccessfully() {
        ProductRequest request = new ProductRequest();
        ProductResponse response = new ProductResponse();
        when(productFacade.updateProduct(anyInt(), any())).thenReturn(Mono.just(response));

        Mono<ResponseEntity<ProductResponse>> result = productController.updateProduct(1, Mono.just(request), mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .expectNext(ResponseEntity.ok(response))
                .verifyComplete();
    }

    @Test
    void updateProductWithError() {
        ProductRequest request = new ProductRequest();
        when(productFacade.updateProduct(anyInt(), any())).thenReturn(Mono.error(new RuntimeException()));

        Mono<ResponseEntity<ProductResponse>> result = productController.updateProduct(1, Mono.just(request), mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .expectNext(ResponseEntity.badRequest().build())
                .verifyComplete();
    }

    @Test
    void deleteProductNotFound() {
        when(productFacade.deleteProduct(anyInt())).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> result = productController.deleteProduct(1, mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .expectNext(ResponseEntity.notFound().build())
                .verifyComplete();
    }

    @Test
    void getAllProductsSuccessfully() {
        ProductResponse response = new ProductResponse();
        when(productFacade.getAllProducts()).thenReturn(Flux.just(response));

        Mono<ResponseEntity<Flux<ProductResponse>>> result = productController.getAllProducts(mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .assertNext(entity -> {
                    assertEquals(200, entity.getStatusCodeValue());
                    StepVerifier.create(Objects.requireNonNull(entity.getBody()))
                            .expectNext(response)
                            .verifyComplete();
                })
                .verifyComplete();
    }

    @Test
    void getProductByIdSuccessfully() {
        ProductResponse response = new ProductResponse();
        when(productFacade.getProductById(anyInt())).thenReturn(Mono.just(response));

        Mono<ResponseEntity<ProductResponse>> result = productController.getProductById(1, mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .expectNext(ResponseEntity.ok(response))
                .verifyComplete();
    }

    @Test
    void getProductByIdNotFound() {
        when(productFacade.getProductById(anyInt())).thenReturn(Mono.empty());

        Mono<ResponseEntity<ProductResponse>> result = productController.getProductById(1, mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .expectNext(ResponseEntity.noContent().build())
                .verifyComplete();
    }

    @Test
    void searchProductsByNameSuccessfully() {
        ProductResponse response = new ProductResponse();
        when(productFacade.searchProductsByName(anyString())).thenReturn(Flux.just(response));

        Mono<ResponseEntity<Flux<ProductResponse>>> result = productController.searchProductsByName("test", mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .assertNext(entity -> {
                    assertEquals(200, entity.getStatusCodeValue());
                    StepVerifier.create(entity.getBody())
                            .expectNext(response)
                            .verifyComplete();
                })
                .verifyComplete();
    }

    /*
    @Test
    public void testCreateProductValidation() {
        ProductRequest invalidRequest = new ProductRequest(null, -1, BigDecimal.valueOf(-10), null);

        webTestClient.post()
                .uri("/api/products")
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .consumeWith(response -> {
                    System.out.println("Validation Errors: " + response.getResponseBody());
                });
    }*/


}
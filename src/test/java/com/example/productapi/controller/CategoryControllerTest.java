package com.example.productapi.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.productapi.model.CategoryRequest;
import com.example.productapi.model.CategoryResponse;
import com.example.productapi.service.CategoryService;
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

import javax.validation.Validator;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private Validator validator;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryService = mock(CategoryService.class);
        validator = mock(Validator.class);
        categoryController = new CategoryController(categoryService, validator);
    }

    @Test
    @DisplayName("Create category successfully")
    void createCategorySuccessfully() {
        CategoryRequest request = new CategoryRequest();
        CategoryResponse response = new CategoryResponse();
        when(categoryService.createCategory(any())).thenReturn(Mono.just(response));

        Mono<ResponseEntity<CategoryResponse>> result = categoryController.createCategory(Mono.just(request), mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .expectNext(ResponseEntity.ok(response))
                .verifyComplete();
    }

    @Test
    @DisplayName("Handle error during category creation")
    void createCategoryWithError() {
        CategoryRequest request = new CategoryRequest();
        when(categoryService.createCategory(any())).thenReturn(Mono.error(new RuntimeException()));

        Mono<ResponseEntity<CategoryResponse>> result = categoryController.createCategory(Mono.just(request), mock(ServerWebExchange.class));

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Retrieve all categories successfully")
    void getAllCategoriesSuccessfully() {
        CategoryResponse response = new CategoryResponse();
        when(categoryService.getAllCategories()).thenReturn(Flux.just(response));

        Mono<ResponseEntity<Flux<CategoryResponse>>> result = categoryController.getAllCategories(mock(ServerWebExchange.class));

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


package com.example.productapi.controller;

import com.example.productapi.api.CategoryApi;
import com.example.productapi.model.CategoryRequest;
import com.example.productapi.model.CategoryResponse;
import com.example.productapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@RestController
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;
    private final Validator validator;


    @Autowired
    public CategoryController(CategoryService categoryService, Validator validator) {
        this.categoryService = categoryService;
        this.validator = validator;
    }

    @Override
    public Mono<ResponseEntity<CategoryResponse>> createCategory(@RequestBody Mono<CategoryRequest> categoryRequest,
                                                                 ServerWebExchange exchange) {
        return categoryRequest
                .flatMap(this::validateRequest)
                .flatMap(categoryService::createCategory)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<CategoryResponse>>> getAllCategories(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(categoryService.getAllCategories()));
    }

    private <T> Mono<T> validateRequest(T request) {
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return Mono.just(request);
    }
}

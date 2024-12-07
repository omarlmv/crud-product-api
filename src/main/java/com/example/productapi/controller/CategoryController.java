
package com.example.productapi.controller;

import com.example.productapi.api.CategoryApi;
import com.example.productapi.model.CategoryRequest;
import com.example.productapi.model.CategoryResponse;
import com.example.productapi.service.CategoryService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.openapitools.api.ApiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Mono<ResponseEntity<CategoryResponse>> createCategory(@RequestBody Mono<CategoryRequest> categoryRequest,
                                                                 ServerWebExchange exchange) {
        return categoryRequest
                .flatMap(categoryService::createCategory)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<CategoryResponse>>> getAllCategories(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(categoryService.getAllCategories()));
    }
}

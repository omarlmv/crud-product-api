
package com.example.productapi.service;

import com.example.productapi.model.CategoryRequest;
import com.example.productapi.model.CategoryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    Mono<CategoryResponse> createCategory(CategoryRequest categoryRequest);
    Flux<CategoryResponse> getAllCategories();
}

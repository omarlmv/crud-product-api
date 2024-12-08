
package com.example.productapi.service.impl;

import com.example.productapi.entity.Category;
import com.example.productapi.mapper.CategoryMapper;
import com.example.productapi.model.CategoryRequest;
import com.example.productapi.model.CategoryResponse;
import com.example.productapi.repository.CategoryRepository;
import com.example.productapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Mono<CategoryResponse> createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toEntity(categoryRequest);
        return categoryRepository.save(category)
                .map(categoryMapper::toResponse);
    }

    @Override
    public Flux<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .map(categoryMapper::toResponse);
    }
}

package com.example.productapi.service.impl;

import com.example.productapi.entity.Product;
import com.example.productapi.mapper.ProductMapper;
import com.example.productapi.model.ProductRequest;
import com.example.productapi.model.ProductResponse;
import com.example.productapi.repository.CategoryRepository;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.service.ProductService;
import com.example.productapi.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StatisticsService statisticsService;

    @Override
    public Mono<ProductResponse> createProduct(ProductRequest productRequest) {
        return categoryRepository.findById(productRequest.getCategoryId())
                .switchIfEmpty(Mono.error(new RuntimeException("Category not found")))
                .flatMap(category -> {
                    Product product = productMapper.toEntity(productRequest);
                    return productRepository.save(product)
                            .doOnSuccess(savedProduct -> statisticsService.calculateDynamicStatistics(product.getId()).subscribe());
                })
                .map(productMapper::toResponse);
    }

    @Override
    public Mono<ProductResponse> updateProduct(Integer id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .flatMap(existingProduct -> categoryRepository.findById(productRequest.getCategoryId())
                        .switchIfEmpty(Mono.error(new RuntimeException("Category not found")))
                        .flatMap(category -> {
                            Product updatedProduct = productMapper.toEntity(productRequest);
                            updatedProduct.setId(existingProduct.getId());
                            return productRepository.save(updatedProduct)
                                    .doOnSuccess(savedProduct -> statisticsService.calculateDynamicStatistics(existingProduct.getId()).subscribe());
                        }))
                .map(productMapper::toResponse);
    }

    @Override
    public Mono<Void> deleteProduct(Integer id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .flatMap(product -> productRepository.delete(product)
                        .then(statisticsService.calculateDynamicStatistics(product.getId()).then()));
    }

    @Override
    public Flux<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .map(productMapper::toResponse);
    }

    @Override
    public Mono<ProductResponse> getProductById(Integer id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .map(productMapper::toResponse);
    }

    @Override
    public Flux<ProductResponse> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name)
                .map(productMapper::toResponse);
    }
}


package com.example.productapi.service.impl;

import com.example.productapi.dto.ErrorResponse;
import com.example.productapi.entity.Product;
import com.example.productapi.enums.ProductStatus;
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
    private ProductMapper productMapper;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Mono<ProductResponse> createProduct(ProductRequest productRequest) {

        return categoryRepository.findById(productRequest.getCategoryId())
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        ErrorResponse.builder()
                                .status(404)
                                .error("Category Not Found")
                                .message("Category with ID " + productRequest.getCategoryId() + " not found")
                                .build())))
                .flatMap(category -> {
                    Product product = productMapper.toEntity(productRequest);
                    product.setStatus(ProductStatus.ACTIVE.getValue());
                    return productRepository.save(product)
                            .flatMap(savedProduct -> {
                                return statisticsService.createStatistics(
                                                savedProduct.getCreatedAt(),
                                                1,
                                                0,
                                                0,
                                                savedProduct.getCategoryId().toString())
                                        .thenReturn(productMapper.toResponse(savedProduct));
                            });
                });
    }

    @Override
    public Mono<ProductResponse> updateProduct(Integer id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        ErrorResponse.builder()
                                .status(404)
                                .error("Product Not Found")
                                .message("Product with ID " + id + " not found")
                                .build())))
                .flatMap(existingProduct -> {
                    productMapper.updateProductFromDto(productRequest, existingProduct);
                    return productRepository.save(existingProduct)
                            .flatMap(updatedProduct -> {
                                return statisticsService.createStatistics(
                                        updatedProduct.getCreatedAt(),
                                        0,
                                        1,
                                        0,
                                        updatedProduct.getCategoryId().toString())
                                        .thenReturn(productMapper.toResponse(updatedProduct));
                            });
                });
    }

    @Override
    public Mono<Void> deleteProduct(Integer id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        ErrorResponse.builder()
                                .status(404)
                                .error("Product Not Found")
                                .message("Product with ID " + id + " not found")
                                .build())))
                .flatMap(existingProduct -> {
                    existingProduct.setStatus(ProductStatus.DELETED.getValue());
                    return productRepository.save(existingProduct)
                            .flatMap(deletedProduct -> {
                                return statisticsService.createStatistics(
                                        deletedProduct.getCreatedAt(),
                                        0,
                                        0,
                                        1,
                                        deletedProduct.getCategoryId().toString())
                                        .then();
                            });
                })
                .then();
    }

    @Override
    public Flux<ProductResponse> getAllProducts() {
        return productRepository.findAllByStatusNot(ProductStatus.DELETED.getValue())
                .map(productMapper::toResponse);
    }

    @Override
    public Mono<ProductResponse> getProductById(Integer id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        ErrorResponse.builder()
                                .status(404)
                                .error("Product Not Found")
                                .message("Product with ID " + id + " not found")
                                .build())))
                .map(productMapper::toResponse);
    }

    @Override
    public Flux<ProductResponse> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .switchIfEmpty(Mono.error(
                        ErrorResponse.builder()
                                .status(404)
                                .error("Product Not Found")
                                .message("No products found with name: " + name)
                                .build()))
                .map(productMapper::toResponse);
    }
}

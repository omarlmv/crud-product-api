
package com.example.productapi.service;

import com.example.productapi.model.ProductRequest;
import com.example.productapi.model.ProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<ProductResponse> createProduct(ProductRequest productRequest);
    Mono<ProductResponse> updateProduct(Integer id, ProductRequest productRequest);
    Mono<Void> deleteProduct(Integer id);
    Flux<ProductResponse> getAllProducts();
    Mono<ProductResponse> getProductById(Integer id);

    Flux<ProductResponse> searchProductsByName(String name);
}

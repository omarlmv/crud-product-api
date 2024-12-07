
package com.example.productapi.facade;

import com.example.productapi.model.ProductRequest;
import com.example.productapi.model.ProductResponse;
import com.example.productapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductFacade {

    private final ProductService productService;

    @Autowired
    public ProductFacade(ProductService productService) {
        this.productService = productService;
    }

    public Mono<ProductResponse> createProduct(ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    public Mono<ProductResponse> updateProduct(Integer id, ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    public Mono<Void> deleteProduct(Integer id) {
        return productService.deleteProduct(id);
    }

    public Flux<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    public Mono<ProductResponse> getProductById(Integer id) {
        return productService.getProductById(id);
    }

    public Flux<ProductResponse> searchProductsByName(String name) {
        return productService.searchProductsByName(name);
    }
}


package com.example.productapi.repository;

import com.example.productapi.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findByNameContaining(String name);
    Flux<Product> findAllByStatusNot(String status);
    Mono<Product> findByIdAndStatus(Integer id, String status);
    Flux<Product> findByNameContainingIgnoreCase(String name);
}

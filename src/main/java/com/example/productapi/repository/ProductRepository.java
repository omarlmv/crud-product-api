
package com.example.productapi.repository;

import com.example.productapi.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findByNameContaining(String name);
    @Query("SELECT * FROM products WHERE category_id = :categoryId")
    Flux<Product> findByCategoryId(@Param("categoryId") Integer categoryId);

}

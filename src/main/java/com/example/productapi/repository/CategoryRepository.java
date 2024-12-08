
package com.example.productapi.repository;

import com.example.productapi.entity.Category;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<Category, Integer> {

    Mono<Category> findById(Integer id);
}

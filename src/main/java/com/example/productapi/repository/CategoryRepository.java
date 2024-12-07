
package com.example.productapi.repository;

import com.example.productapi.entity.Category;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<Category, Integer> {
}

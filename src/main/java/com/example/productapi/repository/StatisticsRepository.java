
package com.example.productapi.repository;

import com.example.productapi.entity.Statistics;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface StatisticsRepository extends ReactiveCrudRepository<Statistics, Long> {
    Mono<Statistics> findByCategory(String category);
}

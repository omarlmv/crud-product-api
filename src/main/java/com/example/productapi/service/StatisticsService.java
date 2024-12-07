
package com.example.productapi.service;

import com.example.productapi.model.StatisticsResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface StatisticsService {
    Mono<Void> createStatistics(LocalDateTime date, int totalProducts, int totalUpdates, int totalDeletions, String category);
    Flux<StatisticsResponse> getAllStatistics();
}

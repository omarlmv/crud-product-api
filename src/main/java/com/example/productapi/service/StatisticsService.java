
package com.example.productapi.service;

import com.example.productapi.model.StatisticsResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface StatisticsService {
    Flux<StatisticsResponse> getAllStatistics();

    Mono<StatisticsResponse> calculateDynamicStatistics(Integer productId);
}


package com.example.productapi.controller;

import com.example.productapi.api.StatisticsApi;
import com.example.productapi.model.StatisticsResponse;
import com.example.productapi.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class StatisticsController implements StatisticsApi {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    public Mono<ResponseEntity<Flux<StatisticsResponse>>> getAllStatistics(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(statisticsService.getAllStatistics()));
    }
}

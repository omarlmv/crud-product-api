package com.example.productapi.controller;

import com.example.productapi.model.StatisticsResponse;
import com.example.productapi.service.CategoryService;
import com.example.productapi.service.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsControllerTest {

    @Mock
    private StatisticsService statisticsService;

    @InjectMocks
    private StatisticsController statisticsController;

    @BeforeEach
    void setUp() {
        statisticsService = mock(StatisticsService.class);
        statisticsController = new StatisticsController(statisticsService);
    }

    @Test
    @DisplayName("Get all statistics successfully")
    void getAllStatisticsSuccessfully() {
        StatisticsResponse response = new StatisticsResponse();
        when(statisticsService.getAllStatistics()).thenReturn(Flux.just(response));
        Mono<ResponseEntity<Flux<StatisticsResponse>>> result = statisticsController.getAllStatistics(mock(ServerWebExchange.class));
        StepVerifier.create(result)
                .assertNext(entity -> {
                    assertEquals(200, entity.getStatusCodeValue());
                    StepVerifier.create(entity.getBody())
                            .expectNext(response)
                            .verifyComplete();
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Get all statistics when no data is available")
    void getAllStatisticsEmpty() {
        when(statisticsService.getAllStatistics()).thenReturn(Flux.empty());
        Mono<ResponseEntity<Flux<StatisticsResponse>>> result = statisticsController.getAllStatistics(mock(ServerWebExchange.class));
        StepVerifier.create(result)
                .assertNext(entity -> {
                    assertEquals(200, entity.getStatusCodeValue());
                    StepVerifier.create(entity.getBody())
                            .verifyComplete();
                })
                .verifyComplete();
    }
}


package com.example.productapi.service.impl;

import com.example.productapi.entity.Statistics;
import com.example.productapi.mapper.StatisticsMapper;
import com.example.productapi.model.StatisticsResponse;
import com.example.productapi.repository.StatisticsRepository;
import com.example.productapi.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final StatisticsMapper statisticsMapper;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository, StatisticsMapper statisticsMapper) {
        this.statisticsRepository = statisticsRepository;
        this.statisticsMapper = statisticsMapper;
    }

    @Override
    public Mono<Void> createStatistics(LocalDateTime date, int totalProducts, int totalUpdates, int totalDeletions, String category) {
        Statistics statistics = new Statistics();
        statistics.setDate(date);
        statistics.setTotalProducts(totalProducts);
        statistics.setTotalUpdates(totalUpdates);
        statistics.setTotalDeletions(totalDeletions);
        statistics.setCategory(category);
        return statisticsRepository.save(statistics)
                .then();
    }

    @Override
    public Flux<StatisticsResponse> getAllStatistics() {
        return statisticsRepository.findAll()
                .map(statisticsMapper::toResponse);
    }
}

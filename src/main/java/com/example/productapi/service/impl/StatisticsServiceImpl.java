package com.example.productapi.service.impl;

import com.example.productapi.entity.Category;
import com.example.productapi.entity.Product;
import com.example.productapi.entity.Statistics;
import com.example.productapi.mapper.StatisticsMapper;
import com.example.productapi.model.StatisticsResponse;
import com.example.productapi.repository.StatisticsRepository;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.repository.CategoryRepository;
import com.example.productapi.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public Flux<StatisticsResponse> getAllStatistics() {
        return statisticsRepository.findAll()
                .map(statisticsMapper::toResponse);
    }

    @Override
    public Mono<StatisticsResponse> calculateDynamicStatistics(Integer productId) {
        return productRepository.findById(productId).flatMap(product -> {
            Integer categoryId = product.getCategoryId();
            return categoryRepository.findById(categoryId).flatMap(category -> {
                return productRepository.findByCategoryId(category.getId()).collectList()
                        .flatMap(products -> {
                            Statistics calculatedStatistics = calculateStatistics(category, products);

                            return statisticsRepository.findByCategory(category.getName())
                                    .flatMap(existingStatistics -> {
                                        updateExistingStatistics(existingStatistics, calculatedStatistics);
                                        return statisticsRepository.save(existingStatistics)
                                                .map(statisticsMapper::toResponse);
                                    })
                                    .switchIfEmpty(Mono.defer(() -> {
                                        return statisticsRepository.save(calculatedStatistics)
                                                .map(statisticsMapper::toResponse);
                                    }));
                        });
            });
        });
    }

    private Statistics calculateStatistics(Category category, List<Product> products) {
        int totalProducts = products.size();
        int activeProducts = (int) products.stream().filter(p -> "ACTIVE".equals(p.getStatus())).count();
        int inactiveProducts = totalProducts - activeProducts;

        String mostPopularProduct = products.stream()
                .collect(Collectors.groupingBy(Product::getName, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);

        double averagePrice = products.stream()
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0);

        Statistics statistics = new Statistics();
        statistics.setCategory(category.getName());
        statistics.setTotalProducts(totalProducts);
        statistics.setActiveProducts(activeProducts);
        statistics.setInactiveProducts(inactiveProducts);
        statistics.setMostPopularProduct(mostPopularProduct);
        statistics.setAveragePrice((float) averagePrice);
        statistics.setLastUpdated(LocalDateTime.now());

        return statistics;
    }

    private void updateExistingStatistics(Statistics existingStatistics, Statistics newStatistics) {
        existingStatistics.setTotalProducts(newStatistics.getTotalProducts());
        existingStatistics.setActiveProducts(newStatistics.getActiveProducts());
        existingStatistics.setInactiveProducts(newStatistics.getInactiveProducts());
        existingStatistics.setMostPopularProduct(newStatistics.getMostPopularProduct());
        existingStatistics.setAveragePrice(newStatistics.getAveragePrice());
        existingStatistics.setLastUpdated(newStatistics.getLastUpdated());
    }
}
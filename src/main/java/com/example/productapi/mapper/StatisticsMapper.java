package com.example.productapi.mapper;

import com.example.productapi.entity.Statistics;
import com.example.productapi.model.StatisticsResponse;
import com.example.productapi.model.StatisticsResponseFinancials;
import com.example.productapi.model.StatisticsResponseHighlights;
import com.example.productapi.model.StatisticsResponseOverview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface StatisticsMapper {

    @Mapping(source = "lastUpdated", target = "lastUpdated", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(target = "overview", expression = "java(createOverview(statistics))")
    @Mapping(target = "financials", expression = "java(createFinancials(statistics))")
    @Mapping(target = "highlights", expression = "java(createHighlights(statistics))")
    StatisticsResponse toResponse(Statistics statistics);

    @Named("localDateTimeToOffsetDateTime")
    default OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atOffset(ZoneOffset.UTC);
    }

    default StatisticsResponseOverview createOverview(Statistics statistics) {
        return new StatisticsResponseOverview(
                statistics.getTotalProducts(),
                statistics.getActiveProducts(),
                statistics.getInactiveProducts()
        );
    }

    default StatisticsResponseFinancials createFinancials(Statistics statistics) {
        return new StatisticsResponseFinancials(
                statistics.getAveragePrice(),
                statistics.getActiveProducts() * statistics.getAveragePrice()
        );
    }

    default StatisticsResponseHighlights createHighlights(Statistics statistics) {
        return new StatisticsResponseHighlights(
                statistics.getMostPopularProduct()
        );
    }
}

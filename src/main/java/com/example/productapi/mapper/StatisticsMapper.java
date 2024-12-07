
package com.example.productapi.mapper;

import com.example.productapi.entity.Statistics;
import com.example.productapi.model.StatisticsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface StatisticsMapper {
    @Mapping(source = "date", target = "date", qualifiedByName = "localDateTimeToOffsetDateTime")
    StatisticsResponse toResponse(Statistics statistics);

    @Named("localDateTimeToOffsetDateTime")
    static OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime date) {
        return date == null ? null : date.atOffset(ZoneOffset.UTC);
    }
}

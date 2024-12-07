
package com.example.productapi.mapper;

import com.example.productapi.entity.Category;
import com.example.productapi.model.CategoryRequest;
import com.example.productapi.model.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequest categoryRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryResponse toResponse(Category category);
}

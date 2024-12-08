
package com.example.productapi.mapper;

import com.example.productapi.entity.Product;
import com.example.productapi.model.ProductRequest;
import com.example.productapi.model.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "categoryId", expression = "java(productRequest.getCategoryId())")
    Product toEntity(ProductRequest productRequest);

    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateProductFromDto(ProductRequest productRequest, @MappingTarget Product product);
}

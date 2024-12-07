
package com.example.productapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("products")
public class Product {
    @Id
    private Integer id;
    private String name;
    private Long categoryId;
    private Double price;
    private String status;
    private LocalDateTime createdAt;

    // Getters and Setters
}

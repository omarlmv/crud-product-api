
package com.example.productapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("products")
public class Product {
    @Id
    private Integer id;
    private String name;
    @Column("category_id")
    private Integer categoryId;
    private Double price;
    private String status;
    @Column("created_at")
    private LocalDateTime createdAt;
}

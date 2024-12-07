
package com.example.productapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("categories")
public class Category {
    @Id
    private Integer id;
    private String name;

    // Getters and Setters
}

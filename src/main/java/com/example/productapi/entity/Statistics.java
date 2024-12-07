
package com.example.productapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("statistics")
public class Statistics {
    @Id
    private Integer id;
    private LocalDateTime date;
    private int totalProducts;
    private int totalUpdates;
    private int totalDeletions;
    private String category;

    // Getters and Setters
}

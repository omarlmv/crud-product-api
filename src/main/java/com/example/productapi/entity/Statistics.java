package com.example.productapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@Table("statistics")
public class Statistics {

    @Id
    private Integer id;
    @Column("category")
    private String category;
    @Column("total_products")
    private Integer totalProducts;
    @Column("active_products")
    private Integer activeProducts;
    @Column("inactive_products")
    private Integer inactiveProducts;
    @Column("average_price")
    private Float averagePrice;
    @Column("most_popular_product")
    private String mostPopularProduct;
    @Column("last_updated")
    private LocalDateTime lastUpdated;
}

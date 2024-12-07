
package com.example.productapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsResponseTest {
    private Long id;
    private String date;
    private int totalProducts;
    private int totalUpdates;
    private int totalDeletions;
    private String category;
}

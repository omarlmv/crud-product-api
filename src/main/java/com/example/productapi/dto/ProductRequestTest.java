
package com.example.productapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestTest {
    private String name;
    private Long categoryId;
    private Double price;
    private String status;
}


package com.example.productapi.enums;

import lombok.Getter;

@Getter
public enum ProductStatus {
    ACTIVE("ACTIVE"),
    DELETED("DELETED");

    private final String value;

    ProductStatus(String value) {
        this.value = value;
    }

}

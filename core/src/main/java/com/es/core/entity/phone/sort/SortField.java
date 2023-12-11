package com.es.core.entity.car.sort;

import java.util.Arrays;

public enum SortField {
    BRAND,
    MODEL,
    DISPLAYSIZEINCHES,
    PRICE;
    public static SortField getValue(String name) {
        if (name == null){
            return null;
        }
        return Arrays.stream(SortField.values())
                .filter(value -> value.name().equals(name.toUpperCase()))
                .findAny()
                .orElse(null);
    }
}

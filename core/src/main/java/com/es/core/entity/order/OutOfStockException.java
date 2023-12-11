package com.es.core.entity.order;

public class OutOfStockException extends RuntimeException {
    private Long productIndex;

    public OutOfStockException() {
    }

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, Long productIndex) {
        super(message);
        this.productIndex = productIndex;
    }

    public Long getProductIndex() {
        return productIndex;
    }
}

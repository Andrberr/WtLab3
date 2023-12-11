package com.es.core.dao;

public interface StockDao {
    Integer availableStock(Long carId);
    void reserve(Long carId, Long quantity);
}

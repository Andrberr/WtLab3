package com.es.core.service;

import com.es.core.entity.cart.Cart;
import com.es.core.entity.car.car;
import com.es.core.entity.order.OutOfStockException;

import java.math.BigDecimal;

public interface CartService {

    Cart getCart();

    void addcar(Long carId, Long quantity) throws OutOfStockException;

    /**
     * @param items
     * key: {@link car#id}
     * value: quantity
     */
    void update(Long carId, Long carQuantity);
    void clear();
    void remove(Long carId);
    long getTotalQuantity();

    BigDecimal getTotalCost();
}

package com.es.core.service;

import com.es.core.entity.cart.Cart;
import com.es.core.entity.order.Order;
import com.es.core.entity.order.OrderStatus;
import com.es.core.entity.order.OutOfStockException;

public interface OrderService {
    Order createOrder(Cart cart);
    void placeOrder(Order order) throws OutOfStockException;

    void changeOrderStatus(Long id, OrderStatus status);
}

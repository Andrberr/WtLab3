package com.es.core.dao;

import com.es.core.entity.order.OrderItem;

import java.util.List;

public interface OrderItemDao {
    List<OrderItem> getOrderItems (Long key);
}

package com.es.core.service.impl;

import com.es.core.entity.cart.Cart;
import com.es.core.service.CartService;
import com.es.core.entity.order.Order;
import com.es.core.entity.order.OutOfStockException;
import com.es.core.dao.OrderDao;
import com.es.core.entity.order.OrderItem;
import com.es.core.entity.order.OrderStatus;
import com.es.core.dao.StockDao;
import com.es.core.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private StockDao stockDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private CartService cartService;
    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setDeliveryPrice(deliveryPrice);
        order.setSubtotal(cart.getTotalCost());
        order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
        fillOrderItems(order, cart);
        return order;
    }

    @Override
    @Transactional
    public void placeOrder(final Order order) throws OutOfStockException {
        checkStock(order);
        order.setDate(new Date(Instant.now().toEpochMilli()));
        order.setTime(new Time(Instant.now().toEpochMilli()));
        order.setStatus(OrderStatus.NEW);
        order.getOrderItems().stream()
                .forEach(item -> stockDao.reserve(item.getcar().getId(), (long) item.getQuantity()));
        order.setSecureID(UUID.randomUUID().toString());
        orderDao.save(order);
        cartService.clear();
    }

    @Override
    public void changeOrderStatus(Long id, OrderStatus status) {
        orderDao.changeStatus(id, status);
    }
    private void fillOrderItems(Order order, Cart cart) {
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setcar(cartItem.getcar());
                    orderItem.setQuantity(Math.toIntExact(cartItem.getQuantity()));
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
    }

    private void checkStock(final Order order) throws OutOfStockException {
        List<OrderItem> outOfStockItems = order.getOrderItems().stream()
                .filter(item -> stockDao.availableStock(item.getcar().getId()) - item.getQuantity() < 0)
                .collect(Collectors.toList());
        if (!outOfStockItems.isEmpty()) {
            StringBuilder outOfStockModels = new StringBuilder();
            outOfStockItems.stream().forEach(item -> {
                outOfStockModels.append(item.getcar().getModel() + "; ");
                cartService.remove(item.getcar().getId());
            });
            throw new OutOfStockException("Some of items out of stock (" + outOfStockModels + "). They deleted from cart.");
        }
    }
}

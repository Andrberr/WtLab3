package com.es.core.dao.impl;

import com.es.core.dao.OrderItemDao;
import com.es.core.entity.order.OrderItem;
import com.es.core.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemDaoImpl implements OrderItemDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public OrderItemDaoImpl() {
         sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    @Override
    public List<OrderItem> getOrderItems(Long key) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM OrderItem WHERE order.id = :orderId", OrderItem.class)
                    .setParameter("orderId", key)
                    .list();
        }
    }
}
package com.es.core.dao.impl;

import com.es.core.dao.OrderItemDao;
import com.es.core.entity.order.Order;
import com.es.core.entity.order.OrderStatus;
import com.es.core.dao.OrderDao;
import com.es.core.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDaoImpl implements OrderDao {
    private final SessionFactory sessionFactory;

    @Resource
    private OrderItemDao orderItemDao;
    @Autowired
    public OrderDaoImpl() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    @Override
    public Optional<Order> getById(Long key) {
        try (Session session = sessionFactory.openSession()) {
            Optional<Order> order = Optional.ofNullable(session.find(Order.class, key));
            order.ifPresent(value -> value.setOrderItems(orderItemDao.getOrderItems(value.getId())));
            return order;
        }
    }

    @Override
    public Optional<Order> getBySecureID(String secureID) {

        try (Session session = sessionFactory.openSession()) {
            Optional<Order> order = session.createQuery("FROM Order WHERE secureID = :secureID", Order.class)
                .setParameter("secureID", secureID)
                .getResultList()
                .stream()
                .findAny();
            order.ifPresent(value -> value.setOrderItems(orderItemDao.getOrderItems(value.getId())));
            return order;
        }
    }

    @Override
    @Transactional
    public void save(Order order) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(order);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Order> findOrders() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Order", Order.class).list();
        }
    }

    @Override
    @Transactional
    public void changeStatus(Long id, OrderStatus status) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Order order = session.find(Order.class, id);
            order.setStatus(status);
            session.getTransaction().commit();
        }
    }
}
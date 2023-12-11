package com.es.core.dao.impl;

import com.es.core.dao.CarDao;
import com.es.core.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Component
public class CarDaoImpl implements CarDao {
    private final SessionFactory sessionFactory;

    public CarDaoImpl() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    @Override
    public Optional<com.es.core.entity.car.car> get(Long key) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(com.es.core.entity.car.car.class, key));
        }
    }

    @Override
    public List<com.es.core.entity.car.car> findAll(int offset, int limit, com.es.core.entity.car.sort.SortField sortField, com.es.core.entity.car.sort.SortOrder sortOrder, String query) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<com.es.core.entity.car.car> criteriaQuery = criteriaBuilder.createQuery(com.es.core.entity.car.car.class);
            Root<com.es.core.entity.car.car> root = criteriaQuery.from(com.es.core.entity.car.car.class);

            criteriaQuery.select(root);
            if (query != null && !query.isEmpty()) {
                criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), "%" + query.toLowerCase() + "%"));
            }
            if (sortField != null && sortOrder != null) {
                Order order = sortOrder == com.es.core.entity.car.sort.SortOrder.ASC ? criteriaBuilder.asc(root.get(sortField.name().toLowerCase()))
                        : criteriaBuilder.desc(root.get(sortField.name().toLowerCase()));
                criteriaQuery.orderBy(order);
            }

            Query<com.es.core.entity.car.car> queryResult = session.createQuery(criteriaQuery);
            queryResult.setFirstResult(offset);
            queryResult.setMaxResults(limit);

            return queryResult.getResultList();
        }
    }

    @Override
    public Long numberByQuery(String query) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
            Root<com.es.core.entity.car.car> root = countQuery.from(com.es.core.entity.car.car.class);

            countQuery.select(criteriaBuilder.count(root));
            if (query != null && !query.isEmpty()) {
                countQuery.where(criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), "%" + query.toLowerCase() + "%"));
            }

            return session.createQuery(countQuery).getSingleResult();
        }
    }
}

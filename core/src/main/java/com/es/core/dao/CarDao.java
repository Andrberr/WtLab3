package com.es.core.dao;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    Optional<com.es.core.entity.car.car> get(Long key);

    List<com.es.core.entity.car.car> findAll(int offset, int limit, com.es.core.entity.car.sort.SortField sortField, com.es.core.entity.car.sort.SortOrder sortOrder, String query);

    Long numberByQuery(String query);
}

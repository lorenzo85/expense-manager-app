package com.spring.cms.service;

import java.util.List;

public interface BaseService<T, ID> {

    T update(T entity);

    T save(T entity);

    T findOne(ID id);

    List<T> findAll();

    long count();

    void delete(ID id);
}

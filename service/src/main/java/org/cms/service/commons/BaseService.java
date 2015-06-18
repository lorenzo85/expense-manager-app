package org.cms.service.commons;

import java.util.List;

public interface BaseService<T, I> {

    T update(T entity);

    T save(T entity);

    T findOne(I id);

    List<T> findAll();

    long count();

    void delete(I id);
}

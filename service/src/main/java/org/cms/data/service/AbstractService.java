package org.cms.data.service;

import org.cms.data.utilities.EntityNotFoundException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;


@Transactional(readOnly = true)
public abstract class AbstractService<M, T, I extends Serializable> implements BaseService<M, I> {

    @Autowired
    protected Mapper mapper;

    @Override
    public M findOne(I id) {
        T entity = findOneOrThrow(id);
        return mapper.map(entity, getDtoClass());
    }

    @Override
    public List<M> findAll() {
        Iterable<T> entities = getRepository().findAll();
        return mapAll(entities);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(I id) {
        getRepository().delete(id);
    }

    @Override
    public long count() {
        return getRepository().count();
    }

    protected abstract CrudRepository<T, I> getRepository();

    protected T findOneOrThrow(I id) {
        T entity = getRepository().findOne(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }
        return entity;
    }

    protected void throwIfFound(I id) {
        T entity = getRepository().findOne(id);
        if(entity != null) {
            throw new IllegalArgumentException(format("An entity with id %s was already found!", id));
        }
    }

    protected List<M> mapAll(Iterable<T> entities) {
        List<M> mapped = new ArrayList<>();
        entities.forEach(e -> {
            M mappedEntity = mapper.map(e, getDtoClass());
            mapped.add(mappedEntity);
        });
        return mapped;
    }

    @SuppressWarnings("unchecked")
    private Class<M> getDtoClass() {
        return (Class) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
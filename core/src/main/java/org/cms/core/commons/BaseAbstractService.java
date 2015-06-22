package org.cms.core.commons;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;


public abstract class BaseAbstractService<M extends Dto<I>, T, I extends Serializable> implements BaseService<M, I> {

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
    public void delete(I id) {
        getRepository().delete(id);
    }

    @Override
    public M save(@IsValid M entity) {
        throwIfFound(entity.getId());

        T domain = mapper.map(entity, getDomainClass());
        domain = getRepository().save(domain);

        return mapper.map(domain, getDtoClass());
    }

    @Override
    public M update(@IsValid M entity) {
        findOneOrThrow(entity.getId());

        T domain = mapper.map(entity, getDomainClass());
        domain = getRepository().save(domain);

        return mapper.map(domain, getDtoClass());
    }

    @Override
    public long count() {
        return getRepository().count();
    }

    protected abstract JpaRepository<T, I> getRepository();

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
    private Class<T> getDomainClass() {
        return getClazzForParam(1);
    }
    @SuppressWarnings("unchecked")
    private Class<M> getDtoClass() {
        return getClazzForParam(0);
    }

    @SuppressWarnings("unchecked")
    private <F> Class<F> getClazzForParam(int index) {
        return (Class) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[index];
    }
}
package com.nlmk.potapov.tm.repository;

import com.nlmk.potapov.tm.entity.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRepository<T> {

    private static final Logger logger = LogManager.getLogger(AbstractRepository.class);

    protected final List<T> entityList = new ArrayList<>();

    protected final Map<String,List<T>> entityMap = new HashMap<>();

    public int size() {
        return entityList.size();
    }

    public List<T> findAll() {
        return entityList;
    }

    public T create(final T entity) {
        entityList.add(entity);
        addToEntityMap(entity);
        return entity;
    }

    private String getEntityName(final T entity) {
        try {
            Method method = entity.getClass().getMethod("getName");
            return method.invoke(entity, null).toString();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Ошибка при вызове метода \"getName()\" у класса " + entity.getClass()+ ":"+e.getMessage());
            return null;
        }
    }

    public void addToEntityMap(final T entity) {
        final String name = getEntityName(entity);
        List<T> valueList = entityMap.get(name);
        if (valueList == null || valueList.isEmpty()) {
            valueList = new ArrayList<>();
            valueList.add(entity);
            entityMap.put(name,valueList);
            return;
        }
        valueList.add(entity);
    }

    public void removeFromEntityMap(final T entity) {
        final String name = getEntityName(entity);
        List<T> valueList = entityMap.get(name);
        if (valueList == null || valueList.isEmpty()) return;
        if (valueList.size() > 1) valueList.remove(entity);
        else entityMap.remove(name);
    }

}

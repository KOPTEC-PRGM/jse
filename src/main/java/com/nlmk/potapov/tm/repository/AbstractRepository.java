package com.nlmk.potapov.tm.repository;

import com.nlmk.potapov.tm.entity.Project;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRepository<T> {

    protected final List<T> entityList = new ArrayList<>();

    protected final Map<String,List<T>> entityMap = new HashMap<>();

    public List<T> getEntityList() {
        return entityList;
    }

    public Map<String, List<T>> getEntityMap() {
        return entityMap;
    }

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

    public void addToEntityMap(final T entity) {
        String name = null;
        try {
            Method method = entity.getClass().getMethod("getName");
            name = method.invoke(entity, null).toString();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        List<T> valueList = entityMap.get(name);
        if (valueList == null || valueList.isEmpty()) {
            valueList = new ArrayList<>();
            valueList.add(entity);
            entityMap.put(name,valueList);
            return;
        }
        valueList.add(entity);
    }

}

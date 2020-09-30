package com.nlmk.potapov.tm.repository;

import java.util.ArrayList;
import java.util.List;

public class AbstractRepository<T> {

    private final List<T> entity = new ArrayList<>();

    public List<T> getEntity() {
        return entity;
    }

    public int size() {
        return entity.size();
    }

    public List<T> findAll() {
        return (List<T>)entity;
    }

}

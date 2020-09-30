package com.nlmk.potapov.tm.repository;

import java.util.ArrayList;
import java.util.List;

public class AbstractRepository<T> {

    private final List<T> entity = new ArrayList<>();

    public List<T> getEntity() {
        return entity;
    }

}

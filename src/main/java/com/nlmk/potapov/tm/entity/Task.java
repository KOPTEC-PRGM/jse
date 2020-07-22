package com.nlmk.potapov.tm.entity;

public class Task {

    private final Long id = System.nanoTime();

    private String name = "";

    private String description = "";

    public Task() {
    }

    public Task(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return id + " : " + name;
    }

}

package com.nlmk.potapov.tm.entity;

public class Project {

    private final Long id = System.nanoTime();

    private String name = "";

    private String description = "";

    public Project() {
    }

    public Project(String name) {
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
package com.nlmk.potapov.tm.dao;

import com.nlmk.potapov.tm.entity.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    private List<Project> projects = new ArrayList<>();

    public Project create(final String name) {
        final Project project = new Project(name);
        projects.add(project);
        return project;
    }

    public void clear() {
        projects.clear();
    }

    public List<Project> findAll() {
        return projects;
    }

}

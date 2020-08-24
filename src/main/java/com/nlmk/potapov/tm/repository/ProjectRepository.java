package com.nlmk.potapov.tm.repository;

import com.nlmk.potapov.tm.entity.Project;

import java.util.*;

public class ProjectRepository {

    private final List<Project> projects = new ArrayList<>();

    private final Map<String,List<Project>> projectMap = new HashMap<>();

    public void addToProjectMap(Project project) {
        final String name = project.getName();
        List<Project> valueList = projectMap.get(name);
        if (valueList == null || valueList.isEmpty()) {
            valueList = new ArrayList<>();
            valueList.add(project);
            projectMap.put(name,valueList);
            return;
        }
        valueList.add(project);
    }

    public Project create(final String name) {
        final Project project = new Project(name);
        projects.add(project);
        addToProjectMap(project);
        return project;
    }

    public Project create(final String name, final String description, final Long userId) {
        final Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setUserId(userId);
        projects.add(project);
        return project;
    }

    public Project update(final Long id, final String name, final String description) {
        final Project project = findById(id);
        if (project == null) return null;
        project.setId(id);
        project.setName(name);
        project.setDescription(description);
        return project;
    }

    public void clear() {
        projects.clear();
    }

    public Project findByIndex(final int index) {
        return projects.get(index);
    }

    public Project findByName(final String name) {
        for (final Project project: projects){
            if (project.getName().equals(name)) return project;
        }
        return null;
    }

    public Project findById(final Long id) {
        for (final Project project: projects){
            if (project.getId().equals(id)) return project;
        }
        return null;
    }

    public Project removeByIndex(final int index) {
        final Project project = findByIndex(index);
        if (project == null) return null;
        projects.remove(project);
        return project;
    }

    public Project removeByName(final String name) {
        final Project project = findByName(name);
        if (project == null) return null;
        projects.remove(project);
        return project;
    }

    public Project removeById(final Long id) {
        final Project project = findById(id);
        if (project == null) return null;
        projects.remove(project);
        return project;
    }

    public List<Project> findAll() {
        return projects;
    }

    public List<Project> findAllByUserId(final Long userId) {
        List<Project> userProjects = new ArrayList<>();
        for (Project project: findAll())
            if (userId.equals(project.getUserId())) userProjects.add(project);
        return userProjects;
    }

    public int size() {
        return projects.size();
    }

    public Project assertUserIdById(final Long id, final Long userId) {
        Project project = findById(id);
        project.setUserId(userId);
        return project;
    }

    public Project assertUserIdByName(final String name, final Long userId) {
        Project project = findByName(name);
        project.setUserId(userId);
        return project;
    }

    public List<Project> sortList() {
        projects.sort(Comparator.comparing(Project::getName).thenComparing(Project::getDescription));
        return projects;
    }

}
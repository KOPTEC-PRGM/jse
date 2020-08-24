package com.nlmk.potapov.tm.repository;

import com.nlmk.potapov.tm.entity.Project;
import com.nlmk.potapov.tm.entity.Task;

import java.util.*;

public class ProjectRepository {

    private final List<Project> projects = new ArrayList<>();

    private final Map<String,List<Project>> projectMap = new HashMap<>();

    public void addToProjectMap(final Project project) {
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

    public void removeFromTaskMap(final Project project) {
        final String name = project.getName();
        List<Project> valueList = projectMap.get(name);
        if (valueList == null || valueList.isEmpty()) return;
        if (valueList.size() > 1) valueList.remove(project);
        else projectMap.remove(name);
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
        addToProjectMap(project);
        return project;
    }

    public Project update(final Long id, final String name, final String description) {
        final Project project = findById(id);
        if (project == null) return null;
        if (!project.getName().equals(name)) {
            removeFromTaskMap(project);
            project.setName(name);
            addToProjectMap(project);
        }
        project.setId(id);
        project.setDescription(description);
        return project;
    }

    public void clear() {
        projects.clear();
        projectMap.clear();
    }

    public Project findByIndex(final int index) {
        return projects.get(index);
    }

    public Project findByName(final String name, final Long userId, final int position) {
        final List<Project> projectList;
        if (userId == null) projectList = projectMap.get(name);
        else projectList = filterListByUserId(projectMap.get(name), userId);
        if (projectList == null || projectList.isEmpty()) return null;
        return projectList.get(position);
    }

    public List<Project> findListByName(final String name, final Long userId) {
        if (userId == null)  return projectMap.get(name);
        return filterListByUserId(projectMap.get(name),userId);
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

    public List<Project> removeByName(final String name, final Long userId) {
        final List<Project> projectList = findListByName(name,userId);
        if (projectList == null || projectList.isEmpty()) return null;
        projectMap.remove(name);
        for (Project project: projectList) projects.remove(project);
        return projectList;
    }

    public Project removeByName(final String name, final Long userId, final int position) {
        final Project project = findByName(name, userId, position);
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

    public Project assignUserIdById(final Long id, final Long userId) {
        Project project = findById(id);
        project.setUserId(userId);
        return project;
    }

    public Project assignUserIdByName(final String name, final Long userId, final Long currentUserId, final int position) {
        Project project = findByName(name,currentUserId,position);
        project.setUserId(userId);
        return project;
    }

    public List<Project> sortList() {
        projects.sort(Comparator.comparing(Project::getName).thenComparing(Project::getDescription));
        return projects;
    }

    public List<Project> filterListByUserId(final List<Project> projectList, final Long userId) {
        final List<Project> filteredList = new ArrayList<>();
        for (Project project: projectList)
            if(project.getUserId().equals(userId)) filteredList.add(project);
        return filteredList;
    }

}
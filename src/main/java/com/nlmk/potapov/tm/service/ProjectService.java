package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.entity.Project;
import com.nlmk.potapov.tm.repository.ProjectRepository;

import java.util.List;

public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project create(String name) {
        if (name == null || name.isEmpty()) return null;
        return projectRepository.create(name);
    }

    public Project create(String name, String description) {
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return projectRepository.create(name, description);
    }

    public Project update(Long id, String name, String description) {
        if (id == null ) return null;
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return projectRepository.update(id, name, description);
    }

    public void clear() {
        projectRepository.clear();
    }

    public Project findByIndex(int index) {
        if (index < 0 || index > projectRepository.size() -1) return null;
        return projectRepository.findByIndex(index);
    }

    public Project findByName(String name) {
        if (name == null || name.isEmpty()) return null;
        return projectRepository.findByName(name);
    }

    public Project findById(Long id) {
        if (id == null ) return null;
        return projectRepository.findById(id);
    }

    public Project removeByIndex(int index) {
        if (index < 0 || index > projectRepository.size() -1) return null;
        return projectRepository.removeByIndex(index);
    }

    public Project removeByName(String name) {
        if (name == null || name.isEmpty()) return null;
        return projectRepository.removeByName(name);
    }

    public Project removeById(Long id) {
        if (id == null ) return null;
        return projectRepository.removeById(id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

}
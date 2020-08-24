package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.entity.Project;
import com.nlmk.potapov.tm.repository.ProjectRepository;

import java.util.List;

public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project create(final String name) {
        if (name == null || name.isEmpty()) return null;
        return projectRepository.create(name);
    }

    public Project create(final String name, final String description, final Long userId) {
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return projectRepository.create(name, description, userId);
    }

    public Project update(final Long id, final String name, final String description) {
        if (id == null ) return null;
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return projectRepository.update(id, name, description);
    }

    public void clear() {
        projectRepository.clear();
    }

    public Project findByIndex(final int index) {
        if (index < 0 || index > projectRepository.size() -1) return null;
        return projectRepository.findByIndex(index);
    }

    public Project findByName(final String name, final Long userId, final int position) {
        if (name == null || name.isEmpty()) return null;
        return projectRepository.findByName(name,userId,position);
    }

    public List<Project> findListByName(String name, Long userId) {
        if (name == null || name.isEmpty()) return null;
        return projectRepository.findListByName(name, userId);
    }

    public Project findById(final Long id) {
        if (id == null ) return null;
        return projectRepository.findById(id);
    }

    public Project removeByIndex(final int index) {
        if (index < 0 || index > projectRepository.size() -1) return null;
        return projectRepository.removeByIndex(index);
    }

    public List<Project> removeByName(String name, Long userId) {
        if (name == null || name.isEmpty()) return null;
        return projectRepository.removeByName(name, userId);
    }

    public Project removeByName(final String name, final Long userId, final int position) {
        if (name == null || name.isEmpty()) return null;
        return projectRepository.removeByName(name,userId,position);
    }

    public Project removeById(final Long id) {
        if (id == null ) return null;
        return projectRepository.removeById(id);
    }

    public Project assignUserIdByName(final String name, final Long userId, final Long currentUserId, final int position) {
        if (name == null || name.isEmpty()) return null;
        if (userId == null ) return null;
        return projectRepository.assignUserIdByName(name,userId,currentUserId,position);
    }

    public List<Project> findAll(final Long userId) {
        return projectRepository.findAll(userId);
    }


    public List<Project> sortList() {
        return projectRepository.sortList();
    }

}
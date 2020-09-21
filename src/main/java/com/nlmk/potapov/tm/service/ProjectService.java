package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.entity.Project;
import com.nlmk.potapov.tm.exception.ProjectException;
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

    public Project update(final Long id, final String name, final String description, final Long userId) {
        if (id == null ) return null;
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return projectRepository.update(id, name, description, userId);
    }

    public boolean remove(Project project) {
        if (project == null) return false;
        return projectRepository.remove(project);
    }

    public void clear() {
        projectRepository.clear();
    }

    public Project findByIndex(final int index, final Long userId) throws ProjectException {
        if (index < 0 || index > projectRepository.size() -1){
            throw new ProjectException("Project does not exist");
        }
        return returnNotNull(projectRepository.findByIndex(index,userId));
    }

    public Project findByName(final String name, final Long userId, final int position) throws ProjectException {
        if (name == null || name.isEmpty()) return null;
        return returnNotNull(projectRepository.findByName(name, userId, position));
    }

    public List<Project> findListByName(String name, Long userId) throws ProjectException {
        if (name == null || name.isEmpty()) return null;
        return returnNotEmpty(projectRepository.findListByName(name, userId));
    }

    public Project findById(final Long id, final Long userId) throws ProjectException {
        if (id == null ) return null;
        return returnNotNull(projectRepository.findById(id, userId));
    }

    public Project removeByIndex(final int index, final Long userId) throws ProjectException {
        if (index < 0 || index > projectRepository.size() -1) return null;
        return returnNotNull(projectRepository.removeByIndex(index, userId));
    }

    public List<Project> removeByName(String name, Long userId) throws ProjectException {
        if (name == null || name.isEmpty()) return null;
        return returnNotEmpty(projectRepository.removeByName(name, userId));
    }

    public Project removeById(final Long id, final Long userId) throws ProjectException {
        if (id == null ) return null;
        return returnNotNull(projectRepository.removeById(id, userId));
    }

    public Project assignUserIdByName(final String name, final Long userId, final Long currentUserId, final int position) throws ProjectException {
        if (name == null || name.isEmpty()) return null;
        if (userId == null ) return null;
        return returnNotNull(projectRepository.assignUserIdByName(name, userId, currentUserId, position));
    }

    public List<Project> findAll(final Long userId) throws ProjectException {
        return returnNotEmpty(projectRepository.findAll(userId));
    }

    public List<Project> sortList() {
        return projectRepository.sortList();
    }

    private Project returnNotNull(Project project) throws ProjectException {
        if (project == null){
            throw new ProjectException("Project does not exist");
        }
        return project;
    }

    private List<Project> returnNotEmpty(List<Project> projects) throws ProjectException {
        if (projects == null || projects.isEmpty()){
            throw new ProjectException("Projects don't exist");
        }
        return projects;
    }

}
package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.entity.Task;
import com.nlmk.potapov.tm.repository.TaskRepository;

import java.util.List;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task create(final String name) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.create(name);
    }

    public Task create(final String name, final String description, final Long userId) {
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return taskRepository.create(name, description, userId);
    }

    public Task update(final Long id, final String name, final String description) {
        if (id == null ) return null;
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return taskRepository.update(id, name, description);
    }

    public void clear() {
        taskRepository.clear();
    }

    public Task findByIndex(final int index) {
        if (index < 0 || index > taskRepository.size() -1) return null;
        return taskRepository.findByIndex(index);
    }

    public Task findByName(final String name) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.findByName(name);
    }

    public Task findById(final Long id) {
        if (id == null ) return null;
        return taskRepository.findById(id);
    }

    public Task removeByIndex(final int index) {
        if (index < 0 || index > taskRepository.size() -1) return null;
        return taskRepository.removeByIndex(index);
    }

    public Task removeByName(final String name) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.removeByName(name);
    }

    public Task removeById(final Long id) {
        if (id == null ) return null;
        return taskRepository.removeById(id);
    }

    public Task assignUserIdById(final Long id, final Long userId) {
        if (userId == null ) return null;
        if (id == null ) return null;
        return taskRepository.assignUserIdById(id, userId);
    }

    public Task assignUserIdByName(final String name, final Long userId) {
        if (name == null || name.isEmpty()) return null;
        if (userId == null ) return null;
        return taskRepository.assignUserIdByName(name, userId);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findAllByUserId(final Long userId) {
        if (userId == null) return null;
        return taskRepository.findAllByUserId(userId);
    }

    public Task assignProjectId(final Long id, final Long projectId) {
        if (id == null ) return null;
        if (projectId == null ) return null;
        return taskRepository.assertProjectId(id, projectId);
    }

    public Task findByProjectIdAndId(final Long projectId, final Long id) {
        if (projectId == null ) return null;
        if (id == null ) return null;
        return taskRepository.findByProjectIdAndId(projectId, id);
    }

    public List<Task> viewTasksFromProject(final Long projectId) {
        if (projectId == null ) return null;
        return taskRepository.viewTasksFromProject(projectId);
    }

}
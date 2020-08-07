package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.entity.Task;
import com.nlmk.potapov.tm.repository.TaskRepository;

import java.util.List;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task create(String name) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.create(name);
    }

    public Task create(String name, String description) {
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return taskRepository.create(name, description);
    }

    public Task update(Long id, String name, String description) {
        if (id == null ) return null;
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return taskRepository.update(id, name, description);
    }

    public void clear() {
        taskRepository.clear();
    }

    public Task findByIndex(int index) {
        if (index < 0 || index > taskRepository.size() -1) return null;
        return taskRepository.findByIndex(index);
    }

    public Task findByName(String name) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.findByName(name);
    }

    public Task findById(Long id) {
        if (id == null ) return null;
        return taskRepository.findById(id);
    }

    public Task removeByIndex(int index) {
        if (index < 0 || index > taskRepository.size() -1) return null;
        return taskRepository.removeByIndex(index);
    }

    public Task removeByName(String name) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.removeByName(name);
    }

    public Task removeById(Long id) {
        if (id == null ) return null;
        return taskRepository.removeById(id);
    }

    public Task assertUserIdById(Long id, Long userId) {
        if (userId == null ) return null;
        if (id == null ) return null;
        return taskRepository.assertUserIdById(id, userId);
    }

    public Task assertUserIdByName(String name, Long userId) {
        if (name == null || name.isEmpty()) return null;
        if (userId == null ) return null;
        return taskRepository.assertUserIdByName(name, userId);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findAllByUserId(Long userId) {
        if (userId == null) return null;
        return taskRepository.findAllByUserId(userId);
    }

    public Task assertProjectId(final Long id, final Long projectId) {
        if (id == null ) return null;
        if (projectId == null ) return null;
        return taskRepository.assertProjectId(id, projectId);
    }

    public Task findByProjectIdAndId(Long projectId, Long id) {
        if (projectId == null ) return null;
        if (id == null ) return null;
        return taskRepository.findByProjectIdAndId(projectId, id);
    }

    public List<Task> viewTasksFromProject(Long projectId) {
        if (projectId == null ) return null;
        return taskRepository.viewTasksFromProject(projectId);
    }

}
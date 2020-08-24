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

    public Task findByName(final String name, final Long userId, final int position) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.findByName(name, userId, position);
    }

    public List<Task> findListByName(String name, Long userId) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.findListByName(name, userId);
    }

    public Task findById(final Long id) {
        if (id == null ) return null;
        return taskRepository.findById(id);
    }

    public Task removeByIndex(final int index) {
        if (index < 0 || index > taskRepository.size() -1) return null;
        return taskRepository.removeByIndex(index);
    }

    public List<Task> removeByName(final String name) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.removeByName(name);
    }

    public Task removeByName(String name, Long userId, Integer position) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.removeByName(name, userId, position);
    }

    public Task removeById(final Long id) {
        if (id == null ) return null;
        return taskRepository.removeById(id);
    }

    public Task assignUserIdByName(final String name, final Long userId, final Long currentUserId, final int position) {
        if (name == null || name.isEmpty()) return null;
        if (userId == null ) return null;
        return taskRepository.assignUserIdByName(name, userId, currentUserId, position);
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

    public List<Task> sortList() {
        return taskRepository.sortList();
    }

}
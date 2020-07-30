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

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

}

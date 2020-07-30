package com.nlmk.potapov.tm.repository;

import com.nlmk.potapov.tm.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private List<Task> tasks = new ArrayList<>();

    public Task create(final String name) {
        final Task task = new Task(name);
        tasks.add(task);
        return task;
    }

    public Task create(final String name, final String description) {
        final Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        tasks.add(task);
        return task;
    }

    public Task update(final Long id, final String name, final String description) {
        final Task task = findById(id);
        if (task == null) return null;
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        return task;
    }

    public void clear() {
        tasks.clear();
    }

    public Task findByIndex(final int index) {
        return tasks.get(index);
    }

    public Task findByName(final String name) {
        for (final Task task: tasks){
            if (task.getName().equals(name)) return task;
        }
        return null;
    }

    public Task findById(final Long id) {
        for (final Task task: tasks){
            if (task.getId().equals(id)) return task;
        }
        return null;
    }

    public Task removeByIndex(final int index) {
        final Task task = findByIndex(index);
        if (task == null) return null;
        tasks.remove(task);
        return task;
    }

    public Task removeByName(final String name) {
        final Task task = findByName(name);
        if (task == null) return null;
        tasks.remove(task);
        return task;
    }

    public Task removeById(final Long id) {
        final Task task = findById(id);
        if (task == null) return null;
        tasks.remove(task);
        return task;
    }

    public List<Task> findAll() {
        return tasks;
    }

    public int size(){
        return tasks.size();
    }

}
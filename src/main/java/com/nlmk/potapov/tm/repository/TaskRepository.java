package com.nlmk.potapov.tm.repository;

import com.nlmk.potapov.tm.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private final List<Task> tasks = new ArrayList<>();

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

    public Task findByProjectIdAndId(final Long projectId, final Long id) {
        for (final Task task: tasks){
            Long idProject = task.getProjectId();
            if (idProject == null) continue;
            if (!idProject.equals(projectId)) continue;
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

    public List<Task> findAllByUserId(final Long userId) {
        List<Task> userTasks = new ArrayList<>();
        for (Task task: findAll())
            if (userId.equals(task.getUserId())) userTasks.add(task);
        return userTasks;
    }

    public int size(){
        return tasks.size();
    }

    public List<Task> viewTasksFromProject (final Long projectId){
        List<Task> result = new ArrayList<>();
        for (Task task: findAll()){
            if (task.getProjectId() == null) continue;
            if (task.getProjectId().equals(projectId))
                result.add(task);
        }
        return result;
    }

    public Task assertUserIdById(final Long id, final Long userId) {
        Task task = findById(id);
        task.setUserId(userId);
        return task;
    }

    public Task assertUserIdByName(final String name, final Long userId) {
        Task task = findByName(name);
        task.setUserId(userId);
        return task;
    }

    public Task assertProjectId(final Long id, final Long projectId) {
        Task task = findById(id);
        task.setProjectId(projectId);
        return task;
    }

}
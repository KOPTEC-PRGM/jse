package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.entity.Task;
import com.nlmk.potapov.tm.exception.TaskException;
import com.nlmk.potapov.tm.repository.TaskRepository;

import java.util.Collections;
import java.util.List;

import static com.nlmk.potapov.tm.constant.TerminalConst.EMPTY_TASK_LIST_EXCEPTION;
import static com.nlmk.potapov.tm.constant.TerminalConst.NULL_TASK_EXCEPTION;

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

    public Task update(final Long id, final String name, final String description, final Long userId) {
        if (id == null ) return null;
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return taskRepository.update(id, name, description, userId);
    }

    public void clear() {
        taskRepository.clear();
    }

    public Task findByIndex(final int index, final Long userId) throws TaskException {
        if (index < 0 || index > taskRepository.size() -1){
            throw new TaskException("Task does not exist");
        }
        return throwExceptionIfNull(taskRepository.findByIndex(index, userId));
    }

    public Task findByName(final String name, final Long userId, final int position) throws TaskException {
        if (name == null || name.isEmpty()) return null;
        return throwExceptionIfNull(taskRepository.findByName(name, userId, position));
    }

    public List<Task> findListByName(String name, Long userId) throws TaskException {
        if (name == null || name.isEmpty()) return Collections.emptyList();
        return throwExceptionIfEmpty(taskRepository.findListByName(name, userId));
    }

    public Task findById(final Long id, final Long userId) throws TaskException {
        if (id == null ) return null;
        return throwExceptionIfNull(taskRepository.findById(id, userId));
    }

    public Task removeByIndex(final int index, final Long userId) throws TaskException {
        if (index < 0 || index > taskRepository.size() -1) return null;
        return throwExceptionIfNull(taskRepository.removeByIndex(index, userId));
    }

    public List<Task> removeByName(final String name) throws TaskException {
        if (name == null || name.isEmpty()) return Collections.emptyList();
        return throwExceptionIfEmpty(taskRepository.removeByName(name));
    }

    public Task removeByName(String name, Long userId, Integer position) throws TaskException {
        if (name == null || name.isEmpty()) return null;
        return throwExceptionIfNull(taskRepository.removeByName(name, userId, position));
    }

    public Task removeById(final Long id, final Long userId) throws TaskException {
        if (id == null ) return null;
        return throwExceptionIfNull(taskRepository.removeById(id, userId));
    }

    public Task assignUserIdByName(final String name, final Long userId, final Long currentUserId, final int position) throws TaskException {
        if (name == null || name.isEmpty()) return null;
        if (userId == null ) return null;
        return throwExceptionIfNull(taskRepository.assignUserIdByName(name, userId, currentUserId, position));
    }

    public List<Task> findAll() throws TaskException {
        return throwExceptionIfEmpty(taskRepository.findAll());
    }

    public List<Task> findAllByUserId(final Long userId) throws TaskException {
        if (userId == null) return Collections.emptyList();
        return throwExceptionIfEmpty(taskRepository.findAllByUserId(userId));
    }

    public Task assignProjectId(final Long id, final Long projectId, final Long userId) throws TaskException {
        if (id == null ) return null;
        if (projectId == null ) return null;
        return throwExceptionIfNull(taskRepository.assignProjectId(id, projectId, userId));
    }

    public List<Task> sortList() throws TaskException {
        return throwExceptionIfEmpty(taskRepository.sortList());
    }

    private Task throwExceptionIfNull(final Task task) throws TaskException {
        if (task == null){
            throw new TaskException(NULL_TASK_EXCEPTION);
        }
        return task;
    }

    private List<Task> throwExceptionIfEmpty(final List<Task> tasks) throws TaskException {
        if (tasks == null || tasks.isEmpty()){
            throw new TaskException(EMPTY_TASK_LIST_EXCEPTION);
        }
        return tasks;
    }

}
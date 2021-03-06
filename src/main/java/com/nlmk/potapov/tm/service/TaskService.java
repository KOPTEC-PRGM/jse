package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.entity.Task;
import com.nlmk.potapov.tm.exception.TaskException;
import com.nlmk.potapov.tm.repository.ProjectRepository;
import com.nlmk.potapov.tm.repository.TaskRepository;
import com.nlmk.potapov.tm.util.ServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

import static com.nlmk.potapov.tm.constant.TerminalConst.EMPTY_TASK_LIST_EXCEPTION;
import static com.nlmk.potapov.tm.constant.TerminalConst.NULL_TASK_EXCEPTION;

public class TaskService {

    private static final Logger logger = LogManager.getLogger(TaskService.class);

    private static TaskService instance;

    private final TaskRepository taskRepository;

    private TaskService() {
        this.taskRepository = TaskRepository.getInstance();
    }

    public static TaskService getInstance() {
        if (instance == null) {
            synchronized (TaskService.class) {
                if (instance == null) {
                    instance = new TaskService();
                }
            }
        }
        return instance;
    }

    public Task create(final String name) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.create(name);
    }

    public Task create(final String name, final String description, final Long userId) {
        ServiceUtil.traceLogger(logger, "create", new Object[]{name, description, userId});
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return taskRepository.create(name, description, userId);
    }

    public Task update(final Long id, final String name, final String description, final Long userId) {
        ServiceUtil.traceLogger(logger, "update", new Object[]{id, name, description, userId});
        if (id == null) return null;
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return taskRepository.update(id, name, description, userId);
    }

    public void clear() {
        ServiceUtil.traceLogger(logger, "clear", new Object[]{});
        taskRepository.clear();
    }

    public Task findByIndex(final int index, final Long userId) throws TaskException {
        ServiceUtil.traceLogger(logger, "findByIndex", new Object[]{index, userId});
        if (index < 0 || index > taskRepository.size() - 1) {
            throw new TaskException(NULL_TASK_EXCEPTION);
        }
        return throwExceptionIfNull(taskRepository.findByIndex(index, userId));
    }

    public Task findByName(final String name, final Long userId, final int position) throws TaskException {
        ServiceUtil.traceLogger(logger, "findByName", new Object[]{name, userId, position});
        if (name == null || name.isEmpty()) return null;
        return throwExceptionIfNull(taskRepository.findByName(name, userId, position));
    }

    public List<Task> findListByName(String name, Long userId) throws TaskException {
        ServiceUtil.traceLogger(logger, "findListByName", new Object[]{name, userId});
        if (name == null || name.isEmpty()) return Collections.emptyList();
        return throwExceptionIfEmpty(taskRepository.findListByName(name, userId));
    }

    public Task findById(final Long id, final Long userId) throws TaskException {
        ServiceUtil.traceLogger(logger, "findById", new Object[]{id, userId});
        if (id == null) return null;
        return throwExceptionIfNull(taskRepository.findById(id, userId));
    }

    public Task removeByIndex(final int index, final Long userId) throws TaskException {
        ServiceUtil.traceLogger(logger, "removeByIndex", new Object[]{index, userId});
        if (index < 0 || index > taskRepository.size() - 1) return null;
        return throwExceptionIfNull(taskRepository.removeByIndex(index, userId));
    }

    public List<Task> removeByName(final String name) throws TaskException {
        ServiceUtil.traceLogger(logger, "removeByName", new Object[]{name});
        if (name == null || name.isEmpty()) return Collections.emptyList();
        return throwExceptionIfEmpty(taskRepository.removeByName(name));
    }

    public Task removeByName(String name, Long userId, Integer position) throws TaskException {
        ServiceUtil.traceLogger(logger, "removeByName", new Object[]{name, userId, position});
        if (name == null || name.isEmpty()) return null;
        return throwExceptionIfNull(taskRepository.removeByName(name, userId, position));
    }

    public Task removeById(final Long id, final Long userId) throws TaskException {
        ServiceUtil.traceLogger(logger, "removeById", new Object[]{id, userId});
        if (id == null) return null;
        return throwExceptionIfNull(taskRepository.removeById(id, userId));
    }

    public Task assignUserIdByName(final String name, final Long userId, final Long currentUserId, final int position) throws TaskException {
        ServiceUtil.traceLogger(logger, "assignUserIdByName", new Object[]{name, userId, currentUserId, position});
        if (name == null || name.isEmpty()) return null;
        if (userId == null) return null;
        return throwExceptionIfNull(taskRepository.assignUserIdByName(name, userId, currentUserId, position));
    }

    public List<Task> findAll() throws TaskException {
        ServiceUtil.traceLogger(logger, "findAll", new Object[]{});
        return throwExceptionIfEmpty(taskRepository.findAll());
    }

    public List<Task> findAllByUserId(final Long userId) throws TaskException {
        ServiceUtil.traceLogger(logger, "findAllByUserId", new Object[]{userId});
        if (userId == null) return Collections.emptyList();
        return throwExceptionIfEmpty(taskRepository.findAllByUserId(userId));
    }

    public void assignProjectId(final Long id, final Long projectId, final Long userId) {
        ServiceUtil.traceLogger(logger, "assignProjectId", new Object[]{id, projectId, userId});
        if (id == null) return;
        if (projectId == null) return;
        taskRepository.assignProjectId(id, projectId, userId);
    }

    public void sortList() {
        ServiceUtil.traceLogger(logger, "sortList", new Object[]{});
        taskRepository.sortList();
    }

    private Task throwExceptionIfNull(final Task task) throws TaskException {
        if (task == null) {
            throw new TaskException(NULL_TASK_EXCEPTION);
        }
        return task;
    }

    private List<Task> throwExceptionIfEmpty(final List<Task> tasks) throws TaskException {
        if (tasks == null || tasks.isEmpty()) {
            throw new TaskException(EMPTY_TASK_LIST_EXCEPTION);
        }
        return tasks;
    }

    public void saveToJson(String filePath) {
        if (filePath.isEmpty()) filePath = "defaultTaskRepository.json";
        taskRepository.saveToJson(filePath);
    }

    public void saveToXml(String filePath) {
        if (filePath.isEmpty()) filePath = "defaultTaskRepository.xml";
        taskRepository.saveToXml(filePath);
    }

    public void loadFromJson(String filePath) {
        if (filePath.isEmpty()) filePath = "defaultTaskRepository.json";
        taskRepository.loadFromJson(filePath);
    }

    public void loadFromXml(String filePath) {
        if (filePath.isEmpty()) filePath = "defaultTaskRepository.xml";
        taskRepository.loadFromXml(filePath);
    }

}
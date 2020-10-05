package com.nlmk.potapov.tm.repository;

import com.nlmk.potapov.tm.entity.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class TaskRepository {

    private static TaskRepository instance;

    private static final Logger logger = LogManager.getLogger(TaskRepository.class);

    private final List<Task> tasks = new ArrayList<>();

    private final Map<String,List<Task>> taskMap = new HashMap<>();

    private TaskRepository(){
    }

    public static TaskRepository getInstance() {
        if (instance == null) {
            synchronized (TaskRepository.class) {
                if (instance == null) {
                    instance = new TaskRepository();
                }
            }
        }
        return instance;
    }

    public void addToTaskMap(final Task task) {
        final String name = task.getName();
        List<Task> valueList = taskMap.get(name);
        if (valueList == null || valueList.isEmpty()) {
            valueList = new ArrayList<>();
            valueList.add(task);
            taskMap.put(name,valueList);
            return;
        }
        valueList.add(task);
    }

    public void removeFromTaskMap(final Task task) {
        final String name = task.getName();
        List<Task> valueList = taskMap.get(name);
        if (valueList == null || valueList.isEmpty()) return;
        if (valueList.size() > 1) valueList.remove(task);
        else taskMap.remove(name);
    }

    public Task create(final String name) {
        final Task task = new Task(name);
        tasks.add(task);
        addToTaskMap(task);
        logger.info(LOGGER_CREATE_TASK, task);
        return task;
    }

    public Task create(final String name, final String description, final Long userId) {
        final Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setUserId(userId);
        tasks.add(task);
        addToTaskMap(task);
        logger.info(LOGGER_CREATE_TASK, task);
        return task;
    }

    public Task update(final Long id, final String name, final String description, final Long userId) {
        final Task task = findById(id, userId);
        if (task == null) return null;
        if (!task.getName().equals(name)) {
            removeFromTaskMap(task);
            task.setName(name);
            addToTaskMap(task);
        }
        task.setId(id);
        task.setDescription(description);
        logger.info(LOGGER_UPDATE_TASK, task);
        return task;
    }

    public void clear() {
        tasks.clear();
        taskMap.clear();
        logger.info("Все задачи удалены");
    }

    public Task findByIndex(final int index, final Long userId) {
        if (userId == null) return tasks.get(index);
        final List<Task> filteredList = filterListByUserId(tasks, userId);
        return filteredList.get(index);
    }

    public Task findByName(final String name, final Long userId, final int position) {
        final List<Task> taskList;
        if (userId == null) taskList = taskMap.get(name);
        else taskList = filterListByUserId(taskMap.get(name),userId);
        if (taskList == null || taskList.isEmpty()) return null;
        return taskList.get(position);
    }

    public List<Task> findListByName(final String name) {
        return taskMap.get(name);
    }

    public List<Task> findListByName(final String name, final Long userId) {
        if (userId == null)  return taskMap.get(name);
        return filterListByUserId(taskMap.get(name),userId);
    }

    public Task findById(final Long id, final Long userId) {
        for (final Task task: tasks){
            if ((task.getId().equals(id))
            && (userId == null || task.getUserId().equals(userId)))return task;

        }
        return null;
    }

    public Task findByProjectIdAndId(final Long projectId, final Long id, final Long userId) {
        for (final Task task: tasks){
            Long idProject = task.getProjectId();
            if ((idProject == null)
            || (!idProject.equals(projectId) )
            || (!task.getUserId().equals(userId) && userId != null))continue;
            if (task.getId().equals(id)) return task;
        }
        return null;
    }

    public Task removeByIndex(final int index, final Long userId) {
        final Task task = findByIndex(index, userId);
        if (task == null) return null;
        tasks.remove(task);
        removeFromTaskMap(task);
        logger.info(LOGGER_DELETE_TASK, task);
        return task;
    }

    public List<Task> removeByName(final String name) {
        final List<Task> taskList = findListByName(name);
        if (taskList == null || taskList.isEmpty()) return Collections.emptyList();
        taskMap.remove(name);
        for (Task task: taskList){
            logger.info(LOGGER_DELETE_TASK,task);
            tasks.remove(task);
        }
        return taskList;
    }

    public Task removeByName(final String name, final Long userId, final Integer position) {
        final List<Task> taskList = findListByName(name);
        if (taskList == null || taskList.isEmpty()) return null;
        final Task task;
        if (userId == null)  task = taskList.get(position);
        else task = filterListByUserId(taskList,userId).get(position);
        taskList.remove(task);
        tasks.remove(task);
        logger.info(LOGGER_DELETE_TASK, task);
        return task;
    }

    public Task removeById(final Long id, final Long userId) {
        final Task task = findById(id, userId);
        if (task == null) return null;
        tasks.remove(task);
        removeFromTaskMap(task);
        logger.info(LOGGER_DELETE_TASK, task);
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

    public List<Task> getTasksFromProject (final Long projectId){
        List<Task> result = new ArrayList<>();
        for (Task task: findAll()){
            if (task.getProjectId() == null) continue;
            if (task.getProjectId().equals(projectId))
                result.add(task);
        }
        return result;
    }

    public Task assignUserIdByName(final String name, final Long userId, final Long currentUserId, final int position) {
        Task task = findByName(name, currentUserId, position);
        task.setUserId(userId);
        logger.info("Задаче [{}] назначен пользователь ID={}", task, userId);
        return task;
    }

    public Task assignProjectId(final Long id, final Long projectId, final Long userId) {
        Task task = findById(id, userId);
        task.setProjectId(projectId);
        logger.info("Задача [{}] привязана к проекту ID={}", task, projectId);
        return task;
    }

    public List<Task> sortList() {
        tasks.sort(Comparator.comparing(Task::getName).thenComparing(Task::getDescription));
        return tasks;
    }

    public List<Task> filterListByUserId(final List<Task> taskList, final Long userId) {
        final List<Task> filteredList = new ArrayList<>();
        for (Task task: taskList)
            if(task.getUserId().equals(userId)) filteredList.add(task);
        return filteredList;
    }

}
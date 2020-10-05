package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.entity.Project;
import com.nlmk.potapov.tm.entity.Task;
import com.nlmk.potapov.tm.exception.ProjectException;
import com.nlmk.potapov.tm.exception.TaskException;
import com.nlmk.potapov.tm.repository.ProjectRepository;
import com.nlmk.potapov.tm.repository.TaskRepository;

import java.util.Collections;
import java.util.List;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class ProjectTaskService {

    private static ProjectTaskService instance;

    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

    private ProjectTaskService() {
        this.projectRepository = ProjectRepository.getInstance();
        this.taskRepository = TaskRepository.getInstance();
    }

    public static ProjectTaskService getInstance() {
        if (instance == null) {
            synchronized (ProjectTaskService.class) {
                if (instance == null) {
                    instance = new ProjectTaskService();
                }
            }
        }
        return instance;
    }

    public Task addTaskToProject(final Long projectId, final Long taskId, final Long userId) throws ProjectException, TaskException {
        if (projectId == null) return null;
        if (taskId == null) return null;
        final Project project = projectRepository.findById(projectId, userId);
        if (project == null){
            throw new ProjectException(NULL_PROJECT_EXCEPTION);
        }
        final Task task = taskRepository.findById(taskId, userId);
        if (task == null){
            throw new TaskException(NULL_TASK_EXCEPTION);
        }
        task.setProjectId(projectId);
        return task;
    }

    public Task removeTaskFromProject(final Long projectId, final Long taskId, final Long userId) throws TaskException {
        if (projectId == null) return null;
        if (taskId == null) return null;
        final Task task = taskRepository.findByProjectIdAndId(projectId, taskId, userId);
        if (task == null){
            throw new TaskException(NULL_TASK_EXCEPTION);
        }
        task.setProjectId(null);
        return task;
    }

    public List<Task> viewTasksFromProject(final Long projectId, final Long userId) throws TaskException {
        if (projectId == null) return Collections.emptyList();
        if (projectRepository.findById(projectId, userId) == null){
            throw new TaskException(EMPTY_TASK_LIST_EXCEPTION);
        }
        return taskRepository.getTasksFromProject(projectId);
    }

    public Project removeProjectWithTasks(final Long projectId, final Long userId) throws ProjectException {
        if (projectId == null) return null;
        if (projectRepository.findById(projectId, userId) == null){
            throw new ProjectException(NULL_PROJECT_EXCEPTION);
        }
        List<Task> tasks = taskRepository.getTasksFromProject(projectId);
        for (Task task: tasks){
            taskRepository.removeById(task.getId(), userId);
        }
        return projectRepository.removeById(projectId, userId);
    }

}
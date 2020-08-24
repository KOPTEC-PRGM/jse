package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.entity.Project;
import com.nlmk.potapov.tm.entity.Task;
import com.nlmk.potapov.tm.repository.ProjectRepository;
import com.nlmk.potapov.tm.repository.TaskRepository;

import java.util.Collections;
import java.util.List;

public class ProjectTaskService {

    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

    public ProjectTaskService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public Task addTaskToProject(final Long projectId, final Long taskId, final Long userId){
        if (projectId == null) return null;
        if (taskId == null) return null;
        final Project project = projectRepository.findById(projectId, userId);
        if (project == null) return null;
        final Task task = taskRepository.findById(taskId, userId);
        if (task == null) return null;
        task.setProjectId(projectId);
        return task;
    }

    public Task removeTaskFromProject(final Long projectId, final Long taskId, final Long userId){
        if (projectId == null) return null;
        if (taskId == null) return null;
        final Task task = taskRepository.findByProjectIdAndId(projectId, taskId, userId);
        if (task == null) return null;
        task.setProjectId(null);
        return task;
    }

    public List<Task> viewTasksFromProject(final Long projectId, final Long userId) {
        if (projectId == null) return Collections.emptyList();
        if (projectRepository.findById(projectId, userId) == null) return Collections.emptyList();
        return taskRepository.getTasksFromProject(projectId);
    }

    public Project removeProjectWithTasks(final Long projectId, final Long userId) {
        if (projectId == null) return null;
        if (projectRepository.findById(projectId, userId) == null) return null;
        List<Task> tasks = taskRepository.getTasksFromProject(projectId);
        for (Task task: tasks){
            taskRepository.removeById(task.getId(), userId);
        }
        return projectRepository.removeById(projectId, userId);
    }

}
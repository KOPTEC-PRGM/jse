package com.nlmk.potapov.tm.repository;

import com.nlmk.potapov.tm.entity.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class ProjectRepository extends AbstractRepository<Project> {

    private static final Logger logger = LogManager.getLogger(ProjectRepository.class);
    private static ProjectRepository instance;

    private ProjectRepository() {
    }

    public static ProjectRepository getInstance() {
        if (instance == null) {
            synchronized (ProjectRepository.class) {
                if (instance == null) {
                    instance = new ProjectRepository();
                }
            }
        }
        return instance;
    }

    public void loadFromJson(final String filePath) {
        super.loadFromJson(filePath, Project.class);
    }

    public void loadFromXml(final String filePath) {
        super.loadFromXml(filePath, Project.class);
    }

    public Project create(final String name) {
        final Project project = new Project(name);
        super.create(project);
        logger.info(LOGGER_CREATE_PROJECT, project);
        return project;
    }

    public Project create(final String name, final String description, final Long userId) {
        final Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setUserId(userId);
        super.create(project);
        logger.info(LOGGER_CREATE_PROJECT, project);
        return project;
    }

    public Project update(final Long id, final String name, final String description, final Long userId) {
        final Project project = findById(id, userId);
        if (project == null) return null;
        if (!project.getName().equals(name)) {
            removeFromEntityMap(project);
            project.setName(name);
            super.addToEntityMap(project);
        }
        project.setId(id);
        project.setDescription(description);
        logger.info(LOGGER_UPDATE_PROJECT, project);
        return project;
    }

    public boolean remove(final Project project) {
        entityList.remove(project);
        removeFromEntityMap(project);
        logger.info(LOGGER_DELETE_PROJECT, project);
        return true;
    }

    public void clear() {
        entityList.clear();
        entityMap.clear();
        logger.info("Все проекты удалены");
    }

    public Project findByIndex(final int index, final Long userId) {
        if (userId == null) return entityList.get(index);
        final List<Project> filteredList = filterListByUserId(entityList, userId);
        return filteredList.get(index);
    }

    public Project findByName(final String name, final Long userId, final int position) {
        final List<Project> projectList;
        if (userId == null) projectList = entityMap.get(name);
        else projectList = filterListByUserId(entityMap.get(name), userId);
        if (projectList == null || projectList.isEmpty()) return null;
        return projectList.get(position);
    }

    public List<Project> findListByName(final String name, final Long userId) {
        if (userId == null) return entityMap.get(name);
        return filterListByUserId(entityMap.get(name), userId);
    }

    public Project findById(final Long id, final Long userId) {
        for (final Project project : entityList) {
            if ((project.getId().equals(id))
                    && (userId == null || project.getUserId().equals(userId))) return project;
        }
        return null;
    }

    public Project removeByIndex(final int index, final Long userId) {
        final Project project = findByIndex(index, userId);
        if (project == null) return null;
        if (remove(project)) {
            logger.info(LOGGER_DELETE_PROJECT, project);
            return project;
        }
        return null;
    }

    public List<Project> removeByName(final String name, final Long userId) {
        final List<Project> projectList = findListByName(name, userId);
        if (projectList == null || projectList.isEmpty()) return Collections.emptyList();
        entityMap.remove(name);
        for (Project project : projectList) {
            logger.info(LOGGER_DELETE_PROJECT, project);
            entityList.remove(project);
        }
        return projectList;
    }

    public Project removeById(final Long id, final Long userId) {
        final Project project = findById(id, userId);
        if (project == null) return null;
        entityList.remove(project);
        removeFromEntityMap(project);
        return project;
    }

    public List<Project> findAll(final Long userId) {
        if (userId == null) return entityList;
        else return filterListByUserId(entityList, userId);
    }

    public Project assignUserIdByName(final String name, final Long userId, final Long currentUserId, final int position) {
        Project project = findByName(name, currentUserId, position);
        project.setUserId(userId);
        logger.info("Проекту [{}] назначен пользователь userId={}.", project, userId);
        return project;
    }

    public void sortList() {
        entityList.sort(Comparator.comparing(Project::getName).thenComparing(Project::getDescription));
    }

    public List<Project> filterListByUserId(final List<Project> projectList, final Long userId) {
        final List<Project> filteredList = new ArrayList<>();
        for (Project project : projectList)
            if (project.getUserId().equals(userId)) filteredList.add(project);
        return filteredList;
    }

}
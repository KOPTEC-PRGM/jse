package com.nlmk.potapov.tm.repository;

import com.nlmk.potapov.tm.entity.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class ProjectRepository extends AbstractRepository{

    private static ProjectRepository instance;

    private static final Logger logger = LogManager.getLogger(ProjectRepository.class);

    private final List<Project> projects = getEntity();

    private final Map<String,List<Project>> projectMap = new HashMap<>();

    private ProjectRepository() {
    }

    public static ProjectRepository getInstance() {
        if (instance == null){
            instance = new ProjectRepository();
        }
        return instance;
    }

    public void addToProjectMap(final Project project) {
        final String name = project.getName();
        List<Project> valueList = projectMap.get(name);
        if (valueList == null || valueList.isEmpty()) {
            valueList = new ArrayList<>();
            valueList.add(project);
            projectMap.put(name,valueList);
            return;
        }
        valueList.add(project);
    }

    public void removeFromProjectMap(final Project project) {
        final String name = project.getName();
        List<Project> valueList = projectMap.get(name);
        if (valueList == null || valueList.isEmpty()) return;
        if (valueList.size() > 1) valueList.remove(project);
        else projectMap.remove(name);
    }

    public Project create(final String name) {
        final Project project = new Project(name);
        projects.add(project);
        addToProjectMap(project);
        logger.info(LOGGER_CREATE_PROJECT,project);
        return project;
    }

    public Project create(final String name, final String description, final Long userId) {
        final Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setUserId(userId);
        projects.add(project);
        addToProjectMap(project);
        logger.info(LOGGER_CREATE_PROJECT,project);
        return project;
    }

    public Project update(final Long id, final String name, final String description, final Long userId) {
        final Project project = findById(id,userId);
        if (project == null) return null;
        if (!project.getName().equals(name)) {
            removeFromProjectMap(project);
            project.setName(name);
            addToProjectMap(project);
        }
        project.setId(id);
        project.setDescription(description);
        logger.info(LOGGER_UPDATE_PROJECT, project);
        return project;
    }

    public boolean remove(final Project project) {
        projects.remove(project);
        removeFromProjectMap(project);
        logger.info(LOGGER_DELETE_PROJECT,project);
        return true;
    }

    public void clear() {
        projects.clear();
        projectMap.clear();
        logger.info("Все проекты удалены");
    }

    public Project findByIndex(final int index, final Long userId) {
        if (userId == null) return projects.get(index);
        final List<Project> filteredList = filterListByUserId(projects,userId);
        return filteredList.get(index);
    }

    public Project findByName(final String name, final Long userId, final int position) {
        final List<Project> projectList;
        if (userId == null) projectList = projectMap.get(name);
        else projectList = filterListByUserId(projectMap.get(name), userId);
        if (projectList == null || projectList.isEmpty()) return null;
        return projectList.get(position);
    }

    public List<Project> findListByName(final String name, final Long userId) {
        if (userId == null)  return projectMap.get(name);
        return filterListByUserId(projectMap.get(name), userId);
    }

    public Project findById(final Long id, final Long userId) {
        for (final Project project: projects){
            if ((project.getId().equals(id))
            && (userId == null || project.getUserId().equals(userId))) return project;
        }
        return null;
    }

    public Project removeByIndex(final int index, final Long userId) {
        final Project project = findByIndex(index, userId);
        if (project == null) return null;
        if (remove(project)){
            logger.info(LOGGER_DELETE_PROJECT,project);
            return project;
        }
        return null;
    }

    public List<Project> removeByName(final String name, final Long userId) {
        final List<Project> projectList = findListByName(name,userId);
        if (projectList == null || projectList.isEmpty()) return Collections.emptyList();
        projectMap.remove(name);
        for (Project project: projectList){
            logger.info(LOGGER_DELETE_PROJECT,project);
            projects.remove(project);
        }
        return projectList;
    }

    public Project removeById(final Long id, final Long userId) {
        final Project project = findById(id, userId);
        if (project == null) return null;
        projects.remove(project);
        removeFromProjectMap(project);
        return project;
    }

    public List<Project> findAll(final Long userId) {
        if (userId == null) return projects;
        else return filterListByUserId(projects, userId);
    }

    public Project assignUserIdByName(final String name, final Long userId, final Long currentUserId, final int position) {
        Project project = findByName(name,currentUserId, position);
        project.setUserId(userId);
        logger.info("Проекту [{}] назначен пользователь userId={}.", project, userId);
        return project;
    }

    public void sortList() {
        projects.sort(Comparator.comparing(Project::getName).thenComparing(Project::getDescription));
    }

    public List<Project> filterListByUserId(final List<Project> projectList, final Long userId) {
        final List<Project> filteredList = new ArrayList<>();
        for (Project project: projectList)
            if(project.getUserId().equals(userId)) filteredList.add(project);
        return filteredList;
    }

}
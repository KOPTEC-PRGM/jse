package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.entity.Project;
import com.nlmk.potapov.tm.exception.ProjectException;
import com.nlmk.potapov.tm.repository.ProjectRepository;
import com.nlmk.potapov.tm.util.ServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

import static com.nlmk.potapov.tm.constant.TerminalConst.EMPTY_PROJECT_LIST_EXCEPTION;
import static com.nlmk.potapov.tm.constant.TerminalConst.NULL_PROJECT_EXCEPTION;

public class ProjectService {

    private static final Logger logger = LogManager.getLogger(ProjectService.class);

    private static ProjectService instance;

    private final ProjectRepository projectRepository;

    private ProjectService() {
        this.projectRepository = ProjectRepository.getInstance();
    }

    public static ProjectService getInstance() {
        if (instance == null){
            instance = new ProjectService();
        }
        return instance;
    }

    public Project create(final String name) {
        ServiceUtil.traceLogger(logger, "create", new Object[]{name});
        if (name.isEmpty()) return null;
        return projectRepository.create(name);
    }

    public Project create(final String name, final String description, final Long userId) {
        ServiceUtil.traceLogger(logger, "create", new Object[]{name,description,userId});
        if (name.isEmpty()) return null;
        if (description.isEmpty()) return null;
        return projectRepository.create(name, description, userId);
    }

    public Project update(final Long id, final String name, final String description, final Long userId) {
        ServiceUtil.traceLogger(logger, "update", new Object[]{id,name,description,userId});
        if (id == null ) return null;
        if (name == null || name.isEmpty()) return null;
        if (description == null || description.isEmpty()) return null;
        return projectRepository.update(id, name, description, userId);
    }

    public boolean remove(Project project) {
        ServiceUtil.traceLogger(logger, "remove",new Object[]{});
        if (project == null) return false;
        return projectRepository.remove(project);
    }

    public void clear() {
        ServiceUtil.traceLogger(logger, "clear",new Object[]{});
        projectRepository.clear();
    }

    public Project findByIndex(final int index, final Long userId) throws ProjectException {
        ServiceUtil.traceLogger(logger, "findByIndex",new Object[]{index,userId});
        if (index < 0 || index > projectRepository.size() -1){
            throw new ProjectException("Project does not exist");
        }
        return throwExceptionIfNull(projectRepository.findByIndex(index,userId));
    }

    public Project findByName(final String name, final Long userId, final int position) throws ProjectException {
        ServiceUtil.traceLogger(logger, "findByName",new Object[]{name,userId,position});
        if (name == null || name.isEmpty()) return null;
        return throwExceptionIfNull(projectRepository.findByName(name, userId, position));
    }

    public List<Project> findListByName(String name, Long userId) throws ProjectException {
        ServiceUtil.traceLogger(logger, "findListByName",new Object[]{name,userId});
        if (name.isEmpty()) return Collections.emptyList();
        return throwExceptionIfEmpty(projectRepository.findListByName(name, userId));
    }

    public Project findById(final Long id, final Long userId) throws ProjectException {
        ServiceUtil.traceLogger(logger, "findById",new Object[]{id,userId});
        if (id == null ) return null;
        return throwExceptionIfNull(projectRepository.findById(id, userId));
    }

    public Project removeByIndex(final int index, final Long userId) throws ProjectException {
        ServiceUtil.traceLogger(logger, "removeByIndex",new Object[]{index,userId});
        if (index < 0 || index > projectRepository.size() -1) return null;
        return throwExceptionIfNull(projectRepository.removeByIndex(index, userId));
    }

    public List<Project> removeByName(String name, Long userId) throws ProjectException {
        ServiceUtil.traceLogger(logger, "removeByName",new Object[]{name,userId});
        if (name == null || name.isEmpty()) return Collections.emptyList();
        return throwExceptionIfEmpty(projectRepository.removeByName(name, userId));
    }

    public Project removeById(final Long id, final Long userId) throws ProjectException {
        ServiceUtil.traceLogger(logger, "removeById",new Object[]{id,userId});
        if (id == null ) return null;
        return throwExceptionIfNull(projectRepository.removeById(id, userId));
    }

    public Project assignUserIdByName(final String name, final Long userId, final Long currentUserId, final int position) throws ProjectException {
        ServiceUtil.traceLogger(logger, "assignUserIdByName", new Object[]{name,userId,currentUserId,position});
        if (name == null || name.isEmpty()) return null;
        if (userId == null ) return null;
        return throwExceptionIfNull(projectRepository.assignUserIdByName(name, userId, currentUserId, position));
    }

    public List<Project> findAll(final Long userId) throws ProjectException {
        ServiceUtil.traceLogger(logger, "findAll", new Object[]{userId});
        return throwExceptionIfEmpty(projectRepository.findAll(userId));
    }

    public void sortList() {
        ServiceUtil.traceLogger(logger, "sortList", new Object[]{});
        projectRepository.sortList();
    }

    private Project throwExceptionIfNull(final Project project) throws ProjectException {
        if (project == null){
            throw new ProjectException(NULL_PROJECT_EXCEPTION);
        }
        return project;
    }

    private List<Project> throwExceptionIfEmpty(final List<Project> projects) throws ProjectException {
        if (projects == null || projects.isEmpty()){
            throw new ProjectException(EMPTY_PROJECT_LIST_EXCEPTION);
        }
        return projects;
    }

}
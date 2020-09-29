package com.nlmk.potapov.tm.listener;

import com.nlmk.potapov.tm.entity.Project;
import com.nlmk.potapov.tm.entity.Task;
import com.nlmk.potapov.tm.enumerated.RoleType;
import com.nlmk.potapov.tm.exception.ProjectException;
import com.nlmk.potapov.tm.exception.TaskException;
import com.nlmk.potapov.tm.service.ProjectService;
import com.nlmk.potapov.tm.service.ProjectTaskService;

import java.util.List;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class ProjectListener implements Listener{

    private final ProjectService projectService;

    private final ProjectTaskService projectTaskService;

    public ProjectListener() {
        this.projectService = ProjectService.getInstance();
        this.projectTaskService = ProjectTaskService.getInstance();
    }
    @Override
    public int callMethod(String method, Long userId, RoleType roleType) throws TaskException, ProjectException {
        if (userId == null) return 0;
        switch (method) {
            case PROJECT_CREATE:
                return createProject(userId);
            case PROJECT_CLEAR:
                return clearProject(roleType);
            case PROJECT_LIST:
                return listProject(userId, roleType);
            case PROJECT_LIST_WITH_TASK:
                return listProjectWithTasks(userId);
            case PROJECT_VIEW_BY_INDEX:
                return viewProjectByIndex(userId, roleType);
            case PROJECT_VIEW_BY_ID:
                return viewProjectById(userId, roleType);
            case PROJECT_REMOVE_BY_INDEX:
                return removeProjectByIndex(userId, roleType);
            case PROJECT_REMOVE_BY_NAME:
                return removeProjectByName(userId, roleType);
            case PROJECT_REMOVE_BY_ID:
                return removeProjectById(userId, roleType);
            case PROJECT_UPDATE_BY_INDEX:
                return updateProjectByIndex(userId, roleType);
            case PROJECT_UPDATE_BY_ID:
                return updateProjectById(userId, roleType);
            case PROJECT_ASSIGN_BY_NAME_TO_USER_BY_ID:
                return assignProjectByNameToUserById(userId, roleType);
            case PROJECT_REMOVE_WITH_TASKS_BY_ID:
                return removeProjectWithTasksById(roleType);
        }
        return 0;
    }

    public int viewProjectList(Project project) {
        if (project == null) return -1;
        System.out.println("[Просмотр проекта]");
        System.out.println("ID: " + project.getId());
        System.out.println("Имя: " + project.getName());
        System.out.println("Описание: " + project.getDescription());
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int viewProjectByIndex(final Long userId, final RoleType roleType) throws ProjectException {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.print("Введите номер проекта: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Project project = projectService.findByIndex(index, currentUserId);
        return viewProjectList(project);
    }

    public int viewProjectById(final Long userId, final RoleType roleType) throws ProjectException {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.print("Введите ID проекта: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Project project = projectService.findById(id, currentUserId);
        return viewProjectList(project);
    }

    public int removeProjectByIndex(final Long userId, final RoleType roleType) throws ProjectException {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление проекта по номеру]");
        System.out.print("Введите номер проекта: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Project project = projectService.removeByIndex(index, currentUserId);
        System.out.println("[Готово. Проект (ID = " + project.getId() + ") удален]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeProjectByName(final Long userId, final RoleType roleType) throws ProjectException {
        List<Project> removedList = null;
        Project removedProject = null;
        Long currentUserId = null;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление проекта(ов) по имени]");
        System.out.print("Введите название проекта(ов): ");
        final String name = scanner.nextLine();
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        List<Project> projectList = projectService.findListByName(name, currentUserId);
        if (projectList.size() == 1) removedProject = projectList.get(0);
        else {
            System.out.println("Найденые проекты: ");
            viewProjectList(projectList);
            System.out.print("Введите номер проекта, который хотите удалить (0 - удалить все): ");
            final int index = getIndexFromScanner();
            if (index == -1) removedList = projectService.removeByName(name,currentUserId);
            else removedProject = projectList.get(index);
        }
        final boolean result = projectService.remove(removedProject);
        if (!result && removedList == null) System.out.println("[Ошибка удаления проекта(ов)]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeProjectById(final Long userId, final RoleType roleType) throws ProjectException {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление проекта по ID]");
        System.out.print("Введите ID проекта: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Project project = projectService.removeById(id, currentUserId);
        System.out.println("[Готово. Проект (ID = " + project.getId() + ") удален]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int listProject(final Long userId, final RoleType roleType) throws ProjectException {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        projectService.sortList();
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Список проектов]");
        List<Project> projectList = projectService.findAll(currentUserId);
        viewProjectList(projectList);
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private void viewProjectList(List<Project> projects) {
        if (projects == null || projects.isEmpty()) return;
        int index = 1;
        for (final Project project: projects){
            System.out.println(INDENT+index + ". " + project.getId() + ": " + project.getName() + ": UserID - " + project.getUserId());
            index++;
        }
    }

    public int listProjectWithTasks(final Long userId) throws ProjectException, TaskException {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Список проектов с подзадачами]");
        List<Project> projectList;
        projectList = projectService.findAll(userId);
        viewProjectWithTasks(projectList, userId);
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private void viewProjectWithTasks(List<Project> projects, final Long userId) throws TaskException {
        if (projects == null || projects.isEmpty()) return;
        int index = 1;
        for (final Project project: projects){
            int indexTask = 1;
            System.out.println(INDENT+index + ". " + project.getId() + ": " + project.getName());
            for (final Task task: projectTaskService.viewTasksFromProject(project.getId(), userId)){
                System.out.println(INDENT+INDENT+indexTask + ". " + task.getId() + ": " + task.getName());
                indexTask++;
            }
            System.out.println();
            index++;
        }
    }

    public int clearProject(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Очистка списка проектов]");
        projectService.clear();
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int createProject(final Long userId) {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Создание проекта]");
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание проекта: ");
        final String description = scanner.nextLine();
        final Long id = projectService.create(name, description, userId).getId();
        System.out.println("[Готово. Проект \""+name+"\" добавлен в список. Id = "+id+"]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateProjectByIndex(final Long userId, final RoleType roleType) throws ProjectException {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление проекта по номеру]");
        System.out.print("Введите номер проекта: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Project project = projectService.findByIndex(index, currentUserId);
        if (project == null) {
            System.out.println("[Ошибка обновления проекта. Проект не найден]");
            System.out.println(BLOCK_SEPARATOR);
            return 0;
        }
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание проекта: ");
        final String description = scanner.nextLine();
        final Long id = projectService.update(project.getId(), name, description, currentUserId).getId();
        System.out.println("[Готово. Проект " + (index + 1) + " (ID = " + id + ") обновлен]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateProjectById(final Long userId, final RoleType roleType) throws ProjectException {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление проекта по ID]");
        System.out.print("Введите ID проекта: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Project project = projectService.findById(id, currentUserId);
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание проекта: ");
        final String description = scanner.nextLine();
        projectService.update(project.getId(), name, description, currentUserId);
        System.out.println("[Готово. Проект (ID = " + id + ") обновлен]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }


    public int assignProjectByNameToUserById(final Long userId, final RoleType roleType) throws ProjectException {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Назначение пользователя по ID к проекту по имени]");
        System.out.print("Введите ID пользователя: ");
        final Long id = getIdFromScanner();
        if (id == null) {
            System.out.println("[Ошибка. Введен пустой ID]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        if (name == null) {
            System.out.println("[Ошибка. Введено пустое название проекта]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final Project project;
        List<Project> projectList = projectService.findListByName(name,currentUserId);
        if (projectList.size() == 1) project = projectService.assignUserIdByName(name,id,currentUserId,0);
        else {
            System.out.println("Найденые проекты: ");
            viewProjectList(projectList);
            System.out.print("Введите номер проекта, которому хотите назначить пользователя: ");
            int i = getIndexFromScanner();
            project = projectService.assignUserIdByName(name,id,currentUserId,i);
        }
        if (project == null) System.out.println("[Ошибка. Не удалось назначить пользователя.]");
        else System.out.println("[Готово. Проекту \"" + project.getName() + "\" назначен пользователь(ID = \"" + id + "\").]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeProjectWithTasksById(final RoleType roleType) throws ProjectException {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Удаление проекта с задачами по ID]");
        System.out.print("Введите ID проекта: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Project project = projectTaskService.removeProjectWithTasks(id, null);
        System.out.println("[Готово. Проект (ID = " + project.getId() + ") удален]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private Long getIdFromScanner(){
        if (!scanner.hasNextLong()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return null;
        }
        return Long.valueOf(scanner.nextLine());
    }

    private int getIndexFromScanner(){
        if (!scanner.hasNextInt()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        return Integer.parseInt(scanner.nextLine()) -1;
    }

}

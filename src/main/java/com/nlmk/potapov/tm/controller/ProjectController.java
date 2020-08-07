package com.nlmk.potapov.tm.controller;

import com.nlmk.potapov.tm.entity.Project;
import com.nlmk.potapov.tm.entity.Task;
import com.nlmk.potapov.tm.service.ProjectService;
import com.nlmk.potapov.tm.service.ProjectTaskService;

import java.util.List;

import static com.nlmk.potapov.tm.constant.TerminalConst.BLOCK_SEPARATOR;
import static com.nlmk.potapov.tm.constant.TerminalConst.INDENT;

public class ProjectController extends AbstractController {

    private final ProjectService projectService;

    private final ProjectTaskService projectTaskService;

    public ProjectController(ProjectService projectService, ProjectTaskService projectTaskService) {
        this.projectService = projectService;
        this.projectTaskService = projectTaskService;
    }

    public int viewProject(Project project) {
        if (project == null) return -1;
        System.out.println("[Просмотр проекта]");
        System.out.println("ID: " + project.getId());
        System.out.println("Имя: " + project.getName());
        System.out.println("Описание: " + project.getDescription());
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int viewProjectByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.print("Введите номер проекта: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Project project = projectService.findByIndex(index);
        if (project == null) System.out.println("[Проект не найден]");
        return viewProject(project);
    }

    public int viewProjectById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.print("Введите ID проекта: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Project project = projectService.findById(id);
        if (project == null) System.out.println("[Проект не найден]");
        return viewProject(project);
    }

    public int removeProjectByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление проекта по номеру]");
        System.out.print("Введите номер проекта: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Project project = projectService.removeByIndex(index);
        if (project == null) System.out.println("[Ошибка удаления проекта. Проект не найден.]");
        else System.out.println("[Готово. Проект (ID = " + project.getId() + ") удален]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeProjectByName() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление проекта по имени]");
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        final Project project = projectService.removeByName(name);
        if (project == null) System.out.println("[Ошибка удаления проекта]");
        else System.out.println("[Готово. Проект " + project.getName() + " удален]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeProjectById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление проекта по ID]");
        System.out.print("Введите ID проекта: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Project project = projectService.removeById(id);
        if (project == null) System.out.println("[Ошибка удаления проекта. Проект не найден.]");
        else System.out.println("[Готово. Проект (ID = " + project.getId() + ") удален]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int listProject(final Long userId) {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Список проектов]");
        List<Project> projectList;
        if (userId == null) projectList = projectService.findAll();
        else projectList = projectService.findAllByUserId(userId);
        viewProject(projectList);
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private void viewProject(List<Project> projects) {
        if (projects == null || projects.isEmpty()) return;
        int index = 1;
        for (final Project project: projects){
            System.out.println(INDENT+index + ". " + project.getId() + ": " + project.getName());
            index++;
        }
    }

    public int listProjectWithTasks(final Long userId) {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Список проектов с подзадачами]");
        List<Project> projectList;
        if (userId == null) projectList = projectService.findAll();
        else projectList = projectService.findAllByUserId(userId);
        viewProjectWithTasks(projectList);
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private void viewProjectWithTasks(List<Project> projects) {
        if (projects == null || projects.isEmpty()) return;
        int index = 1;
        for (final Project project: projects){
            int indexTask = 1;
            System.out.println(INDENT+index + ". " + project.getId() + ": " + project.getName());
                for (final Task task: projectTaskService.viewTasksFromProject(project.getId())){
                    System.out.println(INDENT+INDENT+indexTask + ". " + task.getId() + ": " + task.getName());
                    indexTask++;
                }
            System.out.println();
            index++;
        }
    }

    public int clearProject() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Очистка списка проектов]");
        projectService.clear();
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int createProject() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Создание проекта]");
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание проекта: ");
        final String description = scanner.nextLine();
        final Long id = projectService.create(name, description).getId();
        System.out.println("[Готово. Проект \""+name+"\" добавлен в список. Id = "+id+"]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateProjectByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление проекта по номеру]");
        System.out.print("Введите номер проекта: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Project project = projectService.findByIndex(index);
        if (project == null) {
            System.out.println("[Ошибка обновления проекта. Проект не найден]");
            System.out.println(BLOCK_SEPARATOR);
            return 0;
        }
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание проекта: ");
        final String description = scanner.nextLine();
        final Long id = projectService.update(project.getId(), name, description).getId();
        System.out.println("[Готово. Проект " + (index + 1) + " (ID = " + id + ") обновлен]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateProjectById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление проекта по ID]");
        System.out.print("Введите ID проекта: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Project project = projectService.findById(id);
        if (project == null) {
            System.out.println("[Ошибка обновления проекта. Проект не найден]");
            System.out.println(BLOCK_SEPARATOR);
            return 0;
        }
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание проекта: ");
        final String description = scanner.nextLine();
        projectService.update(project.getId(), name, description);
        System.out.println("[Готово. Проект (ID = " + id + ") обновлен]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeProjectWithTasksById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление проекта с задачами по ID]");
        System.out.print("Введите ID проекта: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Project project = projectTaskService.removeProjectWithTasks(id);
        if (project == null) System.out.println("[Ошибка удаления проекта. Проект (ID = " + id + ") не найден.]");
        else System.out.println("[Готово. Проект (ID = " + project.getId() + ") удален]");
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

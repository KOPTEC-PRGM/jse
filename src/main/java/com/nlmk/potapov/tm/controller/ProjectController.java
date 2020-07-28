package com.nlmk.potapov.tm.controller;

import com.nlmk.potapov.tm.dao.ProjectDAO;
import com.nlmk.potapov.tm.entity.Project;

import static com.nlmk.potapov.tm.constant.TerminalConst.BLOCK_SEPARATOR;

public class ProjectController extends AbstractController {

    private final ProjectDAO projectDAO;

    public ProjectController(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
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
        if (!scanner.hasNextInt()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final int index = Integer.parseInt(scanner.nextLine()) -1;
        final Project project = projectDAO.findByIndex(index);
        if (project == null) System.out.println("[Проект не найден]");
        return viewProject(project);
    }

    public int viewProjectById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.print("Введите ID проекта: ");
        if (!scanner.hasNextLong()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final Long id = Long.valueOf(scanner.nextLine());
        final Project project = projectDAO.findById(id);
        if (project == null) System.out.println("[Проект не найден]");
        return viewProject(project);
    }

    public int removeProjectByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление проекта по номеру]");
        System.out.print("Введите номер проекта: ");
        if (!scanner.hasNextInt()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка удаления проекта. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final int index = Integer.parseInt(scanner.nextLine()) -1;
        final Project project = projectDAO.removeByIndex(index);
        if (project == null) System.out.println("[Ошибка удаления проекта. Проект не найден.]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeProjectByName() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление проекта по имени]");
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        final Project project = projectDAO.removeByName(name);
        if (project == null) System.out.println("[Ошибка удаления проекта]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeProjectById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление проекта по ID]");
        System.out.print("Введите ID проекта: ");
        if (!scanner.hasNextLong()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка удаления проекта. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final Long id = Long.valueOf(scanner.nextLine());
        final Project project = projectDAO.removeById(id);
        if (project == null) System.out.println("[Ошибка удаления проекта. Проект не найден.]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int listProject() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Список проектов]");
        int index = 1;
        for (final Project project: projectDAO.findAll()){
            System.out.println(index + ". " + project.getId() + ": " + project.getName());
            index++;
        }
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int clearProject() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Очистка списка проектов]");
        projectDAO.clear();
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
        final Long id = projectDAO.create(name, description).getId();
        System.out.println("[Готово. Проект \""+name+"\" добавлен в список. Id = "+id+"]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateProjectByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление проекта по номеру]");
        System.out.print("Введите номер проекта: ");
        if (!scanner.hasNextInt()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final int index = Integer.parseInt(scanner.nextLine()) -1;
        final Project project = projectDAO.findByIndex(index);
        if (project == null) {
            System.out.println("[Ошибка обновления проекта. Проект не найден]");
            System.out.println(BLOCK_SEPARATOR);
            return 0;
        }
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание проекта: ");
        final String description = scanner.nextLine();
        final Long id = projectDAO.update(project.getId(), name, description).getId();
        System.out.println("[Готово. Проект " + (index + 1) + " (ID = " + id + ") обновлен]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateProjectById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление проекта по ID]");
        System.out.print("Введите ID проекта: ");
        if (!scanner.hasNextLong()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final Long id = Long.valueOf(scanner.nextLine());
        final Project project = projectDAO.findById(id);
        if (project == null) {
            System.out.println("[Ошибка обновления проекта. Проект не найден]");
            System.out.println(BLOCK_SEPARATOR);
            return 0;
        }
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание проекта: ");
        final String description = scanner.nextLine();
        projectDAO.update(project.getId(), name, description);
        System.out.println("[Готово. Проект (ID = " + id + ") обновлен]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

}

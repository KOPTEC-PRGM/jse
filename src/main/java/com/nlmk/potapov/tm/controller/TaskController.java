package com.nlmk.potapov.tm.controller;

import com.nlmk.potapov.tm.entity.Task;
import com.nlmk.potapov.tm.service.ProjectTaskService;
import com.nlmk.potapov.tm.service.TaskService;

import java.util.List;

import static com.nlmk.potapov.tm.constant.TerminalConst.BLOCK_SEPARATOR;
import static com.nlmk.potapov.tm.constant.TerminalConst.INDENT;

public class TaskController extends AbstractController{

    private final TaskService taskService;

    private final ProjectTaskService projectTaskService;

    public TaskController(TaskService taskService, ProjectTaskService projectTaskService) {
        this.taskService = taskService;
        this.projectTaskService = projectTaskService;
    }

    public int viewTask(Task task) {
        if (task == null) return -1;
        System.out.println("[Просмотр задачи]");
        System.out.println("ID: " + task.getId());
        System.out.println("Имя: " + task.getName());
        System.out.println("Описание: " + task.getDescription());
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int viewTaskByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.print("Введите номер задачи: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Task task = taskService.findByIndex(index);
        if (task == null) System.out.println("[Задача не найдена]");
        return viewTask(task);
    }

    public int viewTaskById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.print("Введите ID задачи: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Task task = taskService.findById(id);
        if (task == null) System.out.println("[Задача не найдена]");
        return viewTask(task);
    }

    public int removeTaskByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление задачи по номеру]");
        System.out.print("Введите номер задачи: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Task task = taskService.removeByIndex(index);
        if (task == null) System.out.println("[Ошибка удаления задачи. Задача не найдена.]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeTaskByName() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление задачи по имени]");
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        final Task task = taskService.removeByName(name);
        if (task == null) System.out.println("[Ошибка удаления задачи]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeTaskById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление задачи по ID]");
        System.out.print("Введите ID задачи: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Task task = taskService.removeById(id);
        if (task == null) System.out.println("[Ошибка удаления задачи. Задача не найдена.]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int listTask() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Список задач]");
        viewTask(taskService.findAll());
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public void viewTask(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) return;
        int index = 1;
        for (final Task task: tasks){
            System.out.println(INDENT+index + ". " + task.getId() + ": " + task.getName());
            index++;
        }
    }

    public int clearTask() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Очистка списка задач]");
        taskService.clear();
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int createTask() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Создание задачи]");
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание задачи: ");
        final String description = scanner.nextLine();
        final Long id = taskService.create(name, description).getId();
        System.out.println("[Готово. Задача \""+name+"\" добавлена в список. ID = "+id+"]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateTaskByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление задачи по номеру]");
        System.out.print("Введите номер задачи: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Task task = taskService.findByIndex(index);
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание задачи: ");
        final String description = scanner.nextLine();
        final Long id = taskService.update(task.getId(), name, description).getId();
        System.out.println("[Готово. Задача " + (index + 1) + " (ID = " + id + ") обновлена]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateTaskById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление задачи по ID]");
        System.out.print("Введите ID задачи: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Task task = taskService.findById(id);
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание задачи: ");
        final String description = scanner.nextLine();
        taskService.update(task.getId(), name, description);
        System.out.println("[Готово. Задача (ID = " + id + ") обновлена]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int listTasksByProjectId(){
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Список задач в проекте]");
        System.out.print("Введите ID проекта: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        viewTask(projectTaskService.viewTasksFromProject(id));
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int addTaskToProjectByIds(){
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Добавление задачи в проект]");
        System.out.print("Введите ID проекта: ");
        final Long projectId = getIdFromScanner();
        if (projectId == null) return -1;
        System.out.print("Введите ID задачи: ");
        final Long taskId = getIdFromScanner();
        if (taskId == null) return -1;
        projectTaskService.addTaskToProject(projectId,taskId);
        System.out.println("[Готово. Задача (ID = " + taskId + ") добавлена в проект (ID = " + projectId + ")]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeTaskFromProjectByIds(){
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление задачи из проекта]");
        System.out.print("Введите ID проекта: ");
        final Long projectId = getIdFromScanner();
        if (projectId == null) return -1;
        System.out.print("Введите ID задачи: ");
        final Long taskId = getIdFromScanner();
        if (taskId == null) return -1;
        projectTaskService.removeTaskFromProject(projectId,taskId);
        System.out.println("[Готово. Задача (ID = " + taskId + ") удалена из проекта (ID = " + projectId + ")]");
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

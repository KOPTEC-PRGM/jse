package com.nlmk.potapov.tm.controller;

import com.nlmk.potapov.tm.entity.Task;
import com.nlmk.potapov.tm.enumerated.RoleType;
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
        System.out.println(INDENT+"ID: " + task.getId());
        System.out.println(INDENT+"Имя: " + task.getName());
        System.out.println(INDENT+"Описание: " + task.getDescription());
        System.out.println(INDENT+task.hashCode());
        System.out.println("[Готово]");
        return 0;
    }

    public int viewTaskByIndex(final Long userId, final RoleType roleType) {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.print("Введите номер задачи: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Task task = taskService.findByIndex(index, currentUserId);
        if (task == null) System.out.println("[Задача не найдена]");
        final int result = viewTask(task);
        System.out.println(BLOCK_SEPARATOR);
        return result;
    }

    public int viewTaskById(final Long userId, final RoleType roleType) {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.print("Введите ID задачи: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Task task = taskService.findById(id, currentUserId);
        if (task == null) System.out.println("[Задача не найдена]");
        final int result = viewTask(task);
        System.out.println(BLOCK_SEPARATOR);
        return result;
    }

    public int viewTaskByName(final Long userId, final RoleType roleType) {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        if (name == null) return -1;
        final List<Task> taskList = taskService.findListByName(name, currentUserId);
        System.out.println("Количество найденых по запросу задач: "+taskList.size());
        System.out.println();
        for (Task task: taskList){
            viewTask(task);
        }
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeTaskByIndex(final Long userId, final RoleType roleType) {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление задачи по номеру]");
        System.out.print("Введите номер задачи: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Task task = taskService.removeByIndex(index, currentUserId);
        if (task == null) System.out.println("[Ошибка удаления задачи. Задача не найдена.]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeTaskByName(final Long userId, final RoleType roleType) {
        List<Task> removedList = null;
        Task removedTask = null;
        Long currentUserId = null;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление задач(и) по имени]");
        System.out.print("Введите название задач(и): ");
        final String name = scanner.nextLine();
        if (!roleType.equals(RoleType.ADMIN))currentUserId = userId;
        List<Task> taskList = taskService.findListByName(name, currentUserId);
        if (taskList.size() == 0) System.out.println("[Ошибка. Ни одной задачи не найдено]");
        if (taskList.size() == 1) removedTask = taskService.removeByName(name,currentUserId,0);
        if (taskList.size() > 1) {
            System.out.println("Найденые задачи: ");
            viewTaskList(taskList);
            System.out.print("Введите номер задачи, которую хотите удалить (0 - удалить все): ");
            int i = getIndexFromScanner();
            if (i == -1) removedList = taskService.removeByName(name);
            else removedTask = taskService.removeByName(name,currentUserId,i);
        }
        if (removedTask == null && removedList == null) System.out.println("[Ошибка удаления задач(и)]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeTaskById(final Long userId, final RoleType roleType) {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление задачи по ID]");
        System.out.print("Введите ID задачи: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Task task = taskService.removeById(id, currentUserId);
        if (task == null) System.out.println("[Ошибка удаления задачи. Задача не найдена.]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int listTask(final Long userId, final RoleType roleType) {
        taskService.sortList();
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Список задач]");
        List<Task> taskList;
        if (userId == null) taskList = null;
        else if (roleType.equals(RoleType.ADMIN)) taskList = taskService.findAll();
        else taskList = taskService.findAllByUserId(userId);
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        viewTaskList(taskList);
        return 0;
    }

    private void viewTaskList(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) return;
        int index = 1;
        for (final Task task: tasks){
            System.out.println(INDENT+index + ". " + task.getId() + ": " + task.getName() + ": UserID - " + task.getUserId());
            index++;
        }
    }

    public int clearTask(final RoleType roleType) {
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Очистка списка задач]");
        taskService.clear();
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int createTask(final Long userId) {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Создание задачи]");
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание задачи: ");
        final String description = scanner.nextLine();
        final Long id = taskService.create(name, description,userId).getId();
        System.out.println("[Готово. Задача \""+name+"\" добавлена в список. ID = "+id+"]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateTaskByIndex(final Long userId, final RoleType roleType) {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление задачи по номеру]");
        System.out.print("Введите номер задачи: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final Task task = taskService.findByIndex(index, currentUserId);
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание задачи: ");
        final String description = scanner.nextLine();
        final Long id = taskService.update(task.getId(), name, description, currentUserId).getId();
        System.out.println("[Готово. Задача " + (index + 1) + " (ID = " + id + ") обновлена]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateTaskById(final Long userId, final RoleType roleType) {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление задачи по ID]");
        System.out.print("Введите ID задачи: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final Task task = taskService.findById(id, currentUserId);
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание задачи: ");
        final String description = scanner.nextLine();
        taskService.update(task.getId(), name, description, currentUserId);
        System.out.println("[Готово. Задача (ID = " + id + ") обновлена]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int assignTaskByNameToUserById(final Long userId, final RoleType roleType) {
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Назначение пользователя по ID к задаче по имени]");
        System.out.print("Введите ID пользователя: ");
        final Long id = getIdFromScanner();
        if (id == null) {
            System.out.println("[Ошибка. Введен пустой ID]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        if (name == null) {
            System.out.println("[Ошибка. Введено пустое название задачи]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final Task task;
        List<Task> taskList = taskService.findListByName(name, currentUserId);
        if (taskList.size() == 0) {
            System.out.println("[Ошибка. Ни одной задачи не найдено]");
            task = null;
        }
        else if (taskList.size() == 1) task =taskService.assignUserIdByName(name, id, currentUserId, 0);
        else {
            System.out.println("Найденые задачи: ");
            viewTaskList(taskList);
            System.out.print("Введите номер задачи, к которой хотите назначить пользователя: ");
            int i = getIndexFromScanner();
            task = taskService.assignUserIdByName(name, id, currentUserId, i);
        }
        if (task == null) System.out.println("[Ошибка. Не удалось назначить пользователя.]");
        else System.out.println("[Готово. Задаче \"" + task.getName() + "\" назначен пользователь(ID = \"" + id + "\").]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int listTasksByProjectId(final Long userId, final RoleType roleType){
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Список задач в проекте]");
        System.out.print("Введите ID проекта: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        viewTaskList(projectTaskService.viewTasksFromProject(id, currentUserId));
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int addTaskToProjectByIds(final Long userId, final RoleType roleType){
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Добавление задачи в проект]");
        System.out.print("Введите ID проекта: ");
        final Long projectId = getIdFromScanner();
        if (projectId == null) return -1;
        System.out.print("Введите ID задачи: ");
        final Long taskId = getIdFromScanner();
        if (taskId == null) return -1;
        Task task = projectTaskService.addTaskToProject(projectId, taskId, currentUserId);
        if (task == null) System.out.println("[Ошибка добавления задачи в проект]");
        else System.out.println("[Готово. Задача (ID = " + task.getId() + ") добавлена в проект (ID = " + projectId + ")]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeTaskFromProjectByIds(final Long userId, final RoleType roleType){
        Long currentUserId = null;
        if (!roleType.equals(RoleType.ADMIN)) currentUserId = userId;
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление задачи из проекта]");
        System.out.print("Введите ID проекта: ");
        final Long projectId = getIdFromScanner();
        if (projectId == null) return -1;
        System.out.print("Введите ID задачи: ");
        final Long taskId = getIdFromScanner();
        if (taskId == null) return -1;
        Task task = projectTaskService.removeTaskFromProject(projectId, taskId, currentUserId);
        if (task == null) System.out.println("[Ошибка удаления задачи из проекта]");
        else System.out.println("[Готово. Задача (ID = " + task.getId() + ") удалена из проекта (ID = " + projectId + ")]");
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
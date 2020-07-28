package com.nlmk.potapov.tm.controller;

import com.nlmk.potapov.tm.dao.TaskDAO;
import com.nlmk.potapov.tm.entity.Task;

import static com.nlmk.potapov.tm.constant.TerminalConst.BLOCK_SEPARATOR;

public class TaskController extends AbstractController{

    private final TaskDAO taskDAO;

    public TaskController(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
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
        if (!scanner.hasNextInt()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final int index = Integer.parseInt(scanner.nextLine()) -1;
        final Task task = taskDAO.findByIndex(index);
        if (task == null) System.out.println("[Задача не найдена]");
        return viewTask(task);
    }

    public int viewTaskById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.print("Введите ID задачи: ");
        if (!scanner.hasNextLong()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final Long id = Long.valueOf(scanner.nextLine());
        final Task task = taskDAO.findById(id);
        if (task == null) System.out.println("[Задача не найдена]");
        return viewTask(task);
    }

    public int removeTaskByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление задачи по номеру]");
        System.out.print("Введите номер задачи: ");
        if (!scanner.hasNextInt()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка удаления задачи. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final int index = Integer.parseInt(scanner.nextLine()) -1;
        final Task task = taskDAO.removeByIndex(index);
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
        final Task task = taskDAO.removeByName(name);
        if (task == null) System.out.println("[Ошибка удаления задачи]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int removeTaskById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление задачи по ID]");
        System.out.print("Введите ID задачи: ");
        if (!scanner.hasNextLong()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка удаления задачи. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final Long id = Long.valueOf(scanner.nextLine());
        final Task task = taskDAO.removeById(id);
        if (task == null) System.out.println("[Ошибка удаления задачи. Задача не найдена.]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int listTask() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Список задач]");
        int index = 1;
        for (final Task task: taskDAO.findAll()){
            System.out.println(index + ". " + task.getId() + ": " + task.getName());
            index++;
        }
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int clearTask() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Очистка списка задач]");
        taskDAO.clear();
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
        final Long id = taskDAO.create(name, description).getId();
        System.out.println("[Готово. Задача \""+name+"\" добавлена в список. ID = "+id+"]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateTaskByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление задачи по номеру]");
        System.out.print("Введите номер задачи: ");
        if (!scanner.hasNextInt()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка обновления задачи. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final int index = Integer.parseInt(scanner.nextLine()) -1;
        final Task task = taskDAO.findByIndex(index);
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание задачи: ");
        final String description = scanner.nextLine();
        final Long id = taskDAO.update(task.getId(), name, description).getId();
        System.out.println("[Готово. Задача " + (index + 1) + " (ID = " + id + ") обновлена]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateTaskById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление задачи по ID]");
        System.out.print("Введите ID задачи: ");
        if (!scanner.hasNextLong()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка обновления задачи. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final Long id = Long.valueOf(scanner.nextLine());
        final Task task = taskDAO.findById(id);
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        System.out.print("Введите описание задачи: ");
        final String description = scanner.nextLine();
        taskDAO.update(task.getId(), name, description);
        System.out.println("[Готово. Задача (ID = " + id + ") обновлена]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

}

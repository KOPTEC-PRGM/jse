package com.nlmk.potapov.tm;

import com.nlmk.potapov.tm.dao.ProjectDAO;
import com.nlmk.potapov.tm.dao.TaskDAO;
import com.nlmk.potapov.tm.entity.Project;
import com.nlmk.potapov.tm.entity.Task;

import java.util.Scanner;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class App {

    private static final ProjectDAO projectDAO = new ProjectDAO();

    private static final TaskDAO taskDAO = new TaskDAO();

    private static final Scanner scanner = new Scanner(System.in);

    static {
        projectDAO.create("Демонстрационный проект №1");
        projectDAO.create("Демонстрационный проект №2");
        taskDAO.create("Демонстрационное задание №1");
        taskDAO.create("Демонстрационное задание №2");
    }

    public static void main(final String[] args) {
        displayWelcome();
        run(args);
        String command = "";
        int result = 0;
        while (!EXIT.equals(command)){
            System.out.print(INPUT_MESSAGE);
            command = scanner.nextLine();
            result = run(command);
        }
        System.exit(result);
    }

    private static void run(final String[] args) {
        if (args == null) return;
        if (args.length < 1) return;
        final String param = args[0];
        final int result = run(param);
        if (EXIT.equals(param)) System.exit(result);
    }

    private static int run(final String param) {
        if (param == null) return -1;
        if (param.isBlank()) return 0;
        switch (param) {
            case HELP: return displayHelp();
            case VERSION: return displayVersion();
            case ABOUT: return displayAbout();
            case EXIT: return displayExit();

            case PROJECT_CREATE: return createProject();
            case PROJECT_CLEAR: return clearProject();
            case PROJECT_LIST: return listProject();
            case PROJECT_VIEW: return viewProjectByIndex();
            case PROJECT_REMOVE_BY_INDEX: return removeProjectByIndex();
            case PROJECT_REMOVE_BY_NAME: return removeProjectByName();
            case PROJECT_REMOVE_BY_ID: return removeProjectById();
            case PROJECT_UPDATE_BY_INDEX: return updateProjectByIndex();

            case TASK_CREATE: return createTask();
            case TASK_CLEAR: return clearTask();
            case TASK_LIST: return listTask();
            case TASK_VIEW: return viewTaskByIndex();
            case TASK_REMOVE_BY_INDEX: return removeTaskByIndex();
            case TASK_REMOVE_BY_NAME: return removeTaskByName();
            case TASK_REMOVE_BY_ID: return removeTaskById();
            case TASK_UPDATE_BY_INDEX: return updateTaskByIndex();

            default: return displayError();
        }
    }

    private static int viewTask(Task task) {
        if (task == null) return -1;
        System.out.println("[Просмотр задачи]");
        System.out.println("ID: " + task.getId());
        System.out.println("Имя: " + task.getName());
        System.out.println("Описание: " + task.getDescription());
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private static int viewTaskByIndex() {
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

    private static int removeTaskByIndex() {
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

    private static int removeTaskByName() {
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

    private static int removeTaskById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление задачи по ID]");
        System.out.print("Введите ID задачи: ");
        if (!scanner.hasNextLong()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка удаления задачи. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final Long id = Long.parseLong(scanner.nextLine());
        final Task task = taskDAO.removeById(id);
        if (task == null) System.out.println("[Ошибка удаления задачи. Задача не найдена.]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private static int listTask() {
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

    private static int clearTask() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Очистка списка задач]");
        taskDAO.clear();
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private static int createTask() {
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

    private static int updateTaskByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление задачи]");
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

    private static int viewProject(Project project) {
        if (project == null) return -1;
        System.out.println("[Просмотр проекта]");
        System.out.println("ID: " + project.getId());
        System.out.println("Имя: " + project.getName());
        System.out.println("Описание: " + project.getDescription());
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private static int viewProjectByIndex() {
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

    private static int removeProjectByIndex() {
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

    private static int removeProjectByName() {
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

    private static int removeProjectById() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление проекта по ID]");
        System.out.print("Введите ID проекта: ");
        if (!scanner.hasNextLong()) {
            final String error_value = scanner.nextLine();
            System.out.println("[Ошибка удаления проекта. Введено некорректное значение: \"" + error_value + "\"]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final Long id = Long.parseLong(scanner.nextLine());
        final Project project = projectDAO.removeById(id);
        if (project == null) System.out.println("[Ошибка удаления проекта. Проект не найден.]");
        else System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private static int listProject() {
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

    private static int clearProject() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Очистка списка проектов]");
        projectDAO.clear();
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private static int createProject() {
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

    private static int updateProjectByIndex() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Обновление проекта]");
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

    private static int displayHelp() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"version - Информация о версии приложения.");
        System.out.println(INDENT+"about - Информация о разработчике.");
        System.out.println(INDENT+"help - Вывод списка терминальных команд.");
        System.out.println(INDENT+"exit - Выход из приложения.");
        System.out.println();
        System.out.println(INDENT+"project-create - Создание проекта.");
        System.out.println(INDENT+"project-list - Список проектов.");
        System.out.println(INDENT+"project-clear - Очистка списка проектов.");
        System.out.println();
        System.out.println(INDENT+"task-create - Создание задачи.");
        System.out.println(INDENT+"task-list - Список задач.");
        System.out.println(INDENT+"task-clear - Очистка списка задач.");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private static int displayVersion() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"1.0.0");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private static int displayAbout() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"Потапов Вадим");
        System.out.println(INDENT+"potapov_vs@nlmk.com");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private static int displayError() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"Ошибка! Неизвестная терминальная команда...");
        System.out.println(INDENT+"Повторите ввод.");
        System.out.println(BLOCK_SEPARATOR);
        return -1;
    }

    private static int displayExit() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"Завершение работы приложения...");
        System.out.println(INDENT+"Всего хорошего.");
        return 0;
    }

    private static void displayWelcome() {
        System.out.println("** ДОБРО ПОЖАЛОВАТЬ В TASK MANAGER **");
        System.out.println(BLOCK_SEPARATOR);
    }

}

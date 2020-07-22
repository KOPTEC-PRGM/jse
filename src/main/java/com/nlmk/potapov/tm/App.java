package com.nlmk.potapov.tm;

import com.nlmk.potapov.tm.dao.ProjectDAO;
import com.nlmk.potapov.tm.dao.TaskDAO;

import java.util.Scanner;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class App {

    private static final ProjectDAO projectDAO = new ProjectDAO();

    private static final TaskDAO taskDAO = new TaskDAO();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(final String[] args) {
        displayWelcome();
        run(args);
        String command = "";
        int result = 0;
        while (!EXIT.equals(command) & result == 0){
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

            case TASK_CREATE: return createTask();
            case TASK_CLEAR: return clearTask();
            case TASK_LIST: return listTask();

            default: return displayError();
        }
    }

    private static int listTask() {
        System.out.println("[Список задач]");
        System.out.println(taskDAO.findAll());
        System.out.println("[Готово]");
        return 0;
    }

    private static int clearTask() {
        System.out.println("[Очистка списка задач]");
        taskDAO.clear();
        System.out.println("[Готово]");
        return 0;
    }

    private static int createTask() {
        System.out.println("[Создание задачи]");
        System.out.print("Введите название задачи: ");
        final String name = scanner.nextLine();
        final Long id = taskDAO.create(name).getId();
        System.out.println("[Готово. Задача \""+name+"\" добавлена в список. Id = "+id+"]");
        return 0;
    }

    private static int listProject() {
        System.out.println("[Список проектов]");
        System.out.println(projectDAO.findAll());
        System.out.println("[Готово]");
        return 0;
    }

    private static int clearProject() {
        System.out.println("[Очистка списка проектов]");
        projectDAO.clear();
        System.out.println("[Готово]");
        return 0;
    }

    private static int createProject() {
        System.out.println("[Создание проекта]");
        System.out.print("Введите название проекта: ");
        final String name = scanner.nextLine();
        final Long id = projectDAO.create(name).getId();
        System.out.println("[Готово. Проект \""+name+"\" добавлен в список. Id = "+id+"]");
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
        System.out.println(INDENT+"Приложение завершает работу...");
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

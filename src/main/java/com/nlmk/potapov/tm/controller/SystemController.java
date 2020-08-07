package com.nlmk.potapov.tm.controller;

import com.nlmk.potapov.tm.Application;
import com.nlmk.potapov.tm.entity.User;
import com.nlmk.potapov.tm.service.UserService;

import static com.nlmk.potapov.tm.constant.TerminalConst.BLOCK_SEPARATOR;
import static com.nlmk.potapov.tm.constant.TerminalConst.INDENT;

public class SystemController extends AbstractController{

    private final Application app;

    private final UserService userService;

    public SystemController(Application app, UserService userService) {
        this.app = app;
        this.userService = userService;
    }

    public int displayHelp() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"version - Информация о версии приложения.");
        System.out.println(INDENT+"about - Информация о разработчике.");
        System.out.println(INDENT+"help - Вывод списка терминальных команд.");
        System.out.println(INDENT+"exit - Выход из приложения.");
        System.out.println(INDENT+"login - Аутентификация пользователя.");
        System.out.println(INDENT+"logout - Завершение сеанса пользователя.");
        System.out.println();
        System.out.println(INDENT+"project-create - Создание проекта.");
        System.out.println(INDENT+"project-list - Список проектов.");
        System.out.println(INDENT+"project-list-with-task - Список проектов с подзадачами.");
        System.out.println(INDENT+"project-clear - Очистка списка проектов.");
        System.out.println(INDENT+"project-view-by-index - Просмотр проекта по номеру.");
        System.out.println(INDENT+"project-view-by-id - Просмотр проекта по ID.");
        System.out.println(INDENT+"project-remove-by-name - Удаление проекта по имени.");
        System.out.println(INDENT+"project-remove-by-id - Удаление проекта по ID.");
        System.out.println(INDENT+"project-remove-by-index - Удаление проекта по номеру.");
        System.out.println(INDENT+"project-remove-with-tasks-by-id - Удаление проекта с его задачами по ID проекта.");
        System.out.println(INDENT+"project-update-by-index - Обновление проекта по номеру.");
        System.out.println(INDENT+"project-update-by-id - Обновление проекта по ID.");
        System.out.println();
        System.out.println(INDENT+"task-create - Создание задачи.");
        System.out.println(INDENT+"task-list - Список задач.");
        System.out.println(INDENT+"task-clear - Очистка списка задач.");
        System.out.println(INDENT+"task-view-by-index - Просмотр задачи по номеру.");
        System.out.println(INDENT+"task-view-by-id - Просмотр задачи по ID.");
        System.out.println(INDENT+"task-remove-by-name - Удаление задачи по имени.");
        System.out.println(INDENT+"task-remove-by-id - Удаление задачи по ID.");
        System.out.println(INDENT+"task-remove-by-index - Удаление задачи по номеру.");
        System.out.println(INDENT+"task-update-by-index - Обновление задачи по номеру.");
        System.out.println(INDENT+"task-update-by-id - Обновление задачи по ID.");
        System.out.println(INDENT+"task-list-by-project-id - Список задач в проекте по ID проекта.");
        System.out.println(INDENT+"task-add-to-project-by-ids - Добавление задачи в проект по ID проекта и ID задачи");
        System.out.println(INDENT+"task-remove-from-project-by-ids - Удаление задачи из проекта по ID проекта и ID задачи.");
        System.out.println();
        System.out.println(INDENT+"user-create - Добавление нового пользователя.");
        System.out.println(INDENT+"user-list - Список пользователей.");
        System.out.println(INDENT+"user-clear - Очистка списка пользователей.");
        System.out.println(INDENT+"user-view-by-index - Просмтор пользователя по номеру.");
        System.out.println(INDENT+"user-view-by-login - Просмтор пользователя по логину.");
        System.out.println(INDENT+"user-remove-by-index - Удаление пользователя по номеру.");
        System.out.println(INDENT+"user-remove-by-login - Удаление пользователя по логину.");
        System.out.println(INDENT+"user-update-by-index - Обновление пользователя по номеру.");
        System.out.println(INDENT+"user-update-by-login - Обновление пользователя по логину.");
        System.out.println(INDENT+"user-update-password - Обновление пароля пользователя.");
        System.out.println(INDENT+"user-view-current - Просмотр данных текущего пользователя.");
        System.out.println(INDENT+"user-update-current - Обновление данных текущего пользователя.");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int displayVersion() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"1.0.0");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int displayAbout() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"Потапов Вадим");
        System.out.println(INDENT+"potapov_vs@nlmk.com");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int displayError() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"Ошибка! Неизвестная терминальная команда...");
        System.out.println(INDENT+"Повторите ввод.");
        System.out.println(BLOCK_SEPARATOR);
        return -1;
    }

    public int displayExit() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"Завершение работы приложения...");
        System.out.println(INDENT+"Всего хорошего.");
        return 0;
    }

    public void displayWelcome() {
        System.out.println("** ДОБРО ПОЖАЛОВАТЬ В TASK MANAGER **");
        System.out.println(BLOCK_SEPARATOR);
    }

    public int login() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Вход в систему]");
        System.out.print("Введите логин: ");
        final String login = scanner.nextLine();
        if (login == null || login.isEmpty()) {
            System.out.println("[Ошибка. Введен пустой логин]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.print("Введите пароль: ");
        final String password = scanner.nextLine();
        if (password == null || password.isEmpty()) {
            System.out.println("[Ошибка. Введен пустой пароль]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final User user = userService.findByLogin(login);
        if (user == null) return -1;
        if (userService.checkPassword(login,password)){
            app.setCurrentUserId(user.getId());
            app.setCurrentUserName(user.getLogin());
        }
        System.out.println("[Готово. Добро пожаловать \"" + user.getLogin() + "\"]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int logout() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Выход из системы]");
        app.setCurrentUserId(null);
        app.setCurrentUserName(null);
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

}
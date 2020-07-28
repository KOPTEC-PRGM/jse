package com.nlmk.potapov.tm.controller;

import static com.nlmk.potapov.tm.constant.TerminalConst.BLOCK_SEPARATOR;
import static com.nlmk.potapov.tm.constant.TerminalConst.INDENT;

public class SystemController {

    public int displayHelp() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"version - Информация о версии приложения.");
        System.out.println(INDENT+"about - Информация о разработчике.");
        System.out.println(INDENT+"help - Вывод списка терминальных команд.");
        System.out.println(INDENT+"exit - Выход из приложения.");
        System.out.println();
        System.out.println(INDENT+"project-create - Создание проекта.");
        System.out.println(INDENT+"project-list - Список проектов.");
        System.out.println(INDENT+"project-clear - Очистка списка проектов.");
        System.out.println(INDENT+"project-view-by-index - Просмотр проекта по номеру.");
        System.out.println(INDENT+"project-view-by-id - Просмотр проекта по ID.");
        System.out.println(INDENT+"project-remove-by-name - Удаление проекта по имени.");
        System.out.println(INDENT+"project-remove-by-id - Удаление проекта по ID.");
        System.out.println(INDENT+"project-remove-by-index - Удаление проекта по номеру.");
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

}

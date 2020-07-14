package com.nlmk.potapov.tm;

import java.util.Scanner;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class App {

    public static void main(final String[] args) {
        displayWelcome();
        run(args);
        final Scanner scanner = new Scanner(System.in);
        String command = "";
        while (!EXIT.equals(command)){
            System.out.print("Task Manager> ");
            command = scanner.nextLine();
            run(command);
        }
    }

    private static void run(final String[] args) {
        if (args == null) return;
        if (args.length < 1) return;
        final String param = args[0];
        run(param);
    }

    private static int run(final String param) {
        switch (param) {
            case HELP: return displayHelp();
            case VERSION: return displayVersion();
            case ABOUT: return displayAbout();
            case EXIT: return displayExit();
            default: return displayError();
        }
    }

    private static int displayHelp() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"version - Информация о версии приложения.");
        System.out.println(INDENT+"about - Информация о разработчике.");
        System.out.println(INDENT+"help - Вывод списка терминальных команд.");
        System.out.println(INDENT+"exit - Выход из приложения.");
        System.out.println(BLOCK_SEPARATOR);
        return(0);
    }

    private static int displayVersion() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"1.0.0");
        System.out.println(BLOCK_SEPARATOR);
        return(0);
    }

    private static int displayAbout() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"Потапов Вадим");
        System.out.println(INDENT+"potapov_vs@nlmk.com");
        System.out.println(BLOCK_SEPARATOR);
        return(0);
    }

    private static int displayError() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"Ошибка! Неизвестная терминальная команда...");
        System.out.println(BLOCK_SEPARATOR);
        return(-1);
    }

    private static int displayExit() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println(INDENT+"Завершение работы приложения...");
        System.out.println(INDENT+"Всего хорошего.");
        return(0);
    }

    private static void displayWelcome() {
        System.out.println("** ДОБРО ПОЖАЛОВАТЬ В TASK MANAGER **");
        System.out.println(BLOCK_SEPARATOR);
    }

}

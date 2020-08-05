package com.nlmk.potapov.tm.controller;

import com.nlmk.potapov.tm.entity.User;
import com.nlmk.potapov.tm.service.UserService;

import java.util.List;

import static com.nlmk.potapov.tm.constant.TerminalConst.BLOCK_SEPARATOR;
import static com.nlmk.potapov.tm.constant.TerminalConst.INDENT;

public class UserController extends AbstractController{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public int addUser() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Создание нового пользователя]");
        System.out.print("Введите логин: ");
        final String login = scanner.nextLine();
        if (userService.findByLogin(login) != null) {
            System.out.println("[Ошибка. Такой пользователь уже существует]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.print("Введите пароль: ");
        final String password = scanner.nextLine();
        System.out.print("Подтвердите пароль: ");
        final String checkPassword = scanner.nextLine();
        if (!password.equals(checkPassword)) {
            System.out.println("[Ошибка. Пароли не совпадают]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.print("Введите фамилию: ");
        final String lastName = scanner.nextLine();
        System.out.print("Введите имя: ");
        final String firstName = scanner.nextLine();
        System.out.print("Введите отчество: ");
        final String middleName = scanner.nextLine();
        User user = userService.create(login,password,firstName,middleName,lastName);
        if (user == null) {
            System.out.println("[Ошибка создания пользователя. Не все поля заполнены или заполнены некорректно]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Готово. Пользователь " + user.getLogin() + " создан]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int deleteUserByLogin() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Удаление пользователя]");
        System.out.print("Введите логин: ");
        final String login = scanner.nextLine();
        if (userService.findByLogin(login) == null) {
            System.out.println("[Ошибка. Такого пользователя не существует]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        User user = userService.removeByLogin(login);
        System.out.println("[Готово. Пользователь " + login + " удален]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int listUser() {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Список пользователей]");
        viewUser(userService.findAll());
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private void viewUser(List<User> users) {
        if (users == null || users.isEmpty()) return;
        int index = 1;
        for (final User user: users){
            System.out.println(INDENT+index + ". " + user.toString());
            index++;
        }
    }

}

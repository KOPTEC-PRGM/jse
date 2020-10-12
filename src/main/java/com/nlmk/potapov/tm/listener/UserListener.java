package com.nlmk.potapov.tm.listener;

import com.nlmk.potapov.tm.entity.User;
import com.nlmk.potapov.tm.enumerated.RoleType;
import com.nlmk.potapov.tm.service.UserService;

import java.util.List;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class UserListener implements Listener{

    private final UserService userService;

    public UserListener() {
        this.userService = UserService.getInstance();
    }

    @Override
    public int callCommand(String method, Long userId, RoleType roleType) {
        if (userId == null) return 0;
        switch (method) {
            case USER_CREATE:
                return addUser(roleType);
            case USER_CLEAR:
                return clearUser(roleType);
            case USER_LIST:
                return listUser(roleType);
            case USER_VIEW_BY_ID:
                return viewUserById(roleType);
            case USER_VIEW_BY_INDEX:
                return viewUserByIndex(roleType);
            case USER_VIEW_BY_LOGIN:
                return viewUserByLogin(roleType);
            case USER_REMOVE_BY_ID:
                return deleteUserById(roleType);
            case USER_REMOVE_BY_INDEX:
                return deleteUserByIndex(roleType);
            case USER_REMOVE_BY_LOGIN:
                return deleteUserByLogin(roleType);
            case USER_UPDATE_BY_ID:
                return updateUserById(roleType);
            case USER_UPDATE_BY_INDEX:
                return updateUserByIndex(roleType);
            case USER_UPDATE_BY_LOGIN:
                return updateUserByLogin(roleType);
            case USER_UPDATE_PASSWORD:
                return changeUserPassword(userId);
            case USER_VIEW_CURRENT:
                return viewCurrent(userId);
            case USER_UPDATE_CURRENT:
                return changeCurrent(userId);
            case USER_SAVE:
                return saveUsers(roleType);

            default:
                return 0;
        }
    }

    public int addUser(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
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
        final String commitPassword = scanner.nextLine();
        if (!password.equals(commitPassword)) {
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
        System.out.println("[Готово. Пользователь \"" + user.getLogin() + "\" создан]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int deleteUserById(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Удаление пользователя по ID]");
        System.out.print("Введите ID пользователя: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        if (userService.findById(id) == null) {
            System.out.println("[Ошибка. Пользователь не найден]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        User user = userService.removeById(id);
        System.out.println("[Готово. Пользователь \"" + user.getLogin() + "\" удален]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int deleteUserByIndex(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Удаление пользователя по номеру]");
        System.out.print("Введите номер пользователя: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        if (userService.findByIndex(index) == null) {
            System.out.println("[Ошибка. Пользователь не найден]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        User user = userService.removeByIndex(index);
        System.out.println("[Готово. Пользователь \"" + user.getLogin() + "\" удален]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int deleteUserByLogin(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Удаление пользователя по логину]");
        System.out.print("Введите логин пользователя: ");
        final String login = scanner.nextLine();
        if (userService.findByLogin(login) == null) {
            System.out.println("[Ошибка. Пользователь не найден]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        User user = userService.removeByLogin(login);
        System.out.println("[Готово. Пользователь \"" + user.getLogin() + "\" удален]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private int viewUser(final User user) {
        if (user == null) {
            System.out.println("[Ошибка. Пользователь не найден]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Просмотр пользователя]");
        System.out.println(INDENT+"Логин: " + user.getLogin());
        System.out.println(INDENT+"Имя: " + user.getFirstName());
        System.out.println(INDENT+"Отчество: " + user.getMiddleName());
        System.out.println(INDENT+"Фамилия: " + user.getLastName());
        System.out.println(INDENT+"Роль: " + user.getRoleType());
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int viewUserByLogin(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Просмтор пользователя по логину]");
        System.out.print("Введите логин пользователя: ");
        final String login = scanner.nextLine();
        if (login == null || login.isEmpty()) {
            System.out.println("[Ошибка. Введен пустой логин]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        User user = userService.findByLogin(login);
        return viewUser(user);
    }

    public int viewUserByIndex(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Просмтор пользователя по номеру]");
        System.out.print("Введите номер пользователя: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        User user = userService.findByIndex(index);
        return viewUser(user);
    }

    public int viewUserById(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Просмтор пользователя по ID]");
        System.out.print("Введите ID пользователя: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        User user = userService.findById(id);
        return viewUser(user);
    }

    public int listUser(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Список пользователей]");
        viewUserList(userService.findAll());
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private void viewUserList(List<User> users) {
        if (users == null || users.isEmpty()) return;
        int index = 1;
        for (final User user: users){
            System.out.println(INDENT+index + ". " + user.toString());
            index++;
        }
    }

    public int clearUser(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Очистка списка пользователей]");
        userService.clear();
        System.out.println("[Готово]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateUser(final User user) {
        if (user == null) {
            System.out.println("[Ошибка. Пользователь не найден]");
            System.out.println(BLOCK_SEPARATOR);
            return 0;
        }
        System.out.print("Введите имя: ");
        final String firstName = scanner.nextLine();
        System.out.print("Введите отчество: ");
        final String middleName = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        final String lastName = scanner.nextLine();
        final User updatedUser = userService.update(user.getLogin(), firstName, middleName, lastName);
        if (updatedUser == null) {
            System.out.println("[Ошибка. Поля заполнены некорректно]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Готово. Пользователь \"" + updatedUser.getLogin() + "\" обновлен]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    private int updateUserPassword(final User user, final User currentUser) {
        if (!currentUser.equals(user) && !currentUser.getRoleType().equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для смены пароля другого пользователя]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        if (currentUser.equals(user)) System.out.print("Введите пароль: ");
        else System.out.print("Введите пароль Администратора: ");
        final String password = scanner.nextLine();
        if (currentUser.equals(user)) {
            if (!userService.checkPassword(user.getLogin(), password)) {
                System.out.println("[Ошибка. Неверный логин или пароль]");
                System.out.println(BLOCK_SEPARATOR);
                return -1;
            }
        }
        else {
            if (!userService.checkPassword(currentUser.getLogin(), password)) {
                System.out.println("[Ошибка. Неверный пароль]");
                System.out.println(BLOCK_SEPARATOR);
                return -1;
            }
        }
        System.out.print("Введите новый пароль: ");
        final String newPassword = scanner.nextLine();
        System.out.print("Подтвердите новый пароль: ");
        final String commitNewPassword = scanner.nextLine();
        if (!newPassword.equals(commitNewPassword)) {
            System.out.println("[Ошибка. Пароли не совпадают]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        userService.update(user.getLogin(),newPassword);
        System.out.println("[Готово. Пароль пользователя \"" + user.getLogin() + "\" изменен]");
        System.out.println(BLOCK_SEPARATOR);
        return 0;
    }

    public int updateUserById(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Обновление пользователя по ID]");
        System.out.print("Введите ID пользователя: ");
        final Long id = getIdFromScanner();
        if (id == null) return -1;
        final User user = userService.findById(id);
        return updateUser(user);
    }

    public int updateUserByIndex(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Обновление пользователя по номеру]");
        System.out.print("Введите номер пользователя: ");
        final int index = getIndexFromScanner();
        if (index < 0) return -1;
        final User user = userService.findByIndex(index);
        return updateUser(user);
    }

    public int updateUserByLogin(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Обновление пользователя по логину]");
        System.out.print("Введите логин пользователя: ");
        final String login = scanner.nextLine();
        if (login == null || login.isEmpty()) {
            System.out.println("[Ошибка. Введен пустой логин]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final User user = userService.findByLogin(login);
        return updateUser(user);
    }

    public int changeUserPassword(final Long id) {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Смена пароля]");
        System.out.print("Введите логин пользователя: ");
        final String login = scanner.nextLine();
        if (login == null || login.isEmpty()) {
            System.out.println("[Ошибка. Введен пустой логин]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        final User user = userService.findByLogin(login);
        if (user == null) {
            System.out.println("[Ошибка. Пользователь не найден]");
            System.out.println(BLOCK_SEPARATOR);
            return 0;
        }
        User currentUser = userService.findById(id);
        return updateUserPassword(user, currentUser);
    }

    public int viewCurrent(final Long id) {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Информация пользователя]");
        User user = userService.findById(id);
        return viewUser(user);
    }

    public int changeCurrent(final Long id) {
        System.out.println(BLOCK_SEPARATOR);
        System.out.println("[Изменение данных пользователя]");
        User user = userService.findById(id);
        return updateUser(user);
    }

    public int saveUsers(final RoleType roleType) {
        System.out.println(BLOCK_SEPARATOR);
        if (!roleType.equals(RoleType.ADMIN)){
            System.out.println("[Ошибка. Не достаточно привелегий для выполнения данной команды]");
            System.out.println(BLOCK_SEPARATOR);
            return -1;
        }
        System.out.println("[Сохранение репозитория пользователей]");
        System.out.println("Выберите формат файла:");
        System.out.println(INDENT+"1 - JSON");
        System.out.println(INDENT+"2 - XML");
        System.out.print("Формат файла:");
        int i = getIndexFromScanner();
        if (i == 0) {
            System.out.print("Введите название JSON-файла:");
            String filename = scanner.nextLine();
            userService.saveToJson(filename);
        }
        else if (i == 1) {
            System.out.print("Введите название XML-файла:");
            String filename = scanner.nextLine();
            userService.saveToXml(filename);
        }
        else{
            System.out.println("Неверный формат файла");
            System.out.println(BLOCK_SEPARATOR);
            return 0;
        }
        System.out.println("[Готово. Пользователи сохранены]");
        System.out.println(BLOCK_SEPARATOR);
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

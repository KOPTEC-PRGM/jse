package com.nlmk.potapov.tm.repository;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.nlmk.potapov.tm.entity.User;
import com.nlmk.potapov.tm.enumerated.RoleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final Logger logger = LogManager.getLogger(UserRepository.class);

    private static UserRepository instance;

    private final List<User> users = new ArrayList<>();

    private User currentAppUser;

    public User getCurrentAppUser() {
        return currentAppUser;
    }

    public void setCurrentAppUser(User currentAppUser) {
        this.currentAppUser = currentAppUser;
    }

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public User create(final String login, final String password, final String firstName, final String middleName, final String lastName) {
        final User user = new User(login, password, firstName, middleName, lastName);
        users.add(user);
        return user;
    }

    public User create(final String login, final String password, final String firstName, final String middleName, final String lastName, final RoleType roleType) {
        final User user = new User(login, password, firstName, middleName, lastName, roleType);
        users.add(user);
        return user;
    }

    public User update(final String login, final String password, final String firstName, final String middleName, final String lastName, final RoleType roleType) {
        final User user = findByLogin(login);
        if (user == null) return null;
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setRoleType(roleType);
        return user;
    }

    public User update(final String login, final String firstName, final String middleName, final String lastName) {
        final User user = findByLogin(login);
        if (user == null) return null;
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        return user;
    }

    public User update(final String login, final String password) {
        final User user = findByLogin(login);
        if (user == null) return null;
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }

    public void clear() {
        users.clear();
    }

    public User findByLogin(final String login) {
        for (final User user: users){
            if (user.getLogin().equals(login)) return user;
        }
        return null;
    }

    public User findById(final Long id) {
        for (final User user: users){
            if (user.getId().equals(id)) return user;
        }
        return null;
    }

    public User findByIndex(final int index) {
        return users.get(index);
    }

    public User removeByLogin(final String login) {
        final User user = findByLogin(login);
        if (user == null) return null;
        users.remove(user);
        return user;
    }

    public User removeByIndex(final int index) {
        final User user = findByIndex(index);
        if (user == null) return null;
        users.remove(user);
        return user;
    }

    public User removeById(final Long id) {
        final User user = findById(id);
        if (user == null) return null;
        users.remove(user);
        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public int size() {
        return users.size();
    }

    public void saveToJson(final String filePath) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            objectMapper.writeValue(new File(filePath),users);
        } catch (IOException e) {
            logger.error("ошибка записи объекта {} в файл {} : {}", users, filePath, e.getMessage());
        }
    }

    public void saveToXml(final String filePath) {
        final XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(new File(filePath),users);
        } catch (IOException e) {
            logger.error("ошибка записи объекта {} в файл {} : {}", users, filePath, e.getMessage());
        }
    }

}
package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.entity.User;
import com.nlmk.potapov.tm.enumerated.RoleType;
import com.nlmk.potapov.tm.repository.UserRepository;
import com.nlmk.potapov.tm.util.HashUtil;

import java.util.List;

public class UserService {

    final UserRepository userRepository;

    public UserService() {
        this.userRepository = UserRepository.getInstance();
    }

    public User create(final String login, final String password,
                       final String firstName, final String middleName, final String lastName
    ) {
        if (login == null || login.isEmpty()) return null;
        if (password == null || password.isEmpty()) return null;
        if (firstName == null || firstName.isEmpty()) return null;
        if (middleName == null || middleName.isEmpty()) return null;
        if (lastName == null || lastName.isEmpty()) return null;
        return userRepository.create(login, HashUtil.generateMD5(password), firstName, middleName, lastName);
    }

    public User create(final String login, final String password,
                       final String firstName, final String middleName, final String lastName,
                       final RoleType roleType
    ) {
        if (login == null || login.isEmpty()) return null;
        if (password == null || password.isEmpty()) return null;
        if (firstName == null || firstName.isEmpty()) return null;
        if (middleName == null || middleName.isEmpty()) return null;
        if (lastName == null || lastName.isEmpty()) return null;
        if (roleType == null) return null;
        return userRepository.create(login, HashUtil.generateMD5(password), firstName, middleName, lastName, roleType);
    }

    public User update(
            final String login, final String password
    ) {
        if (login == null || login.isEmpty()) return null;
        if (password == null || password.isEmpty()) return null;
        return userRepository.update(login, HashUtil.generateMD5(password));
    }

    public User update(
            final String login,
            final String firstName, final String middleName, final String lastName
    ) {
        if (login == null || login.isEmpty()) return null;
        if (firstName == null || firstName.isEmpty()) return null;
        if (middleName == null || middleName.isEmpty()) return null;
        if (lastName == null || lastName.isEmpty()) return null;
        return userRepository.update(login,  firstName, middleName, lastName);
    }

    public User update(
            final String login, final String password,
            final String firstName, final String middleName, final String lastName,
            final RoleType roleType
    ) {
        if (login == null || login.isEmpty()) return null;
        if (password == null || password.isEmpty()) return null;
        if (firstName == null || firstName.isEmpty()) return null;
        if (middleName == null || middleName.isEmpty()) return null;
        if (lastName == null || lastName.isEmpty()) return null;
        if (roleType == null) return null;
        return userRepository.update(login, HashUtil.generateMD5(password), firstName, middleName, lastName, roleType);
    }

    public void clear() {
        userRepository.clear();
    }

    public User findByLogin(final String login) {
        if (login == null || login.isEmpty()) return null;
        return userRepository.findByLogin(login);
    }

    public User findByIndex(final int index) {
        if (index < 0 || index > userRepository.size() -1) return null;
        return userRepository.findByIndex(index);
    }

    public User findById(final Long id) {
        if (id == null) return null;
        return userRepository.findById(id);
    }

    public User removeByLogin(final String login) {
        if (login == null || login.isEmpty()) return null;
        return userRepository.removeByLogin(login);
    }

    public User removeByIndex(final int index) {
        if (index < 0 || index > userRepository.size() -1) return null;
        return userRepository.removeByIndex(index);
    }

    public User removeById(final Long id) {
        if (id == null) return null;
        return userRepository.removeById(id);
    }

    public boolean checkPassword(final String login, final String password){
        if (login == null || login.isEmpty()) return false;
        if (password == null || password.isEmpty()) return false;
        User user = userRepository.findByLogin(login);
        return user.getPassword().equals(HashUtil.generateMD5(password));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
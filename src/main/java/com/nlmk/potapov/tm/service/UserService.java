package com.nlmk.potapov.tm.service;

import com.nlmk.potapov.tm.enumerated.RoleType;
import com.nlmk.potapov.tm.entity.User;
import com.nlmk.potapov.tm.repository.UserRepository;

import java.util.List;

public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(String login, String password, String firstName, String middleName, String lastName) {
        if (login == null || login.isEmpty()) return null;
        if (password == null || password.isEmpty()) return null;
        if (firstName == null || firstName.isEmpty()) return null;
        if (middleName == null || middleName.isEmpty()) return null;
        if (lastName == null || lastName.isEmpty()) return null;
        return userRepository.create(login, MD5(password), firstName, middleName, lastName);
    }

    public User create(String login, String password, String firstName, String middleName, String lastName, RoleType roleType) {
        if (login == null || login.isEmpty()) return null;
        if (password == null || password.isEmpty()) return null;
        if (firstName == null || firstName.isEmpty()) return null;
        if (middleName == null || middleName.isEmpty()) return null;
        if (lastName == null || lastName.isEmpty()) return null;
        if (roleType == null) return null;
        return userRepository.create(login, MD5(password), firstName, middleName, lastName, roleType);
    }

    public User update(String login, String password, String firstName, String middleName, String lastName, RoleType roleType) {
        if (login == null || login.isEmpty()) return null;
        if (password == null || password.isEmpty()) return null;
        if (firstName == null || firstName.isEmpty()) return null;
        if (middleName == null || middleName.isEmpty()) return null;
        if (lastName == null || lastName.isEmpty()) return null;
        if (roleType == null) return null;
        return userRepository.update(login, MD5(password), firstName, middleName, lastName, roleType);
    }

    public void clear() {
        userRepository.clear();
    }

    public User findByLogin(String login) {
        if (login == null || login.isEmpty()) return null;
        return userRepository.findByLogin(login);
    }

    public User removeByLogin(String login) {
        if (login == null || login.isEmpty()) return null;
        return userRepository.removeByLogin(login);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}

package com.nlmk.potapov.tm.entity;

import com.nlmk.potapov.tm.enumerated.RoleType;

public class User {

    private Long id = System.nanoTime();

    private String login;

    private String password;

    private String firstName = "";

    private String middleName = "";

    private String lastName = "";

    private RoleType roleType = RoleType.USER;

    public User() {
    }

    public User(String login, String password, String firstName, String middleName, String lastName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public User(String login, String password, String firstName, String middleName, String lastName, RoleType roleType) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.roleType = roleType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        return id + ": " + login + " - " + firstName + ' ' + middleName + ' ' + lastName + " - " + roleType+ '.';
    }

}

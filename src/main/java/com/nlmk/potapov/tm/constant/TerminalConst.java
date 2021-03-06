package com.nlmk.potapov.tm.constant;

public final class TerminalConst {

    private TerminalConst() {
        throw new IllegalStateException("Utility class");
    }

    public static final int COMMAND_HISTORY_SIZE = 10;

    public static final String HELP = "help";
    public static final String VERSION = "version";
    public static final String ABOUT = "about";
    public static final String EXIT = "exit";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String COMMAND_HISTORY = "command-history";

    public static final String BLOCK_SEPARATOR = "-----------------------------------------------";
    public static final String INDENT = "   ";
    public static final String INPUT_MESSAGE = "Task Manager> ";

    public static final String NULL_TASK_EXCEPTION = "Задачи не существует";
    public static final String EMPTY_TASK_LIST_EXCEPTION = "Задач не существует";
    public static final String NULL_PROJECT_EXCEPTION = "Проекта не существует";
    public static final String EMPTY_PROJECT_LIST_EXCEPTION = "Проектов не существует";


    public static final String LOGGER_CREATE_TASK = "Создана задача [{}].";
    public static final String LOGGER_UPDATE_TASK = "Обновлена задача [{}].";
    public static final String LOGGER_DELETE_TASK = "Удалена задача [{}].";
    public static final String LOGGER_CREATE_PROJECT = "Создан проект [{}].";
    public static final String LOGGER_UPDATE_PROJECT = "Обновлен проект [{}].";
    public static final String LOGGER_DELETE_PROJECT = "Удален проект [{}].";


    public static final String PROJECT_CREATE = "project-create";
    public static final String PROJECT_CLEAR = "project-clear";
    public static final String PROJECT_LIST = "project-list";
    public static final String PROJECT_LIST_WITH_TASK = "project-list-with-task";
    public static final String PROJECT_VIEW_BY_INDEX = "project-view-by-index";
    public static final String PROJECT_VIEW_BY_ID = "project-view-by-id";
    public static final String PROJECT_REMOVE_BY_NAME = "project-remove-by-name";
    public static final String PROJECT_REMOVE_BY_ID = "project-remove-by-id";
    public static final String PROJECT_REMOVE_BY_INDEX = "project-remove-by-index";
    public static final String PROJECT_UPDATE_BY_INDEX = "project-update-by-index";
    public static final String PROJECT_UPDATE_BY_ID = "project-update-by-id";
    public static final String PROJECT_ASSIGN_BY_NAME_TO_USER_BY_ID = "project-assign-by-name-to-user-by-id";
    public static final String PROJECT_REMOVE_WITH_TASKS_BY_ID = "project-remove-with-tasks-by-id";
    public static final String PROJECT_SAVE = "project-save";
    public static final String PROJECT_LOAD = "project-load";

    public static final String TASK_CREATE = "task-create";
    public static final String TASK_CLEAR = "task-clear";
    public static final String TASK_LIST = "task-list";
    public static final String TASK_VIEW_BY_INDEX = "task-view-by-index";
    public static final String TASK_VIEW_BY_ID = "task-view-by-id";
    public static final String TASK_VIEW_BY_NAME = "task-view-by-name";
    public static final String TASK_REMOVE_BY_NAME = "task-remove-by-name";
    public static final String TASK_REMOVE_BY_ID = "task-remove-by-id";
    public static final String TASK_REMOVE_BY_INDEX = "task-remove-by-index";
    public static final String TASK_UPDATE_BY_INDEX = "task-update-by-index";
    public static final String TASK_UPDATE_BY_ID = "task-update-by-id";
    public static final String TASK_ASSIGN_BY_NAME_TO_USER_BY_ID = "task-assign-by-name-to-user-by-id";
    public static final String TASK_LIST_BY_PROJECT_ID = "task-list-by-project-id";
    public static final String TASK_ADD_TO_PROJECT_BY_IDS = "task-add-to-project-by-ids";
    public static final String TASK_REMOVE_FROM_PROJECT_BY_IDS = "task-remove-from-project-by-ids";
    public static final String TASK_SAVE = "task-save";
    public static final String TASK_LOAD = "task-load";

    public static final String USER_CREATE = "user-create";
    public static final String USER_CLEAR = "user-clear";
    public static final String USER_LIST = "user-list";
    public static final String USER_VIEW_BY_ID = "user-view-by-id";
    public static final String USER_VIEW_BY_INDEX = "user-view-by-index";
    public static final String USER_VIEW_BY_LOGIN = "user-view-by-login";
    public static final String USER_REMOVE_BY_ID = "user-remove-by-id";
    public static final String USER_REMOVE_BY_INDEX = "user-remove-by-index";
    public static final String USER_REMOVE_BY_LOGIN = "user-remove-by-login";
    public static final String USER_UPDATE_BY_ID = "user-update-by-id";
    public static final String USER_UPDATE_BY_INDEX = "user-update-by-index";
    public static final String USER_UPDATE_BY_LOGIN = "user-update-by-login";
    public static final String USER_UPDATE_PASSWORD = "user-update-password";
    public static final String USER_VIEW_CURRENT = "user-view-current";
    public static final String USER_UPDATE_CURRENT = "user-update-current";
    public static final String USER_SAVE = "user-save";
    public static final String USER_LOAD = "user-load";

}
package com.nlmk.potapov.tm;

import com.nlmk.potapov.tm.controller.*;
import com.nlmk.potapov.tm.enumerated.RoleType;
import com.nlmk.potapov.tm.repository.ProjectRepository;
import com.nlmk.potapov.tm.repository.TaskRepository;
import com.nlmk.potapov.tm.repository.UserRepository;
import com.nlmk.potapov.tm.service.ProjectService;
import com.nlmk.potapov.tm.service.ProjectTaskService;
import com.nlmk.potapov.tm.service.TaskService;
import com.nlmk.potapov.tm.service.UserService;

import java.util.Scanner;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class Application {

    private final ProjectRepository projectRepository = new ProjectRepository();

    private final TaskRepository taskRepository = new TaskRepository();

    private final UserRepository userRepository = new UserRepository();

    private final ProjectService projectService = new ProjectService(projectRepository);

    private final TaskService taskService = new TaskService(taskRepository);

    private final ProjectTaskService projectTaskService = new ProjectTaskService(projectRepository, taskRepository);

    private final UserService userService = new UserService(userRepository);

    private final ProjectController projectController = new ProjectController(projectService, projectTaskService);

    private final TaskController taskController = new TaskController(taskService, projectTaskService);

    private final UserController userController = new UserController(userService);

    private final SystemController systemController = new SystemController(this, userService);

    private Long currentUserId = null;

    private RoleType currentUserRole = null;

    {
        projectRepository.create("Демонстрационный проект №2");
        projectRepository.create("Демонстрационный проект №1");
        projectRepository.create("Демонстрационный проект №3");
        taskRepository.create("Демонстрационное задание №1");
        taskRepository.create("Демонстрационное задание №2");
        taskRepository.create("Демонстрационное задание №2");
        taskRepository.create("Демонстрационное задание №4");
        taskService.assignProjectId(taskService.findByName("Демонстрационное задание №1",0).getId(), projectService.findByName("Демонстрационный проект №1").getId());
        taskService.assignProjectId(taskService.findByName("Демонстрационное задание №2",0).getId(), projectService.findByName("Демонстрационный проект №2").getId());
        taskService.assignProjectId(taskService.findByName("Демонстрационное задание №2",1).getId(), projectService.findByName("Демонстрационный проект №2").getId());
        taskService.assignProjectId(taskService.findByName("Демонстрационное задание №4",0).getId(), projectService.findByName("Демонстрационный проект №3").getId());
        userService.create("Новый пользователь 1", "Надежный пароль","Иван", "Васильевич", "Бунша", RoleType.USER);
        userService.create("Главный администратор", "Очень надежный пароль","Семен", "Семенович", "Горбунков", RoleType.ADMIN);
        userService.create("1", "1","Семен", "Семенович", "Горбунков", RoleType.ADMIN);
        projectService.assignUserIdByName("Демонстрационный проект №1", userService.findByLogin("Главный администратор").getId());
        projectService.assignUserIdByName("Демонстрационный проект №2", userService.findByLogin("Главный администратор").getId());
        projectService.assignUserIdByName("Демонстрационный проект №3", userService.findByLogin("Новый пользователь 1").getId());
        taskService.assignUserIdByName("Демонстрационное задание №1", userService.findByLogin("Новый пользователь 1").getId(),0);
        taskService.assignUserIdByName("Демонстрационное задание №2", userService.findByLogin("Главный администратор").getId(),0);
        taskService.assignUserIdByName("Демонстрационное задание №2", userService.findByLogin("Новый пользователь 1").getId(),1);
        taskService.assignUserIdByName("Демонстрационное задание №4", userService.findByLogin("Новый пользователь 1").getId(),0);
    }

    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        Application application = new Application();
        application.systemController.displayWelcome();
        application.run(args);
        String command = "";
        int result = 0;
        while (!EXIT.equals(command)){
            System.out.print(INPUT_MESSAGE);
            command = scanner.nextLine();
            application.systemController.roundAdd(command);
            result = application.run(command);
        }
        System.exit(result);
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long userId) {
        this.currentUserId = userId;
    }

    public RoleType getCurrentUserRole() {
        return currentUserRole;
    }

    public void setCurrentUserRole(RoleType currentUserRole) {
        this.currentUserRole = currentUserRole;
    }

    public void run(final String[] args) {
        if (args == null) return;
        if (args.length < 1) return;
        final String param = args[0];
        systemController.roundAdd(param);
        final int result = run(param);
        if (EXIT.equals(param)) System.exit(result);
    }

    public int run(final String param) {
        if (param == null) return -1;
        if (param.isBlank()) return 0;
        switch (param) {
            case HELP: return systemController.displayHelp();
            case VERSION: return systemController.displayVersion();
            case ABOUT: return systemController.displayAbout();
            case EXIT: return systemController.displayExit();
            case LOGIN: return systemController.login();
            case LOGOUT: return systemController.logout();
            case COMMAND_HISTORY: return systemController.displayCommandHistory();
        }
        if (getCurrentUserId() == null) return systemController.displayError();
        switch (param) {
            case PROJECT_CREATE: return projectController.createProject(getCurrentUserId());
            case PROJECT_CLEAR: return projectController.clearProject();
            case PROJECT_LIST: return projectController.listProject(getCurrentUserId(), getCurrentUserRole());
            case PROJECT_LIST_WITH_TASK: return projectController.listProjectWithTasks(getCurrentUserId());
            case PROJECT_VIEW_BY_INDEX: return projectController.viewProjectByIndex();
            case PROJECT_VIEW_BY_ID: return projectController.viewProjectById();
            case PROJECT_REMOVE_BY_INDEX: return projectController.removeProjectByIndex();
            case PROJECT_REMOVE_BY_NAME: return projectController.removeProjectByName();
            case PROJECT_REMOVE_BY_ID: return projectController.removeProjectById();
            case PROJECT_UPDATE_BY_INDEX: return projectController.updateProjectByIndex();
            case PROJECT_UPDATE_BY_ID: return projectController.updateProjectById();
            case PROJECT_ASSIGN_BY_NAME_TO_USER_BY_ID: return projectController.assignProjectByNameToUserById();
            case PROJECT_REMOVE_WITH_TASKS_BY_ID: return projectController.removeProjectWithTasksById();

            case TASK_CREATE: return taskController.createTask(getCurrentUserId());
            case TASK_CLEAR: return taskController.clearTask();
            case TASK_LIST: return taskController.listTask(getCurrentUserId(), getCurrentUserRole());
            case TASK_VIEW_BY_INDEX: return taskController.viewTaskByIndex();
            case TASK_VIEW_BY_ID: return taskController.viewTaskById();
            case TASK_VIEW_BY_NAME: return taskController.viewTaskByName(getCurrentUserId(), getCurrentUserRole());
            case TASK_REMOVE_BY_INDEX: return taskController.removeTaskByIndex();
            case TASK_REMOVE_BY_NAME: return taskController.removeTaskByName(getCurrentUserId(), getCurrentUserRole());
            case TASK_REMOVE_BY_ID: return taskController.removeTaskById();
            case TASK_UPDATE_BY_INDEX: return taskController.updateTaskByIndex();
            case TASK_UPDATE_BY_ID: return taskController.updateTaskById();
            case TASK_ASSIGN_BY_NAME_TO_USER_BY_ID: return taskController.assignTaskByNameToUserById(getCurrentUserId(), getCurrentUserRole());
            case TASK_LIST_BY_PROJECT_ID: return taskController.listTasksByProjectId();
            case TASK_ADD_TO_PROJECT_BY_IDS: return taskController.addTaskToProjectByIds();
            case TASK_REMOVE_FROM_PROJECT_BY_IDS: return taskController.removeTaskFromProjectByIds();

            case USER_CREATE: return userController.addUser();
            case USER_CLEAR: return userController.clearUser();
            case USER_LIST: return userController.listUser();
            case USER_VIEW_BY_ID: return userController.viewUserById();
            case USER_VIEW_BY_INDEX: return userController.viewUserByIndex();
            case USER_VIEW_BY_LOGIN: return userController.viewUserByLogin();
            case USER_REMOVE_BY_ID: return userController.deleteUserById();
            case USER_REMOVE_BY_INDEX: return userController.deleteUserByIndex();
            case USER_REMOVE_BY_LOGIN: return userController.deleteUserByLogin();
            case USER_UPDATE_BY_ID: return userController.updateUserById();
            case USER_UPDATE_BY_INDEX: return userController.updateUserByIndex();
            case USER_UPDATE_BY_LOGIN: return userController.updateUserByLogin();
            case USER_UPDATE_PASSWORD: return userController.changeUserPassword(getCurrentUserId());
            case USER_VIEW_CURRENT: return userController.viewCurrent(getCurrentUserId());
            case USER_UPDATE_CURRENT: return userController.changeCurrent(getCurrentUserId());

            default: return systemController.displayError();
        }
    }

}
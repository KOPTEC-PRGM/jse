package com.nlmk.potapov.tm;

import com.nlmk.potapov.tm.controller.ProjectController;
import com.nlmk.potapov.tm.controller.SystemController;
import com.nlmk.potapov.tm.controller.TaskController;
import com.nlmk.potapov.tm.repository.ProjectRepository;
import com.nlmk.potapov.tm.repository.TaskRepository;
import com.nlmk.potapov.tm.service.ProjectService;
import com.nlmk.potapov.tm.service.TaskService;

import java.util.Scanner;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class Application {

    private final ProjectRepository projectRepository = new ProjectRepository();

    private final TaskRepository taskRepository = new TaskRepository();

    private final ProjectService projectService = new ProjectService(projectRepository);

    private final TaskService taskService = new TaskService(taskRepository);

    private final ProjectController projectController = new ProjectController(projectService);

    private final TaskController taskController = new TaskController(taskService);

    private final SystemController systemController = new SystemController();

    {
        projectRepository.create("Демонстрационный проект №1");
        projectRepository.create("Демонстрационный проект №2");
        taskRepository.create("Демонстрационное задание №1");
        taskRepository.create("Демонстрационное задание №2");
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
            result = application.run(command);
        }
        System.exit(result);
    }

    public void run(final String[] args) {
        if (args == null) return;
        if (args.length < 1) return;
        final String param = args[0];
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

            case PROJECT_CREATE: return projectController.createProject();
            case PROJECT_CLEAR: return projectController.clearProject();
            case PROJECT_LIST: return projectController.listProject();
            case PROJECT_VIEW_BY_INDEX: return projectController.viewProjectByIndex();
            case PROJECT_VIEW_BY_ID: return projectController.viewProjectById();
            case PROJECT_REMOVE_BY_INDEX: return projectController.removeProjectByIndex();
            case PROJECT_REMOVE_BY_NAME: return projectController.removeProjectByName();
            case PROJECT_REMOVE_BY_ID: return projectController.removeProjectById();
            case PROJECT_UPDATE_BY_INDEX: return projectController.updateProjectByIndex();
            case PROJECT_UPDATE_BY_ID: return projectController.updateProjectById();

            case TASK_CREATE: return taskController.createTask();
            case TASK_CLEAR: return taskController.clearTask();
            case TASK_LIST: return taskController.listTask();
            case TASK_VIEW_BY_INDEX: return taskController.viewTaskByIndex();
            case TASK_VIEW_BY_ID: return taskController.viewTaskById();
            case TASK_REMOVE_BY_INDEX: return taskController.removeTaskByIndex();
            case TASK_REMOVE_BY_NAME: return taskController.removeTaskByName();
            case TASK_REMOVE_BY_ID: return taskController.removeTaskById();
            case TASK_UPDATE_BY_INDEX: return taskController.updateTaskByIndex();
            case TASK_UPDATE_BY_ID: return taskController.updateTaskById();

            default: return systemController.displayError();
        }
    }

}

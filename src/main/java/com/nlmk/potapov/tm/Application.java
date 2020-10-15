package com.nlmk.potapov.tm;

import com.nlmk.potapov.tm.enumerated.RoleType;
import com.nlmk.potapov.tm.exception.ProjectException;
import com.nlmk.potapov.tm.exception.TaskException;
import com.nlmk.potapov.tm.listener.ProjectListener;
import com.nlmk.potapov.tm.listener.SystemListener;
import com.nlmk.potapov.tm.listener.TaskListener;
import com.nlmk.potapov.tm.listener.UserListener;
import com.nlmk.potapov.tm.publisher.PublisherImpl;
import com.nlmk.potapov.tm.repository.ProjectRepository;
import com.nlmk.potapov.tm.repository.TaskRepository;
import com.nlmk.potapov.tm.service.ProjectService;
import com.nlmk.potapov.tm.service.TaskService;
import com.nlmk.potapov.tm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    {
        final UserService userService = UserService.getInstance();
        final ProjectRepository projectRepository = ProjectRepository.getInstance();
        final TaskRepository taskRepository = TaskRepository.getInstance();
        final TaskService taskService = TaskService.getInstance();
        final ProjectService projectService = ProjectService.getInstance();

        userService.create("Новый пользователь 1", "Надежный пароль","Иван", "Васильевич", "Бунша", RoleType.USER);
        userService.create("Главный администратор", "Очень надежный пароль","Семен", "Семенович", "Горбунков", RoleType.ADMIN);
        projectRepository.create("Демонстрационный проект №2");
        projectRepository.create("Демонстрационный проект №1");
        projectRepository.create("Демонстрационный проект №3");
        taskRepository.create("Демонстрационное задание №1");
        taskRepository.create("Демонстрационное задание №2");
        taskRepository.create("Демонстрационное задание №2");
        taskRepository.create("Демонстрационное задание №4");
        try {
            taskService.assignProjectId(taskService.findByName("Демонстрационное задание №1",null,0).getId(), projectService.findByName("Демонстрационный проект №1",null,0).getId(), null);
            taskService.assignProjectId(taskService.findByName("Демонстрационное задание №2",null,0).getId(), projectService.findByName("Демонстрационный проект №2",null,0).getId(), null);
            taskService.assignProjectId(taskService.findByName("Демонстрационное задание №2",null,1).getId(), projectService.findByName("Демонстрационный проект №2",null,0).getId(), null);
            taskService.assignProjectId(taskService.findByName("Демонстрационное задание №4",null,0).getId(), projectService.findByName("Демонстрационный проект №3",null,0).getId(), null);
            projectService.assignUserIdByName("Демонстрационный проект №1", userService.findByLogin("Главный администратор").getId(),null,0);
            projectService.assignUserIdByName("Демонстрационный проект №2", userService.findByLogin("Главный администратор").getId(),null,0);
            projectService.assignUserIdByName("Демонстрационный проект №3", userService.findByLogin("Новый пользователь 1").getId(),null,0);
            taskService.assignUserIdByName("Демонстрационное задание №1", userService.findByLogin("Новый пользователь 1").getId(),null,0);
            taskService.assignUserIdByName("Демонстрационное задание №2", userService.findByLogin("Главный администратор").getId(),null,0);
            taskService.assignUserIdByName("Демонстрационное задание №2", userService.findByLogin("Новый пользователь 1").getId(),null,1);
            taskService.assignUserIdByName("Демонстрационное задание №4", userService.findByLogin("Новый пользователь 1").getId(),null,0);
        } catch (ProjectException | TaskException e) {
            logger.error(e);
        }
    }

    public static void main(final String[] args) {
        final PublisherImpl publisher = new PublisherImpl();
        Application application = new Application();
        String param = null;
        if (args.length > 0) param= args[0];

        publisher.addListener(new SystemListener());
        publisher.addListener(new UserListener());
        publisher.addListener(new TaskListener());
        publisher.addListener(new ProjectListener());
        try {
            publisher.runLoop(param);
        } catch (TaskException | ProjectException e) {
            System.out.println(e.getMessage());
                System.out.println(BLOCK_SEPARATOR);
                logger.error(e);
        }
    }

}
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
        UserService.getInstance().create("Новый пользователь 1", "Надежный пароль","Иван", "Васильевич", "Бунша", RoleType.USER);
        UserService.getInstance().create("Главный администратор", "Очень надежный пароль","Семен", "Семенович", "Горбунков", RoleType.ADMIN);
        UserService.getInstance().create("1", "1","Семен", "Семенович", "Горбунков", RoleType.ADMIN);
        ProjectRepository.getInstance().create("Демонстрационный проект №2");
        ProjectRepository.getInstance().create("Демонстрационный проект №1");
        ProjectRepository.getInstance().create("Демонстрационный проект №3");
        TaskRepository.getInstance().create("Демонстрационное задание №1");
        TaskRepository.getInstance().create("Демонстрационное задание №2");
        TaskRepository.getInstance().create("Демонстрационное задание №2");
        TaskRepository.getInstance().create("Демонстрационное задание №4");
        try {
            TaskService.getInstance().assignProjectId(TaskService.getInstance().findByName("Демонстрационное задание №1",null,0).getId(), ProjectService.getInstance().findByName("Демонстрационный проект №1",null,0).getId(), null);
            TaskService.getInstance().assignProjectId(TaskService.getInstance().findByName("Демонстрационное задание №2",null,0).getId(), ProjectService.getInstance().findByName("Демонстрационный проект №2",null,0).getId(), null);
            TaskService.getInstance().assignProjectId(TaskService.getInstance().findByName("Демонстрационное задание №2",null,1).getId(), ProjectService.getInstance().findByName("Демонстрационный проект №2",null,0).getId(), null);
            TaskService.getInstance().assignProjectId(TaskService.getInstance().findByName("Демонстрационное задание №4",null,0).getId(), ProjectService.getInstance().findByName("Демонстрационный проект №3",null,0).getId(), null);
            ProjectService.getInstance().assignUserIdByName("Демонстрационный проект №1", UserService.getInstance().findByLogin("Главный администратор").getId(),null,0);
            ProjectService.getInstance().assignUserIdByName("Демонстрационный проект №2", UserService.getInstance().findByLogin("Главный администратор").getId(),null,0);
            ProjectService.getInstance().assignUserIdByName("Демонстрационный проект №3", UserService.getInstance().findByLogin("Новый пользователь 1").getId(),null,0);
            TaskService.getInstance().assignUserIdByName("Демонстрационное задание №1", UserService.getInstance().findByLogin("Новый пользователь 1").getId(),null,0);
            TaskService.getInstance().assignUserIdByName("Демонстрационное задание №2", UserService.getInstance().findByLogin("Главный администратор").getId(),null,0);
            TaskService.getInstance().assignUserIdByName("Демонстрационное задание №2", UserService.getInstance().findByLogin("Новый пользователь 1").getId(),null,1);
            TaskService.getInstance().assignUserIdByName("Демонстрационное задание №4", UserService.getInstance().findByLogin("Новый пользователь 1").getId(),null,0);
        } catch (ProjectException | TaskException e) {
            logger.error(e);
        }
    }

    public static void main(final String[] args) {
        final PublisherImpl publisher = new PublisherImpl();
        Application application = new Application();
        if (args == null) return;
        if (args.length < 1) return;
        final String param = args[0];
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
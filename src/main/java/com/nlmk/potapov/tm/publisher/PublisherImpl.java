package com.nlmk.potapov.tm.publisher;

import com.nlmk.potapov.tm.enumerated.RoleType;
import com.nlmk.potapov.tm.exception.ProjectException;
import com.nlmk.potapov.tm.exception.TaskException;
import com.nlmk.potapov.tm.listener.Listener;
import com.nlmk.potapov.tm.service.SystemService;
import com.nlmk.potapov.tm.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class PublisherImpl implements Publisher{
    List<Listener> listeners = new ArrayList();

    @Override
    public void addListener(Listener appListener) {
        if (!listeners.contains(appListener)){
            listeners.add(appListener);
        }
    }

    @Override
    public void deleteListener(Listener appListener) {
        listeners.remove(appListener);
    }

    @Override
    public int notifyListener(final String method, final Long userId, final RoleType roleType) throws TaskException, ProjectException {
        int result = 0;
        for(Listener listener: listeners){
            result = listener.callCommand(method,userId,roleType);
        }
        return result;
    }

    public void runLoop(final String startMethod) throws TaskException, ProjectException {
        final Scanner scanner = new Scanner(System.in);
        Long userId = null;
        RoleType roleType = null;
        String command = startMethod;
        int result = 0;
        System.out.println("** ДОБРО ПОЖАЛОВАТЬ В TASK MANAGER **");
        System.out.println(BLOCK_SEPARATOR);
        while (!EXIT.equals(command)){
            if (UserService.getInstance().getCurrentAppUser() != null){
                userId = UserService.getInstance().getCurrentAppUser().getId();
                roleType = UserService.getInstance().getCurrentAppUser().getRoleType();
            }
            else{
                userId = null;
                roleType = null;
            }
            if (!Objects.equals(command,null)) {
                SystemService.getInstance().roundAdd(command);
                result = notifyListener(command, userId, roleType);
            }
            System.out.print(INPUT_MESSAGE);
            command = scanner.nextLine();
        }
        result = notifyListener(command, userId, roleType);
        System.exit(result);
    }
}

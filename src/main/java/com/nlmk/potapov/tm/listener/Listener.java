package com.nlmk.potapov.tm.listener;

import com.nlmk.potapov.tm.enumerated.RoleType;
import com.nlmk.potapov.tm.exception.ProjectException;
import com.nlmk.potapov.tm.exception.TaskException;

import java.util.Scanner;

public interface Listener {

    final Scanner scanner = new Scanner(System.in);

    int callMethod(final String method, final Long userId, final RoleType roleType) throws TaskException, ProjectException;

}

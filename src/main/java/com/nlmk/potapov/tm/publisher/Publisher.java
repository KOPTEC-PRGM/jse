package com.nlmk.potapov.tm.publisher;

import com.nlmk.potapov.tm.enumerated.RoleType;
import com.nlmk.potapov.tm.exception.ProjectException;
import com.nlmk.potapov.tm.exception.TaskException;
import com.nlmk.potapov.tm.listener.Listener;

public interface Publisher {
    void addListener(Listener racer);
    void deleteListener(Listener racer);
    int notifyListener(final String method, final Long userId, final RoleType roleType) throws TaskException, ProjectException;
}


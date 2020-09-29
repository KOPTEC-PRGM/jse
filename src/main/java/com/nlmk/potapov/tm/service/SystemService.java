package com.nlmk.potapov.tm.service;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.nlmk.potapov.tm.constant.TerminalConst.COMMAND_HISTORY_SIZE;

public class SystemService {

    private static SystemService instance;

    private final Deque<String> commandHistory = new ArrayDeque<>();

    public static SystemService getInstance() {
        if (instance == null){
            instance = new SystemService();
        }
        return instance;
    }

    public Deque<String> getCommandHistory() {
        return commandHistory;
    }

    public void roundAdd(final String str){
        while (commandHistory.size() >= COMMAND_HISTORY_SIZE){
            commandHistory.poll();
        }
        commandHistory.offer(str);
    }

}

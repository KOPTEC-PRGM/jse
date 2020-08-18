package com.nlmk.potapov.tm.controller;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class AbstractController {

    private static final Deque<String> commandHistory = new ArrayDeque<>();

    public static Deque<String> getCommandHistory() {
        return commandHistory;
    }

    public static void roundAdd(final String str){
        commandHistory.add(str);
        if (commandHistory.size() > 10) commandHistory.poll();
    }

    protected final Scanner scanner = new Scanner(System.in);

}

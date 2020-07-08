package com.nlmk.potapov.tm;

import static com.nlmk.potapov.tm.constant.TerminalConst.*;

public class Main {

    public static void main(final String[] args) {
        displayWelcome();
        run(args);
    }

    private static void run(final String[] args) {
        if (args == null) return;
        if (args.length < 1) return;
        final String param = args[0];
        switch (param) {
            case HELP: displayHelp();
            case VERSION: displayVersion();
            case ABOUT: displayAbout();
            default: displayError();
        }
    }

    private static void displayHelp() {
        System.out.println("version - Display program version.");
        System.out.println("about - Display developer info.");
        System.out.println("help - Display list of terminal commands.");
        System.exit(0);
    }

    private static void displayVersion() {
        System.out.println("1.0.0");
        System.exit(0);
    }

    private static void displayAbout() {
        System.out.println("Vadim Potapov");
        System.out.println("potapov_vs@nlmk.com");
        System.exit(0);
    }

    private static void displayError() {
        System.out.println("Error! Unknown program argument...");
        System.exit(-1);
    }

    private static void displayWelcome() {
        System.out.println("** WELCOME TO TASK MANAGER **");
    }

}

package com.Revature.AaronDownward.Project0;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "clalendar", description = "Main command to run calendar app", subcommands = {
    NewCalendarCommand.class,
    NewEventCommand.class,
    ViewCalendarCommand.class,
    ViewEventCommand.class,
    EditEventCommand.class
})
public class App implements Callable<Integer> {

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        System.out.println(DatabaseAccess.getEventTimestamp("testcalendar1", "1"));
        System.out.println("You called the main \"clalendar\" command");
        return 0;
    }    

}

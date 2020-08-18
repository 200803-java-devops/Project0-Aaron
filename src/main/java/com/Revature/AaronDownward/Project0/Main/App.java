package com.Revature.AaronDownward.Project0.Main;

import java.util.concurrent.Callable;

import com.Revature.AaronDownward.Project0.Commands.EditEventCommand;
import com.Revature.AaronDownward.Project0.Commands.NewCalendarCommand;
import com.Revature.AaronDownward.Project0.Commands.NewEventCommand;
import com.Revature.AaronDownward.Project0.Commands.ViewCalendarCommand;
import com.Revature.AaronDownward.Project0.Commands.ViewEventCommand;
import com.Revature.AaronDownward.Project0.Database.DatabaseAccess;

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

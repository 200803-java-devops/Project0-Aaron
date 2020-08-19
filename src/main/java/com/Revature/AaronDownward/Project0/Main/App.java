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
import picocli.CommandLine.Option;

@Command(name = "clalendar", description = "Main command to run calendar app", subcommands = {
    NewCalendarCommand.class,
    NewEventCommand.class,
    ViewCalendarCommand.class,
    ViewEventCommand.class,
    EditEventCommand.class
})
public class App implements Callable<Integer> {
    @Option(names={"-h", "--help"}, description="Display help/usage.", help=true)
    private boolean help;  
    
    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        if (this.help) {
            CommandLine.usage(this, System.out, CommandLine.Help.Ansi.AUTO);
        } else {
            System.out.println("Welcome to the Command Line Calendar and Scheduling App:CLalendar");
            System.out.println("\t\t\t\tCLalendar!!");
            System.out.println("Enter clalendar -h(--help) into the terminal for usage help");

        }
        return 0;
    }    

}

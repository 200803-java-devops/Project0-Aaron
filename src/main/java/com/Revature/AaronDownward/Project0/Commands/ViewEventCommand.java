package com.Revature.AaronDownward.Project0.Commands;

import java.util.concurrent.Callable;

import com.Revature.AaronDownward.Project0.Objects.Calendar;
import com.Revature.AaronDownward.Project0.Objects.Print;
import com.Revature.AaronDownward.Project0.UserInput.UserInput;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "viewevent", description = "View a specified event")
public class ViewEventCommand implements Callable<Integer> {

    @Option(names = {"-w", "--wizard"}, paramLabel = "View event Wizard", description = "Indication that user would like to go through wizard to specify an event to view")
    private boolean wizard;

    @Option(names = {"-c", "-calendar"}, paramLabel = "calendarId", description = "The calendar ID")
    private String calendarId;

    @Option(names = {"-e", "-eventid"}, paramLabel = "eventId", description = "The event ID")
    private String eventId;

    @Option(names={"-h", "--help"}, description="Display help/usage.", help=true)
    private boolean help;

    @Override
    public Integer call() {
        if (help) {
            CommandLine.usage(this, System.out, CommandLine.Help.Ansi.AUTO);
            return 1;
        }
        if (wizard) {
            String[] eventDetails = UserInput.eventViewWizard();
            if (eventDetails == null) {
                System.out.println("Something went wrong in the view event wizard");
                return 2;
            }
            calendarId = eventDetails[0];
            eventId = eventDetails[1];
        }
        if (calendarId == null) {
            System.out.println("A valid calendarId of an existing calendar is required for -c option if not using --wizard option");
            return 3;
        } else {
            if (Calendar.getCalendarById(calendarId) == null) {
                System.out.println("Invalid calendarId. Please include valid calendarId of existing calendar");
                return 4;
            }
        }
        Calendar calendar = Calendar.getCalendarById(calendarId);
        
        if (eventId == null) {
            System.out.println("A valid eventId of an existing event in calendar " + calendarId + " is required for -e option if not using --wizard option");
            return 5;
        } else {
            if (calendar.getEventById(eventId) == null) {
                System.out.println("Invalid eventId. Please include eventId of existing event in calendar " + calendarId + ".");
                return 6;
            }
        }

        Print.printEvent(calendarId, eventId);
        return 0;
    }

}

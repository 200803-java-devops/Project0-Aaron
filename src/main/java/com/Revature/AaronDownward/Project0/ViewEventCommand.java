package com.Revature.AaronDownward.Project0;

import java.util.concurrent.Callable;

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

    @Override
    public Integer call() {
        if (wizard) {
            String[] eventDetails = UserInput.eventViewWizard();
            if (eventDetails == null) {
                System.out.println("Something went wrong in the view event wizard");
                return 1;
            }
            calendarId = eventDetails[0];
            eventId = eventDetails[1];
        }
        Print.printEvent(calendarId, eventId);
        return 0;
    }

}

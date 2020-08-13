package com.Revature.AaronDownward.Project0;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "newevent", description = "Creates a new event")
public class NewEventCommand implements Callable<Integer> {

    @Option(names = {"-w", "--wizard"}, paramLabel = "Event Creation Wizard", description = "Indication that user would like to go through event creation wizard to input event details")
    private boolean wizard;

    @Option(names = {"-c", "--calendar"}, paramLabel = "Calendar ID", description = "The calendar ID")
    private String calendarId;

    @Option(names = {"-e", "--eventid"}, paramLabel = "Event ID", description = "The event ID")
    private String eventId;

    @Option(names = {"-d", "--date"}, paramLabel = "date", description = "The date the event occurs")
    private String date;

    @Option(names = {"-n", "--name"}, paramLabel = "name", description = "The name of the event")
    private String name;

    @Option(names = {"-z", "--enddate"}, paramLabel = "end date", description = "The date the event will end")
    private String endDate;

    @Option(names = {"-t", "--starttime"}, paramLabel = "start time", description = "The time the event will start")
    private String startTime;

    @Option(names = {"-y", "--endtime"}, paramLabel = "end time", description = "The time the event will end")
    private String endTime;

    @Option(names = {"-i", "--info"}, paramLabel = "description", description = "The description of the event")
    private String description;

    @Option(names = {"-a", "--attendees"}, paramLabel = "attendees", description = "Invitees to the invite, separate names by commas for multiple attendees")
    private String attendees;

    @Override
    public Integer call() {
        if (wizard) {
            String[] eventDetails = UserInput.eventCreationWizard();
            if (eventDetails == null) {
                return 1;
            }
            Calendar calendar = Calendar.getCalendarById(eventDetails[0]);

            calendar.createEvent(eventDetails);
        }
        else {
            Calendar calendar = Calendar.getCalendarById(calendarId);
            ArrayList<String> attendeesList = new ArrayList<String>();
            if (attendees != null) {
                String[] attendeesArray = attendees.split(",");
                for (String attendee : attendeesArray) {
                    attendeesList.add(attendee);
                }
            }

            calendar.createEvent(eventId, name, date, endDate, startTime, endTime, description, attendeesList);
        }
        return 0;
    }
}

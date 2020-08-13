package com.Revature.AaronDownward.Project0;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "editevent", description = "Edit a specified event")
public class EditEventCommand implements Callable<Integer> {
    @Option(names = { "-w",
            "--wizard" }, paramLabel = "View event Wizard", description = "Indication that user would like to go through wizard to specify an event to view")
    private boolean wizard;

    @Option(names = { "-c", "-calendar" }, paramLabel = "calendarId", description = "The calendar ID")
    private String calendarId;

    @Option(names = { "-e", "-eventid" }, paramLabel = "eventId", description = "The event ID")
    private String eventId;

    @Option(names = { "-x", "--delete" }, description = "Indication that user would like to delete specified event")
    private boolean delete;

    @Option(names = { "-n", "--name" }, paramLabel = "name", description = "The name of the event")
    private String name;

    @Option(names = { "-d", "--date" }, paramLabel = "date", description = "The date the event occurs")
    private String date;

    @Option(names = { "-z", "--enddate" }, paramLabel = "endDate", description = "The date the event will end")
    private String endDate;

    @Option(names = { "-t", "--starttime" }, paramLabel = "startTime", description = "The time the event will start")
    private String startTime;

    @Option(names = { "-y", "--endtime" }, paramLabel = "endTime", description = "The time the event will end")
    private String endTime;

    @Option(names = { "-i", "--info" }, paramLabel = "description", description = "The description of the event")
    private String description;

    @Option(names = { "-a",
            "--attendees" }, paramLabel = "attendees", arity = "1..*", description = "Invitees to the invite, separate names by commas for multiple attendees")
    private String[] attendees;

    @Option(names = { "-r",
            "--replaceAttendees" }, description = "Specifies that attendees supplied will replace the current attendees in event instead of adding to them.")
    private boolean replaceAttendees;

    @Override
    public Integer call() {
        Calendar calendar;
        Event event;
        if (wizard) {
            String[] eventDetails = UserInput.eventEditWizard();
            if (eventDetails == null) {
                System.out.println("Something went wrong in the view event wizard");
                return 1;
            }
            calendarId = eventDetails[0];
            eventId = eventDetails[1];
            delete = Boolean.parseBoolean(eventDetails[2]);
            name = eventDetails[3];
            date = eventDetails[4];
            endDate = eventDetails[5];
            startTime = eventDetails[6];
            endTime = eventDetails[7];
            description = eventDetails[8];
            if (eventDetails[9] != null) {
                attendees = eventDetails[9].split(",");
            }
            replaceAttendees = Boolean.parseBoolean(eventDetails[10]);
        }
        calendar = Calendar.getCalendarById(calendarId);
        event = calendar.getEventById(eventId);
        if (delete) {
            calendar.deleteEvent(eventId);
        } else {
            event.editEvent(name, date, endDate, startTime, endTime, description, attendees, replaceAttendees);
        }
        return 0;
    }

}

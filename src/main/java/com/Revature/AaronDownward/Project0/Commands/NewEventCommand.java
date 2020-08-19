package com.Revature.AaronDownward.Project0.Commands;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import com.Revature.AaronDownward.Project0.Database.DataParse;
import com.Revature.AaronDownward.Project0.Database.DatabaseAccess;
import com.Revature.AaronDownward.Project0.Objects.Calendar;
import com.Revature.AaronDownward.Project0.Objects.Event;
import com.Revature.AaronDownward.Project0.UserInput.UserInput;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "newevent", description = "Creates a new event")
public class NewEventCommand implements Callable<Integer> {

    @Option(names = {"-w", "--wizard"}, paramLabel = "Event Creation Wizard", description = "Indication that user would like to go through event creation wizard to input event details")
    private boolean wizard;

    @Option(names = { "-b", "--database" }, paramLabel = "database", description = "Option to push new events to database")
    private boolean database;

    @Option(names = {"-c", "--calendar"}, paramLabel = "Calendar ID", description = "The calendar ID")
    private String calendarId;

    @Option(names = {"-e", "--eventid"}, paramLabel = "Event ID", description = "The event ID")
    private String eventId;

    @Option(names = {"-d", "--date"}, paramLabel = "date", description = "The date the event will occur")
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

    @Option(names = {"-a", "--attendees"}, paramLabel = "attendees", arity = "1..*", description = "Anticipated event attendees Separate each attendee by a space: \"Aaron D\" \"Emily C\"")
    private String[] attendees;

    @Option(names={"-h", "--help"}, description="Display help/usage.", help=true)
    private boolean help;

    @Override
    public Integer call() {
        if (help) {
            CommandLine.usage(this, System.out, CommandLine.Help.Ansi.AUTO);
            return 1;
        }
        if (wizard) {
            String[] eventDetails = UserInput.eventCreationWizard();
            if (eventDetails == null) {
                return 2;
            }
            calendarId = eventDetails[0];
            eventId = eventDetails[1];
            name = eventDetails[2];
            date = eventDetails[3];
            endDate = eventDetails[4];
            startTime = eventDetails[5];
            endTime = eventDetails[6];
            description = eventDetails[7];
            String attendeesString = eventDetails[8];
            if (attendeesString != null) {
                attendees = attendeesString.split(",");
            }
            database = Boolean.parseBoolean(eventDetails[9]);
        }
        if (calendarId == null) {
            System.out.println("A valid calendarId of an existing calendar is required for the -c option if not using --wizard option");
            return 3;
        } else {
            if (Calendar.getCalendarById(calendarId) == null) {
                System.out.println("Invalid calendarId. Please include calendarId of existing calendar");
                return 4;
            }
        }
        Calendar calendar = Calendar.getCalendarById(calendarId);
    
        if (eventId == null) {
            System.out.println("A unique eventId for this event is required for the -e option if not using --wizard option");
            return 5;
        } else {
            if (calendar.getEventById(eventId) != null) {
                System.out.println("Invalid eventId; already exists in calendar. Please provide a unique eventId for the new event");
                return 6;
            }
        }
        if (date == null) {
            System.out.println("A date for this event is required for the -d option if not using --wizard option");
            return 7;
        }
        // TODO implement date-time validation checks
        if (name == null) {
            name = "";
        }
        if (endDate == null) {
            endDate = "";
        }
        if (startTime == null) {
            startTime = "";
        }
        if (endTime == null) {
            endTime = "";
        }
        if (description == null) {
            description = "";
        }
        ArrayList<String> attendeesList = new ArrayList<String>();
        if (attendees != null) {
            for (String attendee : attendees) {
                attendeesList.add(attendee);
            }
        }

        if (database) {
            Boolean calendarExistsInDB = DatabaseAccess.checkForCalendarInDB(calendar);
            if (!calendarExistsInDB) {
                Boolean createCalendar = UserInput.createCalendarInDB(calendarId);
                if (createCalendar) {
                    DatabaseAccess.addCalendarToDB(calendarId);
                } else {
                    database = false;
                }
            } else {
                Boolean updatedCalendarInDB = DatabaseAccess.compareToCalendarInDB(calendar);
                if (updatedCalendarInDB) {
                    Boolean getUpdatedCalendar = UserInput.getUpdatedCalendarFromDB(calendarId);
                    if (getUpdatedCalendar) {
                        DataParse.createCalendarFromRS(DatabaseAccess.getCalendarFromDB(calendarId), calendarId);
                        Boolean continueEventEdit = UserInput.continueEventEdit(calendarId, eventId);
                        if (!continueEventEdit) {
                            return 8;
                        }
                    }
                }
            }
        }
        calendar = Calendar.getCalendarById(calendarId);

        calendar.createEvent(eventId, name, date, endDate, startTime, endTime, description, attendeesList);
        Event event = calendar.getEventById(eventId);
        if (database) {
            boolean eventInDB = DatabaseAccess.checkForEventInDB(calendarId, eventId);
            if (eventInDB) {
                boolean replaceEvent = UserInput.replaceEventInDB(calendarId, eventId);
                if (replaceEvent) {
                    DatabaseAccess.removeEventFromDB(calendarId, eventId);
                } else {
                    return 9;
                }
            }
            DatabaseAccess.addEventToCalendarDB(calendarId, event);
        }
        return 0;
    }
}

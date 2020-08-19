package com.Revature.AaronDownward.Project0.Commands;

import java.util.concurrent.Callable;

import com.Revature.AaronDownward.Project0.Database.DataParse;
import com.Revature.AaronDownward.Project0.Database.DatabaseAccess;
import com.Revature.AaronDownward.Project0.Objects.Calendar;
import com.Revature.AaronDownward.Project0.Objects.Event;
import com.Revature.AaronDownward.Project0.UserInput.UserInput;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "editevent", description = "Edit a specified event")
public class EditEventCommand implements Callable<Integer> {
    @Option(names = { "-w", "--wizard" }, paramLabel = "Edit event Wizard", description = "Option to go through wizard to specify an event to edit")
    private boolean wizard;

    @Option(names = { "-b", "--database" }, paramLabel = "database", description = "Option to push event edits to database")
    private boolean database;

    @Option(names = { "-c", "-calendar" }, paramLabel = "calendarId", description = "The calendar ID of the event to edit")
    private String calendarId;

    @Option(names = { "-e", "-eventid" }, paramLabel = "eventId", description = "The event ID of the event to edit")
    private String eventId;

    @Option(names = { "-x", "--delete" }, description = "Indication that user would like to delete specified event")
    private boolean delete;

    @Option(names = { "-n", "--name" }, paramLabel = "name", description = "New name of the event")
    private String name;

    @Option(names = { "-d", "--date" }, paramLabel = "date", description = "New date the event will occur")
    private String date;

    @Option(names = { "-z", "--enddate" }, paramLabel = "endDate", description = "New date the event will end")
    private String endDate;

    @Option(names = { "-t", "--starttime" }, paramLabel = "startTime", description = "New time the event will start")
    private String startTime;

    @Option(names = { "-y", "--endtime" }, paramLabel = "endTime", description = "New time the event will end")
    private String endTime;

    @Option(names = { "-i", "--info" }, paramLabel = "description", description = "New description of the event")
    private String description;

    @Option(names = { "-a", "--attendees" }, paramLabel = "attendees", arity = "1..*", description = "New anticipated event attendees. Separate each attendee by a space: \"Aaron D\" \"Emily C\"")
    private String[] attendees;

    @Option(names = { "-r", "--replaceAttendees" }, description = "Specifies that attendees supplied will replace the current attendees in event instead of adding to them.")
    private boolean replaceAttendees;

    @Option(names={"-h", "--help"}, description="Display help/usage.", help=true)
    private boolean help;

    @Override
    public Integer call() {
        Calendar calendar;
        Event event;
        if (help) {
            CommandLine.usage(this, System.out, CommandLine.Help.Ansi.AUTO);
            return 1;
        }
        if (wizard) {
            String[] eventDetails = UserInput.eventEditWizard();
            if (eventDetails == null) {
                System.out.println("Something went wrong with the edit event wizard or caused it to exit early");
                return 2;
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
            database = Boolean.parseBoolean(eventDetails[11]);
        }
        if (calendarId == null) {
            System.out.println("A valid calendarId of an existing calendar is required for -c option if not using --wizard option");
            return 3;
        } else {
            if (Calendar.getCalendarById(calendarId) == null) {
                System.out.println("Invalid calendarId. Please include calendarId of existing calendar");
                return 4;
            }
        }
        calendar = Calendar.getCalendarById(calendarId);
        if (eventId == null) {
            System.out.println("A valid eventId of an existing event in calendar " + calendarId + " is required for -e option if not using --wizard option");
            return 5;
        } else {
            if (calendar.getEventById(eventId) == null) {
                System.out.println("Invalid eventId. Please include eventId of existing event in calendar " + calendarId + ".");
                return 6;
            }
        }
        // TODO implement date-time validation checks
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
                            return 7;
                        }
                    }
                }
            }
        }
        calendar = Calendar.getCalendarById(calendarId);
        event = calendar.getEventById(eventId);
        if (delete) {
            calendar.deleteEvent(eventId);
        } else {
            event.editEvent(name, date, endDate, startTime, endTime, description, attendees, replaceAttendees);
        }
        if (database) {
            DatabaseAccess.removeEventFromDB(calendarId, eventId);
            if (!delete) {
                DatabaseAccess.addEventToCalendarDB(calendarId, event);
            }
        }
        return 0;
    }

}

package com.Revature.AaronDownward.Project0.Commands;

import java.util.concurrent.Callable;

import com.Revature.AaronDownward.Project0.Database.DatabaseAccess;
import com.Revature.AaronDownward.Project0.Objects.Calendar;
import com.Revature.AaronDownward.Project0.UserInput.InputValidation;
import com.Revature.AaronDownward.Project0.UserInput.UserInput;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "newcal", description = "Creates a new calendar")
public class NewCalendarCommand implements Callable<Integer> {

    @Option(names = { "-w", "--wizard" }, paramLabel = "calendar creation Wizard", description = "Option to go through wizard to specify an event to view")
    private boolean wizard;
    
    @Option(names = { "-c", "--calendar" }, paramLabel = "calendar ID", description = "the calendarId")
    private String calendarId;

    @Option(names = { "-b", "--database" }, description = "Option to push event edits to database")
    private boolean database;

    @Option(names={"-h", "--help"}, description="Display help/usage.", help=true)
    private boolean help;

    @Override
    public Integer call() {
        if (help) {
            CommandLine.usage(this, System.out, CommandLine.Help.Ansi.AUTO);
            return 1;
        }
        if (wizard) {
            String[] calendarDetails = UserInput.calendarCreationWizard();
            calendarId = calendarDetails[0];
            database = Boolean.parseBoolean(calendarDetails[1]);
        }
        if (calendarId == null) {
            System.out.println("A valid calendarId that can be used as a filename is required for the -c option if not using --wizard option");
            return 2;
        } else {
            Boolean validCalendarId = InputValidation.checkValidNewCalendarId(calendarId);
            if (!validCalendarId) {
                System.out.println("Invalid calendarId. Please use a valid, unique calendarId that can be used as a filename");
                return 3;
            }
        }
        Calendar.createCalendar(calendarId);
        Calendar calendar = Calendar.getCalendarById(calendarId);
        if (database) {
            Boolean calendarExistsInDB = DatabaseAccess.checkForCalendarInDB(calendar);
            if (calendarExistsInDB) {
                Boolean replaceCalendarInDB = UserInput.replaceCalendarInDB(calendarId);
                if (!replaceCalendarInDB) {
                    return 4;
                }
                boolean deleteSuccess = DatabaseAccess.removeCalendarFromDB(calendarId);
                if (!deleteSuccess) {
                    return 5;
                }
            }
            boolean addSuccess = DatabaseAccess.addCalendarToDB(calendarId);
            if (!addSuccess) {
                return 6;
            }
        }
        return 0;
    }

}
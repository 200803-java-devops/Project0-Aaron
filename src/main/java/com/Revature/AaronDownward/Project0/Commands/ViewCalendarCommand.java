package com.Revature.AaronDownward.Project0.Commands;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import com.Revature.AaronDownward.Project0.Objects.Calendar;
import com.Revature.AaronDownward.Project0.Objects.Print;
import com.Revature.AaronDownward.Project0.UserInput.UserInput;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "viewcal", description = "View a specified calendar")
public class ViewCalendarCommand implements Callable<Integer> {

    @Option(names = {"-w", "--wizard"}, paramLabel = "View Calendar Wizard", description = "Indication that user would like to go through wizard to select viewing options")
    private boolean wizard;

    @Option(names = {"-c", "--calendar"}, paramLabel = "Calendar ID", description = "The calendar ID")
    private String calendarId;

    @Option(names = {"-d", "--day"}, arity = "0..1", fallbackValue = "today", paramLabel = "View day", description = "Views a day's events. If a date is passed into the option, views that day, otherwise defaults to viewing the current date")
    private String dayDate;

    @Option(names = {"-e", "--week"}, arity = "0..1", fallbackValue = "today", paramLabel = "View week", description = "Views a week's events. If a date is passed into the option, views the week starting from that date, otherwise defaults to viewing the current week")
    private String weekDate;

    @Option(names = {"-m", "--month"}, arity = "0..1", fallbackValue = "current", paramLabel = "View month", description = "Views a month's events. If a month is passed into the option, views that month, otherwise defaults to viewing the current week")
    private String month;

    @Option(names = {"-u", "--custom"}, arity = "2", paramLabel = "Custom view", description = "Views the events for a custom time period. Requires two dates to be passed in, then displays all the events for that period")
    private String[] customDates;

    @Option(names={"-h", "--help"}, description="Display help/usage.", help=true)
    private boolean help;

    @Override
    public Integer call() {
        if (help) {
            CommandLine.usage(this, System.out, CommandLine.Help.Ansi.AUTO);
            return 1;
        }
        if (wizard) {
            ArrayList<String> wizardParams = UserInput.viewCalendarWizard();
            if(wizardParams == null) {
                System.out.println("Invalid viewing option given, or the view calendar wizard didn't work properly");
                return 2;
            }
            calendarId = wizardParams.get(0);
            switch (wizardParams.get(1)) {
                case "d": 
                    dayDate = wizardParams.get(2);
                    break;
                case "w":
                    weekDate = wizardParams.get(2);
                    break;
                case "m":
                    month = wizardParams.get(2);
                    break;
                case "c":
                    customDates = new String[2];
                    customDates[0] = wizardParams.get(2);
                    customDates[1] = wizardParams.get(3);
                    break;
                default:
                    System.out.println("The view calendar wizard didn't work properly");
                    return 3;
            }
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
        // TODO add date validation
        if (dayDate != null) {
            Print.printDay(calendarId, dayDate);
        } else if (weekDate != null) {
            Print.printWeek(calendarId, weekDate);
        } else if (month != null) {
            Print.printMonth(calendarId, month);
        } else if (customDates != null) {
            Print.printCustom(calendarId, customDates);
        } else {
            System.out.println("If not using the --wizard option, you must select a viewing option: -d, -e, -m, or -u. Type \"clalendar viewcal -h(--help)\" for usage help");
            return 5;
        }

        return 0;
    }

}

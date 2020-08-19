package com.Revature.AaronDownward.Project0.Commands;

import java.util.concurrent.Callable;

import com.Revature.AaronDownward.Project0.Objects.Calendar;
import com.Revature.AaronDownward.Project0.UserInput.UserInput;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "newcal", description = "Creates a new calendar")
public class NewCalendarCommand implements Callable<Integer> {

    @Option(names = { "-c", "--calendar" }, paramLabel = "calendarId", description = "the calendarId")
    private String calendarId;

    @Override
    public Integer call() {
        if (calendarId == null) {
            calendarId = UserInput.getUserCalendarId();
        }
        Calendar.createCalendar(calendarId);
        return 0;
    }

}
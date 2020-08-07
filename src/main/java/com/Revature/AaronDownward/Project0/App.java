package com.Revature.AaronDownward.Project0;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        //interpret arguments from terminal
        //will later transition to creating unique terminal commands to run code
        //will use terminal args for prototype
        interpret(args);

    }

    // logic to interpret commands and call methods based on input
    // minimal commands I want to implement: newCal, viewCal, viewEvent, newEvent, editEvent  
    private static void interpret(String[] args) {
        if(args.length == 0) return;
        Calendar calendar;
        Event event;
        String calendarId;
        String eventId;
                
        //check if first argument matches command and run corresponding command
        switch(args[0]) {
            // create new Calendar
            case "newCal":
                calendarId = args[1];

                Calendar.createCalendar(calendarId);
                break;
            
            // print Calendar
            case "viewCal":
                calendarId = args[1];
                calendar = Calendar.getCalendarById(calendarId);

                String displayFormat = args[2];
                if(displayFormat == "custom") {
                    String startDate = args[3];
                    String endDate = args[4];

                    calendar.print(displayFormat, startDate, endDate);
                }
                else {
                    calendar.print(displayFormat);
                }
                break;

            // view event
            case "viewEvent":
                calendarId = args[1];
                eventId = args[2];
                calendar = Calendar.getCalendarById(calendarId);
                event = calendar.getEventById(eventId);

                event.print();
                break;

            case "newEvent":
                calendarId = args[1];
                eventId = args[2];
                String[] details = Arrays.copyOfRange(args, 3, args.length);
                calendar = Calendar.getCalendarById(calendarId);

                calendar.createEvent(eventId, details);
                break;

            // edit event
            case "editEvent":
                calendarId = args[1];
                eventId = args[2];
                String parameter = args[3];
                String value = args[4];
                calendar = Calendar.getCalendarById(calendarId);

                calendar.editEvent(eventId, parameter, value);
                break;

            default:
                System.out.println("No valid command received");
        }
    }


}

package com.Revature.AaronDownward.Project0;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        //interpret arguments from terminal
        //will later transition to creating unique terminal commands to run code
        //will use terminal args for prototype

        //testing new prototype base methods
        /*Calendar cal = new Calendar("AaronCalendar");
        String[] deets = {"my birthday", "8/6/2020", "12:00am", "11:59pm", "a cool birthday", "celebrating someone I'm sure?"};
        cal.createEvent("birthday", deets);
        String[] deets2 = {"your birthday", "8/7/2020", "12:00am", "11:59pm", "a cool birthday", "celebrating someone I'm sure?"};
        cal.createEvent("bday", deets2);
        Event myBirthday = cal.getEventById("birthday");*/

        interpret(args);

    }

    // logic to interpret commands and call methods based on input
    // minimal commands I want to implement: newCal, viewCal, viewEvent, newEvent, editEvent  
    private static void interpret(String[] args) {
        if(args.length == 0) return;
        
        //check if first argument matches command and run corresponding command
        switch(args[0]) {
            // create new Calendar
            case "newCal":
                String id = args[1];

                Calendar.createCalendar(id);
                break;
            
            // print Calendar
            case "viewCal":
                String calId = args[1];
                Calendar cal = Calendar.getCalendarById(calId);

                String displayFormat = args[2];
                if(displayFormat == "custom") {
                    String startDate = args[3];
                    String endDate = args[4];

                    cal.print(displayFormat, startDate, endDate);
                }
                else {
                    cal.print(displayFormat);
                }
                break;

            // view event
            case "viewEvent":
                String calendarId = args[1];
                String eventId = args[2];
                Calendar calendar = Calendar.getCalendarById(calendarId);

                event.print();
                break;

            case "newEvent":
                String cId = args[1];
                String evId = args[2];
                String[] details = Arrays.copyOfRange(args, 3, args.length - 1);
                Calendar calendars = Calendar.getCalendarById(cId);

                calendars.createEvent(evId, details);
                break;

            // edit event
            case "editEvent":
                String calendId = args[1];
                String eveId = args[2];
                String param = args[3];
                String value = args[4];
                Calendar calen = Calendar.getCalendarById(calendId);
                Event even = calen.getEventById(eveId);

                even.edit(param, value);
                break;

            default:
                System.out.println("No valid command received");
        }
    }


}

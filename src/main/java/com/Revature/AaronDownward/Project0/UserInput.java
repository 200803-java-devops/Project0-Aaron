package com.Revature.AaronDownward.Project0;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInput {

    public static ArrayList<String> viewCalendarWizard() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> wizardParams = new ArrayList<String>();
        System.out.println("Enter the calendar ID of the calendar you would like to view:");
        String calendarId = scanner.nextLine();
        wizardParams.add(calendarId);
        System.out.println("Would you like to view a day, week, month, or a custom time period? (d/w/m/c)");
        String printOption = scanner.nextLine().toLowerCase();
        switch (printOption) {
            case ("d"):
            case ("day"):
                wizardParams.add("d");
                System.out.println("Enter the date of the day you would like to view, or enter \"today\":");
                String dayDate = scanner.nextLine();
                wizardParams.add(dayDate);
                break;
            case ("w"):
            case ("week"):
                wizardParams.add("w");
                System.out.println("Enter the starting date of the week you would like to view, or enter \"current\":");
                String weekDate = scanner.nextLine();
                wizardParams.add(weekDate);
                break;
            case ("m"):
            case ("month"):
                wizardParams.add("m");
                System.out.println(
                        "Specify the month you would like to view with full name, three letter abbreviation, or number:");
                String month = scanner.nextLine();
                wizardParams.add(month);
                break;
            case ("c"):
            case ("custom"):
                wizardParams.add("c");
                System.out.println("Enter the starting date of your custom time period:");
                String startDate = scanner.nextLine();
                wizardParams.add(startDate);
                System.out.println("Enter the ending date of your custom time period:");
                String endDate = scanner.nextLine();
                wizardParams.add(endDate);
                break;
            default:
                System.out.println("Something went wrong in switch statement:");
                scanner.close();
                return null;
        }
        scanner.close();
        return wizardParams;
    }

    public static String getUserCalendarId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("A calendar ID is required to create a calendar.");
        System.out.println("Please enter a calendar ID for this calendar:");
        String calendarId = scanner.nextLine();
        scanner.close();
        return calendarId;
    }

    public static String[] eventCreationWizard() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        System.out.println("Please enter the calendar ID of the calendar you want to add this event to:");
        String calendarId = scanner.nextLine();

        if (!Calendar.calendarExists(calendarId)) {
            System.out.println("That calendar doesn't exist. Would you like to create one? (y/n)");
            answer = scanner.nextLine().toLowerCase();
            if (answer == "yes" || answer == "y") {
                System.out.println("Enter the calendar ID for the new calendar:");
                calendarId = scanner.nextLine();
                Calendar.createCalendar(calendarId);
            } else {
                System.out.println("Events must be added to an existing calendar. Exiting wizard");
                scanner.close();
                return null;
            }
        }

        System.out.println("Please enter the event ID:");
        String eventId = scanner.nextLine();

        System.out.println("Please enter the name of your event:");
        String name = scanner.nextLine();

        System.out.println("Please enter the date the event will occur:");
        String date = scanner.nextLine();

        System.out.println("Will your event last multiple days? (y/n)");
        answer = scanner.nextLine();
        String endDate;
        if (answer.equals("yes") || answer.equals("y")) {
            System.out.println("Please enter the end date for your event:");
            endDate = scanner.nextLine();
        } else {
            endDate = "";
        }

        System.out.println("Please enter the start time of your event:");
        String startTime = scanner.nextLine();

        System.out.println("Please enter the end time of your event:");
        String endTime = scanner.nextLine();

        System.out.println("Please enter a description for your event:");
        String description = scanner.nextLine();

        System.out.println("How many people will be attending the event?");
        int number = Integer.parseInt(scanner.nextLine());
        String attendees = "";
        String attendee;
        for (int i = 0; i < number; i++) {
            System.out.println("Enter the name of attendee " + (i + 1) + ":");
            attendee = scanner.nextLine();
            attendees += attendee;
            if ((i + 1) < number) {
                attendees += ",";
            }
        }
        scanner.close();
        String[] eventDetails = { calendarId, eventId, name, date, endDate, startTime, endTime, description,
                attendees };

        return eventDetails;
    }

    public static String[] eventViewWizard() {
        Scanner scanner = new Scanner(System.in);
        String[] eventDetails = new String[2];
        System.out.println("Please enter the calendar ID of the calendar from which you'd like to view an event:");
        eventDetails[0] = scanner.nextLine();
        System.out.println("Please enter the Event ID of the event you would like to view:");
        eventDetails[1] = scanner.nextLine();
        scanner.close();
        return eventDetails;
    }

    public static String[] eventEditWizard() {
        Scanner scanner = new Scanner(System.in);
        String calendarId = null;
        String eventId = null;
        String delete = "false";
        String name = null;
        String date = null;
        String endDate = null;
        String startTime = null;
        String endTime = null;
        String description = null;
        String attendees = null;
        String replaceAttendees = "false";
        String[] eventDetails = {calendarId, eventId, delete, name, date, endDate, startTime, endTime, description, attendees, replaceAttendees};
        String[] parametersArray = {"name", "date", "end date", "start time", "end time", "description", "attendees"};
        System.out.println("Please enter the calendar ID of the calendar from which you'd like to edit an event:");
        calendarId = scanner.nextLine();
        eventDetails[0] = calendarId;
        System.out.println("Please enter the Event ID of the event you would like to edit:");
        eventId = scanner.nextLine();
        eventDetails[1] = eventId;
        System.out.println("Would you like to delete this event? (y/n)");
        String deleteResponse = scanner.nextLine();
        if (deleteResponse.equals("y") || deleteResponse.equals("yes")) {
            delete = "true";
            eventDetails[2] = delete;
            scanner.close();
            return eventDetails;
        }
        System.out.println("Would you like to change a specific event detail? (y/n)");
        String editSpecificParameter = scanner.nextLine().toLowerCase();
        if (editSpecificParameter.equals("y") || editSpecificParameter.equals("yes")) {
            boolean repeatLoop = true;
            String parameter;
            String parameterValue;
            do {
                System.out.println(
                        "What event detail would you like to change? (name, date, enddate, starttime, endtime, description, attendees)");
                parameter = scanner.nextLine();
                if (parameter.equals("attendees")) {
                    System.out.println(
                            "Would you like to replace the current event attendee(s)? (y/n) (otherwise will add to attendee list)");
                    String replaceResponse = scanner.nextLine();
                    if (replaceResponse.equals("y") || replaceResponse.equals("yes")) {
                        replaceAttendees = "true";
                        eventDetails[10] = replaceAttendees;
                    }
                    System.out.println("Please enter the number of attendees you want to include:");
                    int attendeeCount = Integer.parseInt(scanner.nextLine());
                    attendees = "";
                    String attendee;
                    for (int i = 0; i < attendeeCount; i++) {
                        System.out.println("Enter the name of attendee " + (i + 1) + ":");
                        attendee = scanner.nextLine();
                        attendees += attendee;
                        if ((i + 1) < attendeeCount) {
                            attendees += ",";
                        }
                    }
                    eventDetails[9] = attendees;
                } else {
                    System.out.println("Please enter the value you would like to enter for " + parameter + ":");
                    parameterValue = scanner.nextLine().toLowerCase();
                    switch (parameter) {
                        case "name":
                            name = parameterValue;
                            eventDetails[3] = name;
                            break;
                        case "date":
                            date = parameterValue;
                            eventDetails[4] = date;
                            break;
                        case "enddate":
                            endDate = parameterValue;
                            eventDetails[5] = endDate;
                            break;
                        case "starttime":
                            startTime = parameterValue;
                            eventDetails[6] = startTime;
                            break;
                        case "endtime":
                            endTime = parameterValue;
                            eventDetails[7] = endTime;
                            break;
                        case "description":
                            description = parameterValue;
                            eventDetails[8] = description;
                            break;
                        default:
                            System.out.println("invalid parameter");
                    }
                }
                System.out.println("Would you like to change another event detail? (y/n)");
                String loopResponse = scanner.nextLine();
                if (!loopResponse.equals("y") && !loopResponse.equals("yes")) {
                    repeatLoop = false;
                }            
            } while (repeatLoop);

        } else {
            String changeResponse;
            for (int i = 0; i < parametersArray.length; i++) {
                System.out.println("Would you like to edit your event's " + parametersArray[i] + "? (y/n)");
                changeResponse = scanner.nextLine();
                if (!changeResponse.equals("y") && !changeResponse.equals("yes")) {
                    continue;
                }
                if (i < parametersArray.length -1) {
                    System.out.println("Please enter the value you want to change your event's " + parametersArray[i] + " to:");
                    eventDetails[i + 3] = scanner.nextLine();
                } else {
                    System.out.println("Would you like to replace the current event attendee(s)? (y/n) (otherwise will add to attendee list)");
                    String replaceResponse = scanner.nextLine();
                    if (replaceResponse.equals("y") || replaceResponse.equals("yes")) {
                        replaceAttendees = "true";
                        eventDetails[10] = replaceAttendees;
                    }
                    System.out.println("Please enter the number of attendees you want to include:");
                    int attendeeCount = Integer.parseInt(scanner.nextLine());
                    attendees = "";
                    String attendee;
                    for (int j = 0; j < attendeeCount; j++) {
                        System.out.println("Enter the name of attendee " + (j + 1) + ":");
                        attendee = scanner.nextLine();
                        attendees += attendee;
                        if ((j + 1) < attendeeCount) {
                            attendees += ",";
                        }
                    }
                    eventDetails[i + 3] = attendees;
                }
            }
        }
        scanner.close();
        return eventDetails;
    }

}

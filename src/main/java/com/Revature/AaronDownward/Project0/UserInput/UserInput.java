package com.Revature.AaronDownward.Project0.UserInput;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.Revature.AaronDownward.Project0.Objects.Calendar;
import com.Revature.AaronDownward.Project0.Objects.Event;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class UserInput {

    public static ArrayList<String> viewCalendarWizard() {
        Scanner scanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(scanner)));
        ArrayList<String> wizardParams = new ArrayList<String>();

        System.out.println("Enter the calendar ID of the calendar you would like to view:");
        String calendarId = scanner.nextLine();
        calendarId = InputValidation.calendarIdValidationLoop(calendarId, scanner);
        wizardParams.add(calendarId);
        
        System.out.println("Would you like to view a day, week, month, or a custom time period? (d/w/m/c)");
        String printOption = scanner.nextLine().toLowerCase();
        printOption = InputValidation.requiredFieldLoop(printOption, scanner).toLowerCase();
        switch (printOption) {
            case ("d"):
            case ("day"):
                wizardParams.add("d");
                System.out.println("Enter the date of the day you would like to view, or enter \"today\":");
                // TODO add date validation
                String dayDate = scanner.nextLine();
                wizardParams.add(dayDate);
                break;
            case ("w"):
            case ("week"):
                wizardParams.add("w");
                System.out.println("Enter the starting date of the week you would like to view, or enter \"current\":");
                // TODO add date validation
                String weekDate = scanner.nextLine();
                wizardParams.add(weekDate);
                break;
            case ("m"):
            case ("month"):
                wizardParams.add("m");
                System.out.println("Specify the month you would like to view with full name, three letter abbreviation, or number:");
                // TODO add month validation
                String month = scanner.nextLine();
                wizardParams.add(month);
                break;
            case ("c"):
            case ("custom"):
                wizardParams.add("c");
                System.out.println("Enter the starting date of your custom time period:");
                // TODO add date validation
                String startDate = scanner.nextLine();
                wizardParams.add(startDate);
                System.out.println("Enter the ending date of your custom time period:");
                String endDate = scanner.nextLine();
                wizardParams.add(endDate);
                break;
            default:
                System.out.println("Invalid viewing option entered. Exiting wizard.");
                scanner.close();
                return null;
        }
        scanner.close();
        return wizardParams;
    }

    private static void shutdown(Scanner scanner) {
        scanner.close();
        System.out.println("Exiting App");
        // message to logger.
    }

    public static String[] calendarCreationWizard() {
        Scanner scanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(scanner)));

        System.out.println("Please enter the unique calendar ID of the calendar you want to create:");
        String calendarId = scanner.nextLine();
        calendarId = InputValidation.idForCreationLoop(calendarId, scanner);

        System.out.println("Do you want to add this calendar to the database? (y/n)");
        String databaseResponse = scanner.nextLine();
        String database = "false";
        if (databaseResponse.equals("y") || databaseResponse.equals("yes")) {
            database = "true";
        }

        String[] calendarDetails = {calendarId, database};
        scanner.close();
        return calendarDetails;
    }

    public static String[] eventCreationWizard() {
        Scanner scanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(scanner)));

        System.out.println("Please enter the calendar ID of the calendar you want to add this event to:");
        String calendarId = scanner.nextLine();
        calendarId = InputValidation.calendarIdValidationLoop(calendarId, scanner);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> updated = executor.submit(new WizardListener(calendarId, "", "calendar"));

        System.out.println("Please enter the event ID: (required)");
        String eventId = scanner.nextLine();
        eventId = InputValidation.requiredFieldLoop(eventId, scanner);
        eventId = InputValidation.checkEventExistsLoop(calendarId, eventId, scanner, "add");
        if (updatedDB(updated)) {
            scanner.close();
            return null;
        }

        System.out.println("Do you want to add this event to the database? (y/n)");
        String databaseResponse = scanner.nextLine();
        String database = "false";
        if (databaseResponse.equals("y") || databaseResponse.equals("yes")) {
            database = "true";
        }
        if (updatedDB(updated)) {
            scanner.close();
            return null;
        }

        System.out.println("Please enter the name of your event:");
        String name = scanner.nextLine();
        if (updatedDB(updated)) {
            scanner.close();
            return null;
        }

        System.out.println("Please enter the date the event will occur*: (dd/mm/yy or dd/mm) (*required)");
        String date = scanner.nextLine();
        date = InputValidation.requiredFieldLoop(date, scanner);
        date = InputValidation.invalidDateLoop(date, scanner);
        if (updatedDB(updated)) {
            scanner.close();
            return null;
        }

        System.out.println("Will your event last multiple days? (y/n)");
        String isMultipleDays = scanner.nextLine();
        String endDate = "";
        if (isMultipleDays.equals("yes") || isMultipleDays.equals("y")) {
            System.out.println("Please enter the end date for your event: (dd/mm/yy or dd/mm)");
            endDate = scanner.nextLine();
            Boolean endDateNotValid;
            do {
                endDateNotValid = false;
                endDate = InputValidation.invalidDateLoop(endDate, scanner);
                if (endDate.equals("")) {
                    break;
                }
                if (InputValidation.dateIsBefore(endDate, date)) {
                    System.out.println(
                            "Date entered is before or on the start date. Please enter a date after the start date ("
                                    + date + ")");
                    endDate = scanner.nextLine();
                    endDateNotValid = true;
                }
            } while (endDateNotValid);
        }
        if (updatedDB(updated)) {
            scanner.close();
            return null;
        }

        System.out.println("Please enter the start time of your event: (hh:mm - hhmm 24-hour clock)");
        String startTime = scanner.nextLine();
        startTime = InputValidation.invalidTimeLoop(startTime, scanner);
        if (updatedDB(updated)) {
            scanner.close();
            return null;
        }

        System.out.println("Please enter the end time of your event: (hh:mm - hhmm 24-hour clock)");
        String endTime = scanner.nextLine();
        Boolean endTimeNotValid;
        do {
            endTimeNotValid = false;
            endTime = InputValidation.invalidTimeLoop(endTime, scanner);
            if (endTime.equals("")) {
                break;
            }
            if ((InputValidation.timeIsBefore(endTime, startTime) && InputValidation.dateIsEqaul(endDate, date))
                    || InputValidation.dateIsBefore(endDate, date)) {
                System.out.println(
                        "Time entered is before or at the start time. Please enter a time after the start time: ("
                                + startTime + ")");
                endTime = scanner.nextLine();
                endTimeNotValid = true;
            }
        } while (endTimeNotValid);
        if (updatedDB(updated)) {
            scanner.close();
            return null;
        }

        System.out.println("Please enter a description for your event:");
        String description = scanner.nextLine();
        if (updatedDB(updated)) {
            scanner.close();
            return null;
        }

        System.out.println("How many people will be attending the event?");
        String attendeeCountString = scanner.nextLine();
        attendeeCountString = checkIntLoop(attendeeCountString);
        int number = Integer.parseInt(attendeeCountString);
        String attendees = (number == 0) ? null : "";
        String attendee;
        for (int i = 0; i < number; i++) {
            System.out.println("Enter the name of attendee " + (i + 1) + ":");
            attendee = scanner.nextLine();
            attendees += attendee;
            if ((i + 1) < number) {
                attendees += ",";
            }
        }
        if (updatedDB(updated)) {
            scanner.close();
            return null;
        }
        scanner.close();
        String[] eventDetails = { calendarId, eventId, name, date, endDate, startTime, endTime, description, attendees, database };

        return eventDetails;
    }

    private static boolean updatedDB(Future<String> updated) {
        if (updated.isDone()) {
            try {
                if (updated.get().equals("stop")) {
                    return true;
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String[] eventViewWizard() {
        Scanner scanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(scanner)));
        String[] eventDetails = new String[2];
        
        System.out.println("Please enter the calendar ID of the calendar from which you'd like to view an event:");
        String calendarId = scanner.nextLine();
        calendarId = InputValidation.calendarIdValidationLoop(calendarId, scanner);
        eventDetails[0] = calendarId;
        
        System.out.println("Please enter the Event ID of the event you would like to view:");
        String eventId = scanner.nextLine();
        eventId = InputValidation.requiredFieldLoop(eventId, scanner);
        eventId = InputValidation.checkEventExistsLoop(calendarId, eventId, scanner, "view");
        eventDetails[1] = eventId;
        
        scanner.close();
        return eventDetails;
    }

    public static String[] eventEditWizard() {
        Scanner scanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(scanner)));
        String calendarId = "";
        String eventId = "";
        String delete = "false";
        String name = null;
        String date = null;
        String endDate = null;
        String startTime = null;
        String endTime = null;
        String description = null;
        String attendees = null;
        String replaceAttendees = "false";
        String database = "false";
        String[] eventDetails = {calendarId, eventId, delete, name, date, endDate, startTime, endTime, description, attendees, replaceAttendees, database};
        String[] parametersArray = {"name", "date", "end date", "start time", "end time", "description", "attendees"};
        System.out.println("Please enter the calendar ID of the calendar from which you'd like to edit an event:");
        calendarId = scanner.nextLine();
        calendarId = InputValidation.calendarIdValidationLoop(calendarId, scanner);
        eventDetails[0] = calendarId;
        
        System.out.println("Please enter the Event ID of the event you would like to edit:");
        eventId = scanner.nextLine();
        eventId = InputValidation.requiredFieldLoop(eventId, scanner);
        eventId = InputValidation.checkEventExistsLoop(calendarId, eventId, scanner, "edit");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> updated = executor.submit(new WizardListener(calendarId, eventId, "event"));

        eventDetails[1] = eventId;
        Calendar calendar = Calendar.getCalendarById(calendarId);
        Event event = calendar.getEventById(eventId);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HHmm");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("ddMMyyyy");
        String currentStartDate = event.startDateTime.format(dateFormat);
        String currentStartTime = event.startDateTime.format(timeFormat);
        String currentEndDate = event.endDateTime.format(dateFormat);
        String currentEndTime = event.endDateTime.format(timeFormat);
        if(event.startDateTime.equals(event.endDateTime)) {
            currentEndDate = "";
            currentEndTime = "";
        }
        String[] currentDateTimes = {currentStartDate, currentEndDate, currentStartTime, currentEndTime};
        
        System.out.println("Would you like these event updates to be pushed to the database? (y/n)");
        String databaseResponse = scanner.nextLine();
        if (databaseResponse.equals("y") || databaseResponse.equals("yes")) {
            database = "true";
            eventDetails[11] = database;
        }

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
            do {
                if (updatedDB(updated)) {
                    scanner.close();
                    return null;
                }
                System.out.println("What event detail would you like to change? (name, date, enddate, starttime, endtime, description, attendees)");
                parameter = scanner.nextLine().toLowerCase();
                switch (parameter) {
                    case "attendees":
                        System.out.println("Would you like to replace the current event attendee(s)? (y/n) (otherwise will add to attendee list)");
                        String replaceResponse = scanner.nextLine();
                        if (replaceResponse.equals("y") || replaceResponse.equals("yes")) {
                            replaceAttendees = "true";
                            eventDetails[10] = replaceAttendees;
                        }
                        System.out.println("Please enter the number of attendees you want to include:");
                        String attendeeCountString = scanner.nextLine();
                        attendeeCountString = checkIntLoop(attendeeCountString);
                        int attendeeCount = Integer.parseInt(attendeeCountString);
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
                        break;
                    case "name":
                        System.out.println("Please enter the value you would like to enter for " + parameter + ":");
                        name = scanner.nextLine();
                        eventDetails[3] = name;
                        break;
                    case "date":
                        System.out.println("Please enter the value you would like to enter for " + parameter + ":");
                        date = scanner.nextLine();
                        date = InputValidation.requiredFieldLoop(date, scanner);
                        date = InputValidation.timeDateValidationLoop(date, currentDateTimes, 0, scanner);
                        currentDateTimes[0] = date;
                        eventDetails[4] = date;
                        if (currentDateTimes[1].equals("")) {
                            eventDetails[5] = date;
                        }
                        break;
                    case "enddate":
                        System.out.println("Please enter the value you would like to enter for " + parameter + ":");
                        endDate = scanner.nextLine();
                        endDate = InputValidation.timeDateValidationLoop(endDate, currentDateTimes, 1, scanner);
                        currentDateTimes[1] = endDate;
                        eventDetails[5] = endDate;
                        break;
                    case "starttime":
                        System.out.println("Please enter the value you would like to enter for " + parameter + ":");    
                        startTime = scanner.nextLine();
                        startTime = InputValidation.timeDateValidationLoop(startTime, currentDateTimes, 2, scanner);
                        currentDateTimes[2] = startTime;
                        eventDetails[6] = startTime;
                        if (currentDateTimes[3].equals("")) {
                            eventDetails[7] = startTime;
                        }
                        break;
                    case "endtime":
                        System.out.println("Please enter the value you would like to enter for " + parameter + ":");    
                        endTime = scanner.nextLine();
                        endTime = InputValidation.timeDateValidationLoop(endTime, currentDateTimes, 3, scanner);
                        currentDateTimes[3] = endTime;
                        eventDetails[7] = endTime;
                        break;
                    case "description":
                        System.out.println("Please enter the value you would like to enter for " + parameter + ":");
                        description = scanner.nextLine();
                        eventDetails[8] = description;
                        break;
                    default:
                        System.out.println("invalid parameter");
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
                if (updatedDB(updated)) {
                    scanner.close();
                    return null;
                }
                System.out.println("Would you like to edit your event's " + parametersArray[i] + "? (y/n)");
                changeResponse = scanner.nextLine();
                if (!changeResponse.equals("y") && !changeResponse.equals("yes")) {
                    continue;
                }
                if (i < parametersArray.length - 1) {
                    System.out.println("Please enter the value you want to change your event's " + parametersArray[i] + " to:");
                    String responseValue = scanner.nextLine();
                    if (i >= 1 && i <= 4 ) {
                        if (i == 1) {
                            responseValue = InputValidation.requiredFieldLoop(responseValue, scanner);
                        }
                        responseValue = InputValidation.timeDateValidationLoop(responseValue, currentDateTimes, i - 1, scanner);
                        currentDateTimes[i - 1] = responseValue;
                    }
                    eventDetails[i + 3] = responseValue;
                    if ((i == 1 || i == 3) && currentDateTimes[i].equals("")) {
                        eventDetails[i + 4] = responseValue;
                    }
                } else {
                    System.out.println("Would you like to replace the current event attendee(s)? (y/n) (otherwise will add to attendee list)");
                    String replaceResponse = scanner.nextLine();
                    if (replaceResponse.equals("y") || replaceResponse.equals("yes")) {
                        replaceAttendees = "true";
                        eventDetails[10] = replaceAttendees;
                    }
                    System.out.println("Please enter the number of attendees you want to include:");
                    String attendeeCountString = scanner.nextLine();
                    attendeeCountString = checkIntLoop(attendeeCountString);
                    int attendeeCount = Integer.parseInt(attendeeCountString);
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

	private static String checkIntLoop(String attendeeCountString) {
        return attendeeCountString;
    }

    public static Boolean createCalendarInDB(String calendarId) {
		return true;
	}

	public static Boolean getUpdatedCalendarFromDB(String calendarId) {
		return true;
	}

	public static Boolean continueEventEdit(String calendarId, String eventId) {
		return true;
	}

	public static Boolean replaceCalendarInDB(String calendarId) {
		return null;
	}

	public static boolean replaceEventInDB(String calendarId, String eventId) {
		return false;
	}

}

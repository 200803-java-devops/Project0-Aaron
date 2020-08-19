package com.Revature.AaronDownward.Project0.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;

public class Calendar {

    public String id;
    public LocalDateTime databaseTimestamp;
    public Map<String, Event> events;

    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    //constructor to intialize a new calendar with given id
    public Calendar(String id) {
        this.id = id;
        this.events = new HashMap<String, Event>();
    }

    //creates a new calendar with a given id and adds it to file
    public static void createCalendar(String id) {
        Calendar calendar = new Calendar(id);
        //@ToWorkOn implement method/use code to create filepath in robust way instead of hardcoding for my system
        String filepath = "C:\\Users\\downw\\Revature Training\\Project0\\Project0-Aaron\\user_data\\" + calendar.id + ".json";
        createCalendarFile(calendar, filepath);
        writeToCalendarFile(calendar, filepath);
    }

    //creates new event, adding to hashmap events with given id key and editing the calendar file
    public void createEvent(String eventId, String name, String date, String endDate, String startTime, String endTime,	String description, List<String> attendees) {
        this.events.put(eventId, new Event(eventId, this.id, name, date, endDate, startTime, endTime, description, attendees));
        String filepath = "C:\\Users\\downw\\Revature Training\\Project0\\Project0-Aaron\\user_data\\" + this.id + ".json";
        writeToCalendarFile(this, filepath);
	}

    public void createEvent(String[] eventDetails) {
        String eventId = eventDetails[0];
        this.events.put(eventId, new Event(eventDetails));
        String filepath = "C:\\Users\\downw\\Revature Training\\Project0\\Project0-Aaron\\user_data\\" + this.id + ".json";
        writeToCalendarFile(this, filepath);
    }
    
    public void createEvent(String eventId, String eventName, LocalDateTime startDateTime, LocalDateTime endDateTime,String description, ArrayList<String> attendees) {
        this.events.put(eventId, new Event(eventId, this.id, eventName, startDateTime, endDateTime, description, attendees));
        String filepath = "C:\\Users\\downw\\Revature Training\\Project0\\Project0-Aaron\\user_data\\" + this.id + ".json";
        writeToCalendarFile(this, filepath);

    }
    
    //creates a new .json file for a calendar if it doesn't exist
    private static void createCalendarFile(Calendar calendar, String filepath) {
        try {
            File file = new File(filepath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                //System.out.println("File already exists.");
            }
        } catch (IOException e) {
            //System.err.println("Unable to create file");
            e.printStackTrace();
        }
    }

    //writes json formatted text to the calendar.json file
    public static void writeToCalendarFile(Calendar calendar, String filepath) {
        try {
            String textForFile = createJSONFromCalendar(calendar);
            FileWriter writer = new FileWriter(filepath);
            writer.write(textForFile);
            writer.close();
            //System.out.println("Successfully wrote to file");
        } catch (IOException e) {
            //System.err.println("Problem while writing to file");
            e.printStackTrace();
        }
    }

    //formats a calendar object into json text
    //somewhat brute force method of converting/writing object as json, considering finding more elegant/easier to control/edit method
    private static String createJSONFromCalendar(Calendar calendar) {
        String json = "{\n\t\"calendarId\": \"" + calendar.id + "\",\n\t\"events\": [";
        int eventCounter = 0;
        for (Event event : calendar.events.values()) {
            eventCounter++;
            json += "\n\t\t{\n\t\t\t\"eventId\": \"" + event.id + "\",";
            json += "\n\t\t\t\"calendarId\": \"" + event.calendarId + "\",";
            json += "\n\t\t\t\"eventName\": \"" + event.name + "\",";
            json += "\n\t\t\t\"startDateTime\": \"" + event.startDateTime.format(Calendar.dateTimeFormat) + "\",";
            json += "\n\t\t\t\"endDateTime\": \"" + event.endDateTime.format(Calendar.dateTimeFormat) + "\",";
            json += "\n\t\t\t\"description\": \"" + event.description + "\",";
            json += "\n\t\t\t\"attendees\": [";
            for (int i = 0; i < event.attendees.size(); i++) {
                json += "\n\t\t\t\t{\n\t\t\t\t\t\"attendeeName\": \"" + event.attendees.get(i) + "\"\n\t\t\t\t}";
                if (i < event.attendees.size() - 1) { 
                    json += ","; 
                }             
            }
            json += "\n\t\t\t]\n\t\t}";
            if (eventCounter < calendar.events.size()) { 
                json += ",";
            }
        }
        json += "\n\t]\n}";
        
        return json;
    }

    //returns the Event with the given id
	public Event getEventById(String eventId) {
		return this.events.get(eventId);
    }
    
    //accesses the json file: calendarId.json and returns a Calendar instantiated to those values
    public static Calendar getCalendarById(String calendarId) {
        String filepath = "C:\\Users\\downw\\Revature Training\\Project0\\Project0-Aaron\\user_data\\" + calendarId + ".json";
        ArrayList<String> textFromFile = readCalendarFile(filepath);
        Calendar calendar = createCalendarObjFromFile(textFromFile);
		return calendar;
	}

    //uses the text from a calendarId.json file and constructs an object initialized to values in text
    //somewhat brute force method and prone to problems if json file doesn't follow exact format
    private static Calendar createCalendarObjFromFile(ArrayList<String> textFromFile) {
        if (textFromFile == null) {
            return null;
        }
        String calendarId = returnJSONValue(textFromFile.get(1));
        Calendar calendar = new Calendar(calendarId);
        Event event;
        
        for(int i = 4; i < textFromFile.size(); i++) {
            if(textFromFile.get(i).contains("eventId")) {
                String eventId = returnJSONValue(textFromFile.get(i++));
                //already have calendarId variable store, skip to next line
                i++;
                String eventName = returnJSONValue(textFromFile.get(i++));
                String startDateTime = returnJSONValue(textFromFile.get(i++));
                String date = dateFromFormattedDateTime(startDateTime);
                String startTime = timeFromFormattedDateTime(startDateTime);

                String endDateTime = returnJSONValue(textFromFile.get(i++));
                String endDate = dateFromFormattedDateTime(endDateTime);
                String endTime = timeFromFormattedDateTime(endDateTime);
                
                String description = returnJSONValue(textFromFile.get(i));
                ArrayList<String> attendees = new ArrayList<String>();
                while (textFromFile.get(i + 3).contains("attendeeName")) {
                    i += 3;
                    attendees.add(returnJSONValue(textFromFile.get(i)));
                }
                event = new Event(eventId, calendarId, eventName, date, endDate, startTime, endTime, description, attendees);
                calendar.events.put(eventId, event);
            }
        }
        return calendar;
    }

    private static String timeFromFormattedDateTime(String formattedDateTime) {
        String time = "";
        String hour = formattedDateTime.substring(11, 13);
        String min = formattedDateTime.substring(14, 16);
        time = hour + min;
        return time;
    }

    private static String dateFromFormattedDateTime(String formattedDateTime) {
        String date = "";
        String day = formattedDateTime.substring(0, 2);
        String month = formattedDateTime.substring(3, 5);
        String year = formattedDateTime.substring(6, 10);
        date = day + month + year;
        return date;
    }

    // extracts the value from a line of a JSON code
    //pulls value from between the second set of quotation marks
    //ex:           "key": "value"
    private static String returnJSONValue(String jsonLine) {
        int endIndex = jsonLine.lastIndexOf('\"');
        int startIndex = jsonLine.lastIndexOf('\"', endIndex - 1) + 1;
        String value = jsonLine.substring(startIndex, endIndex);
        return value;
    }

    //returns a string array list of each line from a text file
    public static ArrayList<String> readCalendarFile(String filepath) {
        ArrayList<String> textFromFile = new ArrayList<String>();
        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                textFromFile.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            //System.err.println("Problem finding or scanning file");
            return null;
        } catch (Exception e) {
            //System.err.println("Some other problem happened");
            return null;
        }
        return textFromFile;
    }

	public void deleteEvent(String eventId) {
        events.remove(eventId);
        String filepath = "C:\\Users\\downw\\Revature Training\\Project0\\Project0-Aaron\\user_data\\" + this.id + ".json";
        Calendar.writeToCalendarFile(this, filepath);
	}

	public void replaceEvent(Event event, String eventId) {
        this.events.put(eventId, event);
	}

}
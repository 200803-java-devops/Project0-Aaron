package com.Revature.AaronDownward.Project0;

import java.util.HashMap;

public class Calendar {

    private String id;
    private HashMap<String, Event> events;

    //constructor to intialize a new calendar with given id
    public Calendar(String id) {
        this.id = id;
        this.events = new HashMap<String, Event>();
    }

    //creates new event, adding to hashmap events with given id key
    public void createEvent(String id, String[] details) {
        this.events.put(id, new Event(id, details));
    }

    //prints a calendar with a given standard display format
    //for now will implement displaying a day and week, then will work towards other displays
	public void print(String displayFormat) {
	}

    //prints a calendar with a custom start and end date 
	public void print(String displayFormat, String startDate, String endDate) {
	}

    //returns the Event with the given id
	public Event getEventById(String eventId) {
		return this.events.get(eventId);
    }
    
    //accesses database and returns a Calendar with the given id
    public static Calendar getCalendarById(String calId) {
		return null;
	}

    //creates a new calendar with a given id and adds it to database
    public static void createCalendar(String id) {
    }


}
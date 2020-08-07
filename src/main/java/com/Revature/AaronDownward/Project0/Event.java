package com.Revature.AaronDownward.Project0;

import java.util.ArrayList;

public class Event {

    public String id;
    public String calendarId;
    public String name;
    public String date;
    public String startTime;
    public String endTime;
    public String description;
    public ArrayList<String> attendees;

    //constructor that initializes id and other parameters from a String array
    //will return to make this implementation much more resilient
    public Event(String eventId, String calendarId, String[] params) {
        this.id = eventId;
        this.calendarId = calendarId;
        this.name = params[0];
        this.date = params[1];
        this.startTime = params[2];
        this.endTime = params[3];
        this.description = "";
        for(int i = 4; i < params.length; i++) {
            description += params[i];
            if (i != params.length - 1) {
                description += " ";
            }
        }
        this.attendees = new ArrayList<String>();
        this.attendees.add("Aaron Downward");
    }

    public Event(String id, String calendarId, String name, String date, String startTime, String endTime, String description, ArrayList<String> attendees) {
        this.id = id;
        this.calendarId = calendarId;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.attendees = attendees;
    }

    //prints the details of an event in a basic format
	public void print() {
        System.out.println("Event: " + this.name);
        System.out.println(this.date + "\t" + this.startTime + " - " + this.endTime);
        System.out.println("Description: " + this.description);
        System.out.println("Attendees: " + this.attendees);
	}
}
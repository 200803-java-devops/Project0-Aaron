package com.Revature.AaronDownward.Project0;

import java.util.ArrayList;
import java.util.List;

public class Event {

    //@ToWorkOn create getters and setters and replace all manual gets/sets with them
    public String id;
    public String calendarId;
    public String name;
    public String date;
    //@ToWorkOn implement using formatted dates and times
    public String endDate;
    public String startTime;
    public String endTime;
    public String description;
    public List<String> attendees;

    //constructor with all parameters passed in
    public Event(String id, String calendarId, String name, String date, String endDate, String startTime, String endTime, String description, List<String> attendees) {
        this.id = (id != null)? id : "";
        this.calendarId = (calendarId != null)? calendarId : "";
        this.name = (name != null)? name : "";
        this.date = (date != null)? date : "";
        this.endDate = (endDate != null)? endDate : "";
        this.startTime = (startTime != null)? startTime : "";
        this.endTime = (endTime != null)? endTime : "";
        this.description = (description != null)? description : "";
        this.attendees = attendees;
    }

    public Event(String[] eventDetails) {
        this.calendarId = eventDetails[0];
        this.id = eventDetails[1];
        this.name = eventDetails[2];
        this.date = eventDetails[3];
        this.endDate = eventDetails[4];
        this.startTime = eventDetails[5];
        this.endTime = eventDetails[6];
        this.description = eventDetails[7];
        ArrayList<String> attendees = new ArrayList<String>();
        if (!this.description.equals("")) {
            String attendeesArray[] = eventDetails[8].split(",");
            for (String attendee : attendeesArray) {
                attendees.add(attendee);
            }  
        }
        this.attendees = attendees;
	}

	public void editEvent(String name, String date, String endDate, String startTime, String endTime, String description, String[] attendees, boolean replaceAttendees) {
        if (name != null) {
            this.name = name;
        }
        if (date != null) {
            this.date = date;
        }
        if (endDate != null) {
            this.endDate = endDate;
        }
        if (startTime != null) {
            this.startTime = startTime;
        }
        if (endTime != null) {
            this.endTime = endTime;
        }
        if (description != null) {
            this.description = description;
        }
        if (attendees != null) {
            if (replaceAttendees) {
                this.attendees = new ArrayList<String>();
            }
            for (String attendee : attendees) {
                this.attendees.add(attendee);
            }
        }
        String filepath = "C:\\Users\\downw\\Revature Training\\Project0\\Project0-Aaron\\user_data\\" + this.calendarId + ".json";
        Calendar calendar = Calendar.getCalendarById(calendarId);
        calendar.replaceEvent(this, this.id);
        Calendar.writeToCalendarFile(calendar, filepath);
	}

}
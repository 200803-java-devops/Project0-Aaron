package com.Revature.AaronDownward.Project0.Objects;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

public class Event {

    //@ToWorkOn create getters and setters and replace all manual gets/sets with them
    public String id;
    public String calendarId;
    public String name;
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
    public String description;
    public List<String> attendees;

    //constructor with all parameters passed in separately as Strings with a List<String> object for attendees
    //will require that input for creating events requires an existing calendar ID, unique event ID, and date.
    //will format date strings to ddMMyyyy will format time strings to HHmm based on 24-hour clock
    //will ensure that end time/date isn't before start time chronologically
    //will ensure that no null values are passed in. Blank fields will be passed in as empty strings
    public Event(String id, String calendarId, String name, String date, String endDate, String startTime, String endTime, String description, List<String> attendees) {
        this.id = id;
        this.calendarId = calendarId;
        this.name = name;
        int startYear = Integer.parseInt(date.substring(4));
        int startMonth = Integer.parseInt(date.substring(2, 4));
        int startDay = Integer.parseInt(date.substring(0, 2));
        int startHour = 0;
        int startMin = 0;
        if (startTime != "") {
            startHour = Integer.parseInt(startTime.substring(0,2));
            startMin = Integer.parseInt(startTime.substring(2));
        }
        this.startDateTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMin);
        int endYear = startYear;
        int endMonth = startMonth;
        int endDay = startDay;
        if (endDate != "") {
            endYear = Integer.parseInt(endDate.substring(4));
            endMonth = Integer.parseInt(endDate.substring(2, 4));
            endDay = Integer.parseInt(endDate.substring(0, 2));
        }
        int endHour = startHour;
        int endMin = startMin;
        if (endTime != "") {
            endHour = Integer.parseInt(endTime.substring(0,2));
            endMin = Integer.parseInt(endTime.substring(2));
        }
        this.endDateTime = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMin);
        this.description = description;
        this.attendees = attendees;
    }

    public Event(String eventId, String calendarId, String eventName, LocalDateTime startDateTime, LocalDateTime endDateTime, String description, ArrayList<String> attendees) {
        this.id = eventId;
        this.calendarId = calendarId;
        this.name = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.attendees = attendees;
    }

    //constructor with all parameters passed in a String[] = {calendarId, eventId, name, date, endDate, startTime, endTime, description, attendees}
    //will require that input for creating events requires an existing calendar ID, unique event ID, and date.
    //will format date strings to ddMMyyyy will format time strings to HHmm based on 24-hour clock
    //will ensure that end time/date isn't before start time chronologically
    //will ensure that no null values are passed in except for attendees value where null indicates 0 attendees. Blank fields will be passed in as empty strings
    public Event(String[] eventDetails) {
        this.calendarId = eventDetails[0];
        this.id = eventDetails[1];
        this.name = eventDetails[2];
        String date = eventDetails[3];
        String startTime = eventDetails[5];
        int startYear = Integer.parseInt(date.substring(4));
        int startMonth = Integer.parseInt(date.substring(2, 4));
        int startDay = Integer.parseInt(date.substring(0, 2));
        int startHour = 0;
        int startMin = 0;
        if (!startTime.equals("")) {
            startHour = Integer.parseInt(startTime.substring(0,2));
            startMin = Integer.parseInt(startTime.substring(2));
        }
        this.startDateTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMin);
        String endDate = eventDetails[4];
        String endTime = eventDetails[6];
        int endYear = startYear;
        int endMonth = startMonth;
        int endDay = startDay;
        if (!endDate.equals("")) {
            endYear = Integer.parseInt(endDate.substring(4));
            endMonth = Integer.parseInt(endDate.substring(2, 4));
            endDay = Integer.parseInt(endDate.substring(0, 2));
        }
        int endHour = startHour;
        int endMin = startMin;
        if (!endTime.equals("")) {
            endHour = Integer.parseInt(endTime.substring(0,2));
            endMin = Integer.parseInt(endTime.substring(2));
        }
        this.endDateTime = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMin);
        this.description = eventDetails[7];
        ArrayList<String> attendees = new ArrayList<String>();
        if (eventDetails[8] != null) {
            String attendeesArray[] = eventDetails[8].split(",");
            for (String attendee : attendeesArray) {
                attendees.add(attendee);
            }  
        }
        this.attendees = attendees;
	}

	//values that are to be edited will be passed in as non-null values
    //will format date strings to ddMMyyyy will format time strings to HHmm based on 24-hour clock
    //will ensure that end time/date isn't before start time chronologically
	public void editEvent(String name, String date, String endDate, String startTime, String endTime, String description, String[] attendees, boolean replaceAttendees) {
        if (name != null) {
            this.name = name;
        }
        if (date != null || startTime != null) {
            int startYear = this.startDateTime.getYear();
            int startMonth = this.startDateTime.getMonthValue();
            int startDay = this.startDateTime.getDayOfMonth();
            int startHour = this.startDateTime.getHour();
            int startMin = this.startDateTime.getMinute();
            
            if (date != null) {
                startYear = Integer.parseInt(date.substring(4));
                startMonth = Integer.parseInt(date.substring(2, 4));
                startDay = Integer.parseInt(date.substring(0, 2));
            }
            if (startTime != null) {
                startHour = Integer.parseInt(startTime.substring(0,2));
                startMin = Integer.parseInt(startTime.substring(2));
            }
            this.startDateTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMin);
        }
        if (endDate != null || endTime != null) {
            int endYear = this.endDateTime.getYear();
            int endMonth = this.endDateTime.getMonthValue();
            int endDay = this.endDateTime.getDayOfMonth();
            int endHour = this.endDateTime.getHour();
            int endMin = this.endDateTime.getMinute();
            
            if (endDate != null) {
                endYear = Integer.parseInt(endDate.substring(4));
                endMonth = Integer.parseInt(endDate.substring(2, 4));
                endDay = Integer.parseInt(endDate.substring(0, 2));
            }
            if (endTime != null) {
                endHour = Integer.parseInt(endTime.substring(0,2));
                endMin = Integer.parseInt(endTime.substring(2));
            }
            this.endDateTime = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMin);
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
package com.Revature.AaronDownward.Project0;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataParse {
    

    public static void createCalendarFromRS(ResultSet rs, String calendarId) {
        Calendar calendar = new Calendar(calendarId);
        Calendar.createCalendar(calendarId);
        String eventId = "";
        String eventName = "";
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        String description = "";
        ArrayList<String> attendees = new ArrayList<String>();
        try {
            while(rs.next()) {
                if (eventId.equals(rs.getString("event_id"))) {
                    attendees.add(rs.getString("attendee"));

                } else {
                    if (!eventId.equals("")) {
                        calendar.createEvent(eventId, eventName, startDateTime, endDateTime, description, attendees);
                    }
                    eventId = rs.getString("event_id");
                    eventName = rs.getString("event_name");
                    startDateTime = LocalDateTime.of(rs.getDate("event_start_date").toLocalDate(), rs.getTime("start_time").toLocalTime());
                    endDateTime = LocalDateTime.of(rs.getDate("end_date").toLocalDate(), rs.getTime("end_time").toLocalTime());
                    description = rs.getString("event_description");
                    attendees = new ArrayList<String>();
                    attendees.add(rs.getString("attendee"));                            
                }
           }
           calendar.createEvent(eventId, eventName, startDateTime, endDateTime, description, attendees);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Event createEventFromRS(ResultSet rs, String calendarId, String eventId) {
        Event detachedEvent = null;
        eventId = "";
        String eventName = "";
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        String description = "";
        ArrayList<String> attendees = new ArrayList<String>();
        try {
            while(rs.next()) {
                if (eventId.equals("")) {
                    eventId = rs.getString("event_id");
                    eventName = rs.getString("event_name");
                    startDateTime = LocalDateTime.of(rs.getDate("event_start_date").toLocalDate(), rs.getTime("start_time").toLocalTime());
                    endDateTime = LocalDateTime.of(rs.getDate("end_date").toLocalDate(), rs.getTime("end_time").toLocalTime());
                    description = rs.getString("event_description");
                    attendees.add(rs.getString("attendee"));    
                } else {
                    attendees.add(rs.getString("attendee"));
            }
        }
            detachedEvent = new Event(eventId, "", eventName, startDateTime, endDateTime, description, attendees);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detachedEvent;
    }
}
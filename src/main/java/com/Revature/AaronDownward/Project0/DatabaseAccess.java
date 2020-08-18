package com.Revature.AaronDownward.Project0;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DatabaseAccess {
    
    public static ResultSet getCalendarFromDB(String calendarId) {
        ResultSet rs = null;
        String query = "SELECT * FROM events JOIN calendars ON calendars.calendar_id = events.calendar_id JOIN attendees ON events.calendar_id = attendees.calendar_id AND events.event_id = attendees.event_id WHERE calendars.calendar_id = ? ORDER BY events.event_id ASC;";
        PreparedStatement statement;
        try {
            statement = ConnectionUtil.getConnection().prepareStatement(query);
            statement.setString(1, calendarId);
            rs = statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet getEventFromDB(String calendarId, String eventId) {
        ResultSet rs = null;
        String query = "SELECT * FROM events JOIN attendees ON events.calendar_id = attendees.calendar_id AND events.event_id = attendees.event_id WHERE events.calendar_id = ? AND events.event_id = ? ORDER BY events.event_id ASC;";
        PreparedStatement statement;
        try {
            statement = ConnectionUtil.getConnection().prepareStatement(query);
            statement.setString(1, calendarId);
            statement.setString(2, eventId);
            rs = statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static boolean addEventToCalendarDB(String calendarId, Event event) {
        String eventInsert = "INSERT INTO events VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String calendarUpdate = "UPDATE calendars SET calendar_last_edit = ? WHERE calendar_id = ?;";
        String attendeesInsert = "INSERT INTO attendees VALUES (?, ?, ?);";
        PreparedStatement statement;
        try {
            statement = ConnectionUtil.getConnection().prepareStatement(calendarUpdate);
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(2, calendarId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        try {
            statement = ConnectionUtil.getConnection().prepareStatement(eventInsert);
            statement.setString(1, event.calendarId);
            statement.setString(2, event.id);
            statement.setString(3, event.name);
            statement.setDate(4, Date.valueOf(event.startDateTime.toLocalDate()));
            statement.setTime(5, Time.valueOf(event.startDateTime.toLocalTime()));
            statement.setDate(6, Date.valueOf(event.endDateTime.toLocalDate()));
            statement.setTime(7, Time.valueOf(event.endDateTime.toLocalTime()));
            statement.setString(8, event.description);
            statement.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        try {
            statement = ConnectionUtil.getConnection().prepareStatement(attendeesInsert);
            for (String attendee : event.attendees) {
                statement.setString(1, event.calendarId);
                statement.setString(2, event.id);
                statement.setString(3, attendee);
                statement.addBatch();
            }
            statement.executeBatch();       
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean addCalendarToDB(String calendarId) {
        String calendarInsert = "INSERT INTO calendars VALUES (?, ?, ?);";
        String eventInsert = "INSERT INTO events VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String attendeesInsert = "INSERT INTO attendees VALUES (?, ?, ?);";
        PreparedStatement statement;
        PreparedStatement attendeeStatement;
        Calendar calendar = Calendar.getCalendarById(calendarId);
        try {
            statement = ConnectionUtil.getConnection().prepareStatement(calendarInsert);
            statement.setString(1, calendarId);
            statement.setString(2, ConnectionUtil.username);
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        try {
            statement = ConnectionUtil.getConnection().prepareStatement(eventInsert);
            for (Event event : calendar.events.values()) {
                statement.setString(1, event.calendarId);
                statement.setString(2, event.id);
                statement.setString(3, event.name);
                statement.setDate(4, Date.valueOf(event.startDateTime.toLocalDate()));
                statement.setTime(5, Time.valueOf(event.startDateTime.toLocalTime()));
                statement.setDate(6, Date.valueOf(event.endDateTime.toLocalDate()));
                statement.setTime(7, Time.valueOf(event.endDateTime.toLocalTime()));
                statement.setString(8, event.description);
                statement.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                statement.addBatch();
                attendeeStatement = ConnectionUtil.getConnection().prepareStatement(attendeesInsert);
                for (String attendee : event.attendees) {
                    attendeeStatement.setString(1, event.calendarId);
                    attendeeStatement.setString(2, event.id);
                    attendeeStatement.setString(3, attendee);
                    attendeeStatement.addBatch();
                }
                attendeeStatement.executeBatch();
            }
            statement.executeBatch();       
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
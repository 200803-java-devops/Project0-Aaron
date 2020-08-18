package com.Revature.AaronDownward.Project0.Objects;

import java.time.format.DateTimeFormatter;

public class Print {

	private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy");
	private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
	private static DateTimeFormatter timewithWeekDayFormat = DateTimeFormatter.ofPattern("EEEE HH:mm");

	public static void printDay(String calendarId, String dayDate) {
        System.out.println("printing day: " + dayDate + " of calendar: " + calendarId);
	}

	public static void printWeek(String calendarId, String weekDate) {
        System.out.println("printing week starting from: " + weekDate + " of calendar: " + calendarId);
    }

	public static void printMonth(String calendarId, String month) {
        System.out.println("printing month: " + month + " of calendar: " + calendarId);
	}

	public static void printCustom(String calendarId, String[] customDates) {
        System.out.println("printing from: " + customDates[0] + " to: " + customDates[1] + " of calendar: " + calendarId);
	}

	public static void printEvent(String calendarId, String eventId) {
		Calendar calendar = Calendar.getCalendarById(calendarId);
		Event event = calendar.getEventById(eventId);
		System.out.println("Event: " + event.name);

		String dateLine = event.startDateTime.format(dateFormat);
		Boolean isLaterDate = false;
		if (event.endDateTime.getDayOfYear() > event.startDateTime.getDayOfYear() || event.endDateTime.getYear() > event.startDateTime.getYear()) {
			dateLine += " - " + event.endDateTime.format(dateFormat);
			isLaterDate = true;
		}
		System.out.println(dateLine);

		String timeLine = event.startDateTime.format(timeFormat);
		if (!event.endDateTime.equals(event.startDateTime) && !isLaterDate) {
			timeLine += " - " + event.endDateTime.format(timeFormat);
		}
		if (isLaterDate) {
			timeLine += " - " + event.endDateTime.format(timewithWeekDayFormat);
		}
		System.out.println(timeLine);

        System.out.println("Description: " + event.description);
		System.out.print("Attendees: ");
		for (int i = 0; i < event.attendees.size(); i++) {
			System.out.print(event.attendees.get(i));
			if ((i + 1) < event.attendees.size()) {
				System.out.print(", ");
			} else {
				System.out.println("");
			}
		}
	}

}

package com.Revature.AaronDownward.Project0;

public class Print {

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
		System.out.println(event.date + " - " + event.endDate);
		System.out.println(event.startTime + " - " + event.endTime);
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

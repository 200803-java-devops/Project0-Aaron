package com.Revature.AaronDownward.Project0.UserInput;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import com.Revature.AaronDownward.Project0.Objects.Calendar;

public class InputValidation {
    
    public static boolean calendarExists(String calendarId) {
		String filepath = "C:\\Users\\downw\\Revature Training\\Project0\\Project0-Aaron\\user_data\\" + calendarId + ".json";
		ArrayList<String> arrayList = Calendar.readCalendarFile(filepath);
		if (arrayList == null) {
			return false;
		}
		return true;
    }

	//checking to make sure that user input dates are valid dates will accept dates in form:
	//dd/MM/yy or ddMMyy
	//dd/MM/yyyy or d/M/yyyy or d/MM/yyy or dd/M/yyyy or ddMMyyyy
	//dd/MM or d/M or d/MM or dd/M or ddMM
	//the day must be a valid value between 1-31, month a value between 1-12, and the year a valid year
	//two digit years will be assumed to be in the 2000s (20 = 2020) and if no year is given, assumed to be current year
	//today and tomorrow will also be acceptable inputs
	public static String formatDate(String date) {
		if (date == "") {
			return "";
		}
		date = date.toLowerCase();
		String dateRegExNoYear = "\\d{1,2}[/]\\d{1,2}";
        String dateRegExYear = "\\d{1,2}[/]\\d{1,2}[/](\\d{2}|\\d{4})";
		String dateRegExNoSlash = "(\\d{4}|\\d{6}|\\d{8})";
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		String formattedDate = "";
		int day;
		int month;
		int year = today.getYear();
		if (date.matches(dateRegExNoYear)) {
			day = Integer.parseInt(date.substring(0, date.indexOf("/")));
			month = Integer.parseInt(date.substring(date.indexOf("/") + 1));
		} else if (date.matches(dateRegExYear)) {
			int firstSlashIndex = date.indexOf("/");
			int secondSlashIndex = date.indexOf("/", firstSlashIndex + 1);
			day = Integer.parseInt(date.substring(0, firstSlashIndex));
			month = Integer.parseInt(date.substring(firstSlashIndex + 1, secondSlashIndex));
			year = Integer.parseInt(date.substring(secondSlashIndex + 1));
			if (year < 100) {
				year += 2000;
			}
		} else if (date.matches(dateRegExNoSlash)) {
			day = Integer.parseInt(date.substring(0,2));
			month = Integer.parseInt(date.substring(2,4));
			if (date.length() > 4) {
				year = Integer.parseInt(date.substring(4));
				if (date.length() == 6) {
					year += 2000;
				}
			}
		} else if (date.equals("today")) {
			day = today.getDayOfMonth();
			month = today.getMonthValue();
		} else if (date.equals("tomorrow")) {
			day = tomorrow.getDayOfMonth();
			month = tomorrow.getMonthValue();
			year = tomorrow.getYear();
		} else {
			return null;
		}
		if (day == 0 || day > 31 || month == 0 || month > 12) {
			return null;
		}
		String dayString = Integer.toString(day);
		if (day < 10) {
			dayString = "0" + dayString;
		}
		String monthString = Integer.toString(month);
		if (month < 10) {
			monthString = "0" + monthString;
		}
		formattedDate = dayString + monthString + year;
		return formattedDate;
	}

	public static String formatTime(String time) {
		if (time == "") {
			return time;
		}
		time = time.toLowerCase();
		String timeRegExColon = "\\d{1,2}[:]\\d{2}";
		String timeRegExNoColon = "\\d{4}";
		String formattedTime = "";
		LocalTime now = LocalTime.now();
		int hours;
		int minutes;
		if (time.matches(timeRegExColon)) {
			hours = Integer.parseInt(time.substring(0, time.indexOf(":")));
			minutes = Integer.parseInt(time.substring(time.indexOf(":") + 1));
		} else if (time.matches(timeRegExNoColon)) {
			hours = Integer.parseInt(time.substring(0, 2));
			minutes = Integer.parseInt(time.substring(2));
		} else if (time.equals("now")) {
			hours = now.getHour();
			minutes = now.getMinute();
		} else {
			return null;
		}
		if (hours > 23 || minutes > 59) {
			return null;
		}
		String hourString = Integer.toString(hours);
		if (hours < 10) {
			hourString = "0" + hours;
		}
		String minuteString = Integer.toString(minutes);
		if (minutes < 10) {
			minuteString = "0" + minutes;
		}
		formattedTime += hourString + minuteString;
		return formattedTime;
	}

	public static boolean dateIsBefore(String endDate, String startDate) {
		if (endDate.equals("")) {
			return false;
		}
		int laterDay = Integer.parseInt(endDate.substring(0, 2));
		int laterMonth = Integer.parseInt(endDate.substring(2, 4));
		int laterYear = Integer.parseInt(endDate.substring(4));
		LocalDate laterDate = LocalDate.of(laterYear, laterMonth, laterDay);

		int earlierDay = Integer.parseInt(startDate.substring(0, 2));
		int earlierMonth = Integer.parseInt(startDate.substring(2, 4));
		int earlierYear = Integer.parseInt(startDate.substring(4));
		LocalDate earlierDate = LocalDate.of(earlierYear, earlierMonth, earlierDay);

		return laterDate.isBefore(earlierDate);
	}

	public static boolean dateIsEqaul(String endDate, String startDate) {
		if (endDate.equals("")) {
			return true;
		}
		int laterDay = Integer.parseInt(endDate.substring(0, 2));
		int laterMonth = Integer.parseInt(endDate.substring(2, 4));
		int laterYear = Integer.parseInt(endDate.substring(4));
		LocalDate laterDate = LocalDate.of(laterYear, laterMonth, laterDay);

		int earlierDay = Integer.parseInt(startDate.substring(0, 2));
		int earlierMonth = Integer.parseInt(startDate.substring(2, 4));
		int earlierYear = Integer.parseInt(startDate.substring(4));
		LocalDate earlierDate = LocalDate.of(earlierYear, earlierMonth, earlierDay);

		return laterDate.isEqual(earlierDate);
	}

	public static boolean timeIsBefore(String endTime, String startTime) {
		if (endTime.equals("")) {
			return false;
		}
		int earlierHour = 0;
		int earlierMinute = 0;
		if (!startTime.equals("")) {
			earlierHour = Integer.parseInt(startTime.substring(0, 2));
			earlierMinute = Integer.parseInt(startTime.substring(2));
		}
		LocalTime earlierTime = LocalTime.of(earlierHour, earlierMinute);

		int laterHour = Integer.parseInt(endTime.substring(0, 2));;
		int laterMinute = Integer.parseInt(endTime.substring(2));;
		LocalTime laterTime = LocalTime.of(laterHour, laterMinute);

		return laterTime.isBefore(earlierTime);
	}

	public static String requiredFieldLoop(String userInput, Scanner scanner) {
        while (userInput.equals("")) {
            System.out.println("That field is required, please enter a valid input to continue");
            userInput = scanner.nextLine();
        }
        return userInput;
    }

	public static String invalidDateLoop(String date, Scanner scanner) {
		if (date.equals("")) {
			return date;
		}
		date = InputValidation.formatDate(date);
        while (date == null) {
			System.out.println("Invalid date. Please enter date with format dd/mm/yy - ddmmyy, dd/mm/yyy - ddmmyyy, or dd/mm - ddmm (if in current year)");
			date = scanner.nextLine();
            date = requiredFieldLoop(date, scanner);
            date = InputValidation.formatDate(date);
        }
        return date;
	}   
	
	public static String invalidTimeLoop(String time, Scanner scanner) {
		if (time.equals("")) {
			return "";
		}
        time = InputValidation.formatTime(time);
        while (time == null) {
            System.out.println("Invalid time. Please enter a time with format hh:mm or hhmm using a 24-hour clock:");
            time = InputValidation.formatTime(scanner.nextLine());
        }
        return time;
    }

	public static String timeDateValidationLoop(String timeOrDate, String[] currentDateTimes, int i, Scanner scanner) {
		Boolean endDateTimeNotValid;
        do {
			endDateTimeNotValid = false;
			if (i == 0 || i == 1) {
            	timeOrDate = invalidDateLoop(timeOrDate, scanner);
			} else {
				timeOrDate = invalidTimeLoop(timeOrDate, scanner);
			}
			currentDateTimes[i] = timeOrDate;
            if ((timeIsBefore(currentDateTimes[3], currentDateTimes[2]) && dateIsEqaul(currentDateTimes[1],currentDateTimes[0])) || dateIsBefore(currentDateTimes[1], currentDateTimes[0])) {
                System.out.println("The value you entered places the starting date/time of your event after its ending date/time. Please enter in a valid value:");
                timeOrDate = scanner.nextLine();
                endDateTimeNotValid = true;
            }
		} while (endDateTimeNotValid);
		return timeOrDate;
	}

}
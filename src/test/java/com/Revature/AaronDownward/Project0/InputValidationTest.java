package com.Revature.AaronDownward.Project0;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.Revature.AaronDownward.Project0.Objects.Calendar;
import com.Revature.AaronDownward.Project0.UserInput.InputValidation;

import org.junit.Before;
import org.junit.Test;

public class InputValidationTest {
    String todayFormattedString;
    String tomorrowFormattedString;
    String currentTimeFormattedString;
    Calendar testCalendar;
    String calendarId = "testCal";


    @Before
    public void setup() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("ddMMyyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HHmm");
        LocalDateTime now = LocalDateTime.now();
        todayFormattedString = now.format(dateFormat);
        tomorrowFormattedString = now.plusDays(1).format(dateFormat);
        currentTimeFormattedString = now.format(timeFormat);   
        Calendar.createCalendar(calendarId);   
    }
    
    @Test
    public void calendarExistsTests() {
        assertTrue(InputValidation.calendarExists(calendarId));
        assertFalse(InputValidation.calendarExists("invalidID"));
    }

    @Test
    public void dateFormatTests() {
        String expectedDate = "02082020";
        
        String actualDate = InputValidation.formatDate("02/08/2020");
        assertEquals(expectedDate, actualDate);

        actualDate = InputValidation.formatDate("02/08/20");
        assertEquals(expectedDate, actualDate);

        actualDate = InputValidation.formatDate("02/08");
        assertEquals(expectedDate, actualDate);

        actualDate = InputValidation.formatDate("2/08/2020");
        assertEquals(expectedDate, actualDate);

        actualDate = InputValidation.formatDate("02/8/2020");
        assertEquals(expectedDate, actualDate);

        actualDate = InputValidation.formatDate("2/8/2020");
        assertEquals(expectedDate, actualDate);

        actualDate = InputValidation.formatDate("2/8");
        assertEquals(expectedDate, actualDate);

        actualDate = InputValidation.formatDate("02082020");
        assertEquals(expectedDate, actualDate);

        actualDate = InputValidation.formatDate("020820");
        assertEquals(expectedDate, actualDate);

        actualDate = InputValidation.formatDate("0208");
        assertEquals(expectedDate, actualDate);

        assertNull(InputValidation.formatDate("32/1/2020"));
        assertNull(InputValidation.formatDate("1/13/2020"));
        assertNull(InputValidation.formatDate("111"));
        assertNull(InputValidation.formatDate("11111"));
        assertNull(InputValidation.formatDate("1111111"));
        assertNull(InputValidation.formatDate("111111111"));
        assertNull(InputValidation.formatDate("word"));
        
        expectedDate = todayFormattedString;
        actualDate = InputValidation.formatDate("today");
        assertEquals(expectedDate, actualDate);

        expectedDate = tomorrowFormattedString;
        actualDate = InputValidation.formatDate("tomorrow");
        assertEquals(expectedDate, actualDate);

        expectedDate = "";
        actualDate = InputValidation.formatDate("");
        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void timeFormatTests() {
        String expectedTime = "1430";

        String actualTime = InputValidation.formatTime("1430");
        assertEquals(expectedTime, actualTime);

        actualTime = InputValidation.formatTime("14:30");
        assertEquals(expectedTime, actualTime);

        actualTime = InputValidation.formatTime("2:30");
        assertNotEquals(expectedTime, actualTime);

        expectedTime = "0230";
        assertEquals(expectedTime, actualTime);

        assertNull(InputValidation.formatTime("12:5"));
        assertNull(InputValidation.formatTime("25:30"));
        assertNull(InputValidation.formatTime("12:61"));
        assertNull(InputValidation.formatTime("123:50"));
        assertNull(InputValidation.formatTime("12:500"));
        assertNull(InputValidation.formatTime("130"));
        assertNull(InputValidation.formatTime("12345"));
        assertNull(InputValidation.formatTime("wo:rd"));
        
        expectedTime = currentTimeFormattedString;
        actualTime = InputValidation.formatTime("now");
        assertEquals(expectedTime, actualTime);
    }

    @Test
    public void dateIsBeforeTests() {
        assertTrue(InputValidation.dateIsBefore("18082020", "19082020"));
        assertTrue(InputValidation.dateIsBefore("18082020", "18092020"));
        assertTrue(InputValidation.dateIsBefore("18082020", "18082021"));
        assertFalse(InputValidation.dateIsBefore("18082020", "18082020"));
        assertFalse(InputValidation.dateIsBefore("19082020", "18082020"));
        assertFalse(InputValidation.dateIsBefore("18092020", "18082020"));
        assertFalse(InputValidation.dateIsBefore("18082021", "18082020"));
        assertFalse(InputValidation.dateIsBefore("", "18082020"));
    }

    @Test
    public void dateIsEqualTests() {
        assertTrue(InputValidation.dateIsEqaul("18082020", "18082020"));
        assertTrue(InputValidation.dateIsEqaul("", "18082020"));
        assertFalse(InputValidation.dateIsEqaul("19082020", "18082020"));
    }

    @Test
    public void timeIsBeforeTests() {
        assertTrue(InputValidation.timeIsBefore("1230", "1231"));
        assertTrue(InputValidation.timeIsBefore("1230", "1330"));
        assertFalse(InputValidation.timeIsBefore("", "1230"));
        assertFalse(InputValidation.timeIsBefore("1231", "1230"));
        assertFalse(InputValidation.timeIsBefore("1330", "1230"));
    }
}
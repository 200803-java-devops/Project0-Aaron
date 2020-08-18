package com.Revature.AaronDownward.Project0;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InputValidationTest {
    
    @Test
    public void dateFormatTests () {
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
    }
}
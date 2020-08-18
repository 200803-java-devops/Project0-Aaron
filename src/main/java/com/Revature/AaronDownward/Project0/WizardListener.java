package com.Revature.AaronDownward.Project0;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class WizardListener implements Callable<String> {

    private String calendarId;
    private String eventId;
    private String scope; 

    public WizardListener(String calendarId, String eventId, String scope) {
        this.calendarId = calendarId;
        this.eventId = eventId;
        this.scope = scope;
    }

    @Override
    public String call() throws Exception {
        String timestamp;
        if (this.scope.equals("calendar")) {
            timestamp = DatabaseAccess.getCalendarTimeStamp(this.calendarId);
        } else if (this.scope.equals("event")) {
            timestamp = DatabaseAccess.getEventTimestamp(this.calendarId, this.eventId);
        } else {
            System.out.println("Terminating this thread");
            return "";
        }
        String newTimestamp;
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.scope.equals("calendar")) {
                newTimestamp = DatabaseAccess.getCalendarTimeStamp(this.calendarId);
            } else {
                newTimestamp = DatabaseAccess.getEventTimestamp(this.calendarId, this.eventId);
            }
            if (!timestamp.equals(newTimestamp)) {
                System.out.println("A change has been made in the database to the " + this.scope + " you are editing. Exiting the wizard to avoid sync issues...");
                return "stop";
            }
        }
    }
    
}
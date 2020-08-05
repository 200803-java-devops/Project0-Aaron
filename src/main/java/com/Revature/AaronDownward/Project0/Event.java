package com.Revature.AaronDownward.Project0;

public class Event {

    private String id;
    private String name;
    private String date;
    private String startTime;
    private String endTime;
    private String description;
    private String attendees = "Aaron Downward";

    //constructor that initializes id and other parameters from a String array
    //will return to make this implementation much more resilient
    public Event(String id, String[] params) {
        this.id = id;
        this.name = params[0];
        this.date = params[1];
        this.startTime = params[2];
        this.endTime = params[3];
        this.description = "";
        for(int i = 4; i < params.length; i++) {
            description += params[i] + " ";
        }
    }

    //changes the specified parameter of the event to a given value
    public void edit(String param, String value) {
    }

    //prints the details of an event in a basic format
	public void print() {
        System.out.println("Event: " + this.name);
        System.out.println(this.date + "\t" + this.startTime + " - " + this.endTime);
        System.out.println("Description: " + this.description);
        System.out.println("Attendees: " + this.attendees);
	}
}
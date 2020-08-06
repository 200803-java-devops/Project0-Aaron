# Calendar and Scheduling App (awesome name incoming)
A simple Java CLI App that enables the user to schedule and track their schedule. 

The intended framework and scope of the project is still under development.

## Features
- [ ] Allow user to view Calendar with scheduled events
    - [ ] Provide multiple display options with multiple defaults and user specified
        - [ ] Day
        - [ ] Week
        - [ ] Month
        - [ ] User specified time period
- [ ] Allow user to schedule and edit events
    - [ ] Edit date, time, name, location, attendees, etc.
    - [ ] Set-up reminder system
    - [ ] Import events from file
- [ ] Allow user to save and share calendars
    - [ ] Save calendars locally on computer in text file
    - [ ] Import calendars from file
    - [ ] Store and update calendars in cloud database server


## Tech Stack

Java 8
- File I/O
    - [ ] import calendars and events from file
    - [ ] save calendars and events to file
- Collections API
    - [ ] events will be stored in HashMap
- Concurrency
    - [ ] multiple users can retrieve and update the same calendar in database
    - [ ] will allow users to sync calendar to latest
Maven 3
    - [ ] used to build project
JUnit 5
    - [ ] used to extensively test project
Git
    - [ ] used for project version control
PostgreSQL 9+
    - [ ] used to access and manipulate calendar data in a cloud database





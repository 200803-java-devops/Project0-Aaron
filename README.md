# CLalendar - a command line calendar and scheduling app
A simple Java CLI App that enables the user to schedule and track their schedule. 

The intended framework and scope of the project is still under development.

## Features
- [ ] Allow user to view Calendar with scheduled events
    - [ ] Provide multiple display options with multiple defaults and user specified
        - [ ] Day
        - [ ] Week
        - [ ] Month
        - [ ] User specified time period
    - [ ] Provide option to print a nicer looking file holding the calendar*
- [ ] Allow user to schedule and edit events
    - [x] Create events
    - [x] Edit date, time, name, location, attendees, etc.
    - [ ] Set-up reminder system*
    - [ ] Import events from file*
- [ ] Allow user to save and share calendars
    - [x] Save calendars locally on computer in text file
    - [ ] Store and update calendars in cloud database server
    - [ ] Import calendars from file*

*if I have enought time to implement


## Tech Stack

Java 8
- File I/O
    - [ ] import calendars and events from file
    - [x] save calendars and events to file
- Collections API
    - [x] events will be stored in HashMap
    - [x] attendees stored in ArrayList
- Concurrency
    - [ ] multiple users can retrieve and update the same calendar in database
    - [ ] will allow users to sync calendar to latest

Maven 3
- [x] used to build project

JUnit 5
- [ ] used to extensively test project

Git
- [x] used for project version control

PostgreSQL 9+
- [ ] used to access and manipulate calendar data in a cloud database





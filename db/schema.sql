CREATE TABLE events (
    calendar_id VARCHAR,
    event_id VARCHAR,
    event_name VARCHAR,
    event_start_date DATE,
    start_time TIME,
    end_date DATE,
    end_time TIME,
    event_description VARCHAR,
    event_last_edit TIMESTAMP,
    PRIMARY KEY (calendar_id, event_id)
);

INSERT INTO events VALUES ('testcalendar1', '1', 'event 1', '2020-08-17', '16:19:00', '2020-08-17', '18:19:00','this is a description', '2020-08-17 17:10:50');

CREATE TABLE calendars (
    calendar_id VARCHAR PRIMARY KEY,
    calendar_owner VARCHAR,
    calendar_last_edit TIMESTAMP
);

INSERT INTO calendars VALUES ('testcalendar1', 'posgres', '2020-08-17 17:10:50');

CREATE TABLE attendees (
    calendar_id VARCHAR,
    event_id VARCHAR,
    attendee VARCHAR,
    PRIMARY KEY (calendar_id, event_id, attendee)
);

INSERT INTO attendees VALUES ('testcalendar1', '1', 'me');
INSERT INTO attendees VALUES ('testcalendar1', '1', 'you');
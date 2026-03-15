package com.santy.taskorg.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CalendarService {

    /**
     * @param accessToken Security token given to Spring
     * @param title Name of the event
     * @param date The date in format of YYYY-MM-DD
     */
    public void createEvent(String accessToken, String title, LocalDate date) throws Exception {
        Calendar service = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                request -> request.getHeaders().setAuthorization("Bearer " + accessToken)
        ).setApplicationName("Calendar App").build();

        // Event construction
        Event event = new Event().setSummary(title);

        // Date configuration
        // The event starts at 9:00
        DateTime startDateTime = new DateTime(date + "T09:00:00+01:00"); // Spain hour
        EventDateTime start = new EventDateTime().setDateTime(startDateTime);
        event.setStart(start);

        // The event ends at 23:59
        DateTime endDateTime = new DateTime(date + "T23:59:00+01:00");
        EventDateTime end = new EventDateTime().setDateTime(endDateTime);
        event.setEnd(end);

        service.events().insert("primary", event).execute();
        System.out.println("Event " + title + " created successfully");
    }
}

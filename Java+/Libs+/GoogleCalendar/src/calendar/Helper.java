package calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Helper {
    public static final String MY_CALENDAR_ID = "aleks.yablokov@gmail.com";
    public static final String HOLIDAYS_CALENDAR_ID = "en.philippines#holiday@group.v.calendar.google.com";

    public static final String CANCELLED_STATUS = "cancelled";
    public static final int MAX_BATCH_SIZE = 1000;

    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String USER_DIR = System.getProperty("user.home");
    private static final File APP_USER_DIR = new File(USER_DIR, ".google");

    public static Calendar getService(String scope) {
        try {
            var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            var credentials = getCredentials(httpTransport, scope);
            return new Calendar.Builder(httpTransport, JSON_FACTORY, credentials)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Credential getCredentials(final NetHttpTransport httpTransport, String scope) throws IOException {
        var credentials = new File(APP_USER_DIR, "GoogleCalendarCleaner.json");
        var in = new FileReader(credentials);
        var clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);

        var tokenDirName = CalendarScopes.CALENDAR.equals(scope) ? "CALENDAR_SCOPE" : "CALENDAR_READONLY_SCOPE";
        var tokensDirectory = new File(APP_USER_DIR, tokenDirName);
        var scopes = Collections.singletonList(scope);
        var flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(tokensDirectory))
                .setAccessType("offline")
                .build();
        var receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void deleteEvent(Calendar service, String calendarId, String eventId) {
        try {
            service.events().delete(calendarId, eventId).execute();
        } catch (GoogleJsonResponseException e) {
            if (e.getStatusCode() != 410) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Event createEvent(Calendar service, String calendarId, String eventSummary) {
        return createEvent(service, calendarId, eventSummary, null);
    }

    public static Event createEvent(Calendar service, String calendarId, String eventSummary, List<String> recurrence) {
        return createEvent(service, calendarId, eventSummary,
                "2021-10-24T09:30:00", "2021-10-24T11:00:00", recurrence);
    }

    public static Event createEvent(Calendar service, String calendarId, String eventSummary,
                                    String startDate, String endDate, List<String> recurrence) {
        var event = new Event()
                .setSummary(eventSummary)
                .setLocation("Kitchen, Home")
                .setDescription("Ginger tea");

        if (startDate != null) {
            var startDateTime = new DateTime(startDate);
            var start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Europe/Moscow");
            event.setStart(start);
        }

        if (endDate != null) {
            var endDateTime = new DateTime(endDate);
            var end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Europe/Moscow");
            event.setEnd(end);
        }

        var reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        var reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        if (recurrence != null) {
            event.setRecurrence(recurrence);
        }

        try {
            return service.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Event> getEventInstances(Calendar service, String calendarId, String eventId) {
        try {
            var result = new ArrayList<Event>();
            String pageToken = null;
            do {
                var events = service.events().instances(calendarId, eventId)
                        .setPageToken(pageToken).execute();
                result.addAll(events.getItems());
                pageToken = events.getNextPageToken();
            } while (pageToken != null);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Event> get10Events(Calendar service, String calendarId) {
        try {
            var result = new ArrayList<Event>();
            String pageToken = null;
            do {
                var events = service.events().list(calendarId)
                        .setMaxResults(10)
                        .setPageToken(pageToken)
                        .execute();
                result.addAll(events.getItems());
                pageToken = events.getNextPageToken();
            } while (pageToken != null);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<Event>> splitList(List<Event> bigList, int maxSize) {
        if (bigList == null) {
            return List.of();
        }
        if (bigList.isEmpty()) {
            return List.of(bigList);
        }
        var from = 0;
        var to = 0;
        var smallLists = new ArrayList<List<Event>>();
        while (from < bigList.size()) {
            to = Math.min(from + maxSize - 1, bigList.size() - 1);
            smallLists.add(bigList.subList(from, to + 1));
            from = to + 1;
        }
        return smallLists;
    }


}

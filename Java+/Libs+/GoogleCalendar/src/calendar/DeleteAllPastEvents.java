package calendar;

import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static calendar.Helper.CANCELLED_STATUS;
import static calendar.Helper.MAX_BATCH_SIZE;
import static calendar.Helper.splitList;

/**
 * Delete from Google Calendar events older than 1 week:
 * 1. Past single events
 * 2. Past instances of recurring events
 * 3. Recurring events without instances
 */
public class DeleteAllPastEvents {
    private static final String OWNER_ROLE = "owner";

    public static void main(String[] args) throws IOException {
        var now = new DateTime(LocalDateTime.now().minusWeeks(1).toInstant(ZoneOffset.UTC).toEpochMilli());
        System.out.printf("Now: %s\n\n", now);
        var service = Helper.getService(CalendarScopes.CALENDAR);
        var calendars = listOwnedCalendars(service);
        var calendarInfoList = listAllEvents(service, calendars, now);
        for (var info : calendarInfoList) {
            var calendar = info.calendar;
            deleteSingleEvents(service, info, calendar);
            deleteRecurringEvents(service, info, calendar, now);
        }
    }

    private static void deleteSingleEvents(Calendar service, CalendarInfo info, CalendarListEntry calendar) throws IOException {
        System.out.printf("Calendar: '%s', Single events: %d\n", calendar.getSummary(), info.singleEvents.size());
        if (info.singleEvents.size() > 0) {
            System.out.printf("Deleting single events from '%s'...\n", calendar.getSummary());
            deleteEventsBatch(service, calendar, info.singleEvents);
            System.out.printf("Deleted single events from '%s'.\n\n", calendar.getSummary());
        }
    }

    private static void deleteRecurringEvents(Calendar service, CalendarInfo info, CalendarListEntry calendar,
                                              DateTime now) throws IOException {
        System.out.printf("Calendar: '%s' , Recurring events: %d\n", calendar.getSummary(), info.recurringEvents.size());
        for (Event recurringEvent : info.recurringEvents) {
            var pastInstances = listInstances(service, calendar.getId(), recurringEvent, now);
            if (pastInstances.size() > 0) {
                System.out.printf("Deleting %d past recurring event instances from '%s' ('%s')...\n",
                        pastInstances.size(), recurringEvent.getSummary(), calendar.getSummary());
                deleteEventsBatch(service, calendar, pastInstances);
                System.out.printf("Deleted past recurrent event instances from '%s'.\n\n", calendar.getSummary());
            } else {
                var allInstances = listInstances(service, calendar.getId(), recurringEvent, null);
                if (allInstances.isEmpty()) {
                    System.out.printf("Deleting recurring event with 0 instances: '%s' ('%s')...\n",
                            recurringEvent.getSummary(), calendar.getSummary());
                    deleteEventsBatch(service, calendar, List.of(recurringEvent));
                }
            }
        }
    }

    private static List<CalendarListEntry> listOwnedCalendars(Calendar service) throws IOException {
        var result = new ArrayList<CalendarListEntry>();
        String pageToken = null;
        do {
            var calendarList = service.calendarList().list()
                    .setPageToken(pageToken)
                    .setMinAccessRole(OWNER_ROLE)
                    .execute();
            result.addAll(calendarList.getItems());
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);
        return result;
    }

    private static List<Event> listEvents(Calendar service, String calendarId, DateTime now) throws IOException {
        var result = new ArrayList<Event>();
        String pageToken = null;
        do {
            var events = service.events().list(calendarId)
                    .setTimeMax(now)
                    .setPageToken(pageToken).execute();
            var active = events.getItems().stream()
                    .filter(event -> !CANCELLED_STATUS.equals(event.getStatus()))
                    .collect(Collectors.toList());
            result.addAll(active);
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
        return result;
    }

    private static List<CalendarInfo> listAllEvents(
            Calendar service, List<CalendarListEntry> calendars, DateTime now) throws IOException {
        List<CalendarInfo> result = new ArrayList<>();
        for (var calendar : calendars) {
            var events = listEvents(service, calendar.getId(), now);
            var singleEvents = filterSingleEvents(events);
            var recurringEvents = filterRecurringEvents(events);
            result.add(new CalendarInfo(calendar, singleEvents, recurringEvents));
        }
        return result;
    }

    private static List<Event> filterSingleEvents(List<Event> events) {
        return events.stream().filter(event -> event.getRecurrence() == null).collect(Collectors.toList());
    }

    private static List<Event> filterRecurringEvents(List<Event> events) {
        return events.stream().filter(event -> event.getRecurrence() != null).collect(Collectors.toList());
    }

    private static List<Event> listInstances(Calendar service, String calendarId, Event event, DateTime now)
            throws IOException {
        List<Event> result = new ArrayList<>();
        String pageToken = null;
        var eventId = event.getId();
        do {
            var instances = service.events().instances(calendarId, eventId).setPageToken(pageToken);
            if (now != null) {
                instances.setTimeMax(now);
            }
            var events = instances.execute();
            var active = events.getItems()
                    .stream().filter(ev -> !CANCELLED_STATUS.equals(ev.getStatus()))
                    .collect(Collectors.toList());
            result.addAll(active);
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
        return result;
    }

    private record CalendarInfo(CalendarListEntry calendar, List<Event> singleEvents, List<Event> recurringEvents) {
    }

    private static void deleteEventsBatch(Calendar service, CalendarListEntry calendar, List<Event> events) throws IOException {
        if (events.size() > 0) {
            var smallLists = splitList(events, MAX_BATCH_SIZE);
            for (List<Event> eventList : smallLists) {
                System.out.printf("Deleting %d events from '%s'...\n", events.size(), calendar.getSummary());
                var success = new AtomicInteger();
                var errors = new ArrayList<GoogleJsonError>();
                var callback = new JsonBatchCallback<Void>() {
                    public void onSuccess(Void event, HttpHeaders responseHeaders) {
                        success.incrementAndGet();
                    }

                    public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) {
                        errors.add(e);
                    }
                };
                var batch = service.batch();
                for (var createdEvent : eventList) {
                    service.events().delete(calendar.getId(), createdEvent.getId()).queue(batch, callback);
                }
                batch.execute();
                System.out.printf("Deleted events: success %d, errors: %s\n", success.get(), errors);
            }
        } else {
            System.out.println("Delete events: 0 events");
        }
    }
}

package calendar.event.recurring;

import calendar.Helper;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static calendar.Helper.MY_CALENDAR_ID;
import static calendar.Helper.createEvent;
import static calendar.Helper.deleteEvent;
import static calendar.Helper.getEventInstances;
import static org.assertj.core.api.Assertions.assertThat;


class ListRecurringEventInstances {
    private static final Calendar service = Helper.getService(CalendarScopes.CALENDAR);

    @Test
    void listInstances() throws IOException {
        var recurrence = List.of("RRULE:FREQ=DAILY;COUNT=2");
        var event = createEvent(service, MY_CALENDAR_ID, "Delete me", recurrence);
        String pageToken = null;
        var eventId = event.getId();
        do {
            var events = service.events().instances(MY_CALENDAR_ID, eventId)
                    .setPageToken(pageToken).execute();
            var eventList = events.getItems();
            eventList.forEach(ev -> System.out.printf("Id: '%s',  RecurringEventId: '%s', Summary: '%s', Start: '%s'\n",
                    ev.getId(), ev.getRecurringEventId(), ev.getSummary(), ev.getStart()));
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
        deleteEvent(service, MY_CALENDAR_ID, eventId);
    }

    @Test
    void getNextInstance() throws IOException {
        var count = 5;
        var recurrence = List.of("RRULE:FREQ=DAILY;COUNT=" + count);
        var startDate = "2021-10-24T10:00:00";
        var endDate = "2021-10-24T11:00:00";
        var event = createEvent(service, MY_CALENDAR_ID, "Delete me", startDate, endDate, recurrence);
        var eventId = event.getId();

        var nextOpt = getNextInstance(service, MY_CALENDAR_ID, eventId, "2021-10-26T22:00:00");
        assertThat(nextOpt).isNotEmpty();
        assertThat(nextOpt.get().getStart().getDateTime().toString()).isEqualTo("2021-10-27T13:00:00.000+03:00");

        var noNextOpt = getNextInstance(service, MY_CALENDAR_ID, eventId, "2021-10-30T22:00:00");
        assertThat(noNextOpt).isEmpty();

        service.events().delete(MY_CALENDAR_ID, eventId).execute();
    }

    @Test
    void listRecurringEventsWithoutInstances() {
        var instanceCount = 2;
        var recurrence = List.of("RRULE:FREQ=DAILY;COUNT=" + instanceCount);
        var event = createEvent(service, MY_CALENDAR_ID, "Delete me", recurrence);
        var instances = getEventInstances(service, MY_CALENDAR_ID, event.getId());
        assertThat(instances).hasSize(instanceCount);
        for (Event instance : instances) {
            deleteEvent(service, MY_CALENDAR_ID, instance.getId());
        }
        instances = getEventInstances(service, MY_CALENDAR_ID, event.getId());
        assertThat(instances).isEmpty();

        deleteEvent(service, MY_CALENDAR_ID, event.getId());
    }

    @SuppressWarnings("SameParameterValue")
    private static Optional<Event> getNextInstance(Calendar service, String calendarId, String eventId, String now) {
        try {
            return service.events().instances(calendarId, eventId)
                    .setTimeMin(DateTime.parseRfc3339(now))
                    .setMaxResults(1)
                    .setTimeZone("Europe/Moscow")
                    .execute().getItems().stream().findFirst();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

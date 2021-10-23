package calendar.event.recurring;

import calendar.Helper;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static calendar.Helper.MY_CALENDAR_ID;
import static calendar.Helper.createEvent;
import static calendar.Helper.deleteEvent;
import static calendar.Helper.getEventInstances;
import static org.assertj.core.api.Assertions.assertThat;

class DeleteRecurringEvent {
    private static final Calendar service = Helper.getService(CalendarScopes.CALENDAR);

    @Test
    void deleteWholeRecurringEvent() throws IOException {
        var recurrence = List.of("RRULE:FREQ=DAILY;COUNT=2");
        var event = createEvent(service, MY_CALENDAR_ID, "Delete me", recurrence);
        var eventId = event.getId();
        service.events().delete(MY_CALENDAR_ID, eventId).execute();
    }

    @Test
    void deleteRecurringEventInstance() throws IOException {
        var count = 5;
        var recurrence = List.of("RRULE:FREQ=DAILY;COUNT=" + count);
        var event = createEvent(service, MY_CALENDAR_ID, "Delete me", recurrence);
        var eventId = event.getId();

        var instances = getEventInstances(service, MY_CALENDAR_ID, eventId);
        assertThat(instances).hasSize(count);

        deleteEvent(service, MY_CALENDAR_ID, instances.get(0).getId());
        assertThat(getEventInstances(service, MY_CALENDAR_ID, eventId)).hasSize(count - 1);

        service.events().delete(MY_CALENDAR_ID, eventId).execute();
    }

    @Test
    void deleteInstancesBefore() throws IOException {
        var count = 5;
        var recurrence = List.of("RRULE:FREQ=DAILY;COUNT=" + count);
        var startDate = "2021-10-24T10:00:00";
        var endDate = "2021-10-24T11:00:00";
        var event = createEvent(service, MY_CALENDAR_ID, "Delete me", startDate, endDate, recurrence);
        var eventId = event.getId();

        var instances = getEventInstances(service, MY_CALENDAR_ID, eventId);
        assertThat(instances).hasSize(count);

        String pageToken = null;
        var deleted = 0;
        do {
            var events = service.events().instances(MY_CALENDAR_ID, eventId)
                    .setPageToken(pageToken)
                    .setTimeMax(DateTime.parseRfc3339("2021-10-26T22:00:00"))
                    .setTimeZone("Europe/Moscow")
                    .execute();
            var items = events.getItems();
            for (Event ev : items) {
                service.events().delete(MY_CALENDAR_ID, ev.getId()).execute();
            }
            deleted += items.size();
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
        assertThat(deleted).isEqualTo(3);
        assertThat(getEventInstances(service, MY_CALENDAR_ID, eventId)).hasSize(count - deleted);

        service.events().delete(MY_CALENDAR_ID, eventId).execute();
    }
}

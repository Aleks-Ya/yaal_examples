package calendar.event.recurring;

import calendar.Helper;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static calendar.Helper.MY_CALENDAR_ID;
import static calendar.Helper.deleteEvent;
import static calendar.Helper.getEventInstances;
import static org.assertj.core.api.Assertions.assertThat;

class CreateRecurringEvent {
    private static final Calendar service = Helper.getService(CalendarScopes.CALENDAR);

    @Test
    void createRecurringEvent() throws IOException {
        var event = new Event()
                .setSummary("Make tea daily")
                .setLocation("Kitchen, Home")
                .setDescription("Ginger tea");

        var startDateTime = new DateTime("2021-10-24T08:00:00");
        var start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Moscow");
        event.setStart(start);

        var endDateTime = new DateTime("2021-10-24T09:00:00");
        var end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Moscow");
        event.setEnd(end);

        event.setRecurrence(List.of("RRULE:FREQ=DAILY;COUNT=2"));

        var reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        var reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        event = service.events().insert(MY_CALENDAR_ID, event).execute();

        deleteEvent(service, MY_CALENDAR_ID, event.getId());
    }

    @Test
    void createForeverRecurringEvent() throws IOException {
        var event = new Event().setSummary("Make tea daily");

        var startDateTime = new DateTime("2021-10-24T08:00:00");
        var start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Moscow");
        event.setStart(start);

        var endDateTime = new DateTime("2021-10-24T09:00:00");
        var end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Moscow");
        event.setEnd(end);

        event.setRecurrence(List.of("RRULE:FREQ=DAILY"));

        event = service.events().insert(MY_CALENDAR_ID, event).execute();

        var instances = getEventInstances(service, MY_CALENDAR_ID, event.getId());
        assertThat(instances).hasSize(730);

        deleteEvent(service, MY_CALENDAR_ID, event.getId());
    }

}

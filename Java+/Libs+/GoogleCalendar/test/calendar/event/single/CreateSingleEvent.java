package calendar.event.single;

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

import static calendar.Helper.MY_CALENDAR_ID;
import static calendar.Helper.deleteEvent;

class CreateSingleEvent {
    private static final Calendar service = Helper.getService(CalendarScopes.CALENDAR);

    @Test
    void createSingleEvent() throws IOException {
        var event = new Event()
                .setSummary("Make tea")
                .setLocation("Kitchen, Home")
                .setDescription("Ginger tea");

        var startDateTime = new DateTime("2021-10-24T09:30:00");
        var start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Moscow");
        event.setStart(start);

        var endDateTime = new DateTime("2021-10-24T11:00:00");
        var end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Moscow");
        event.setEnd(end);

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
}

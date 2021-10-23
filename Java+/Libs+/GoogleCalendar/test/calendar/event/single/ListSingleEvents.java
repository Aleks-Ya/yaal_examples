package calendar.event.single;

import calendar.Helper;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static calendar.Helper.MY_CALENDAR_ID;


class ListSingleEvents {
    private static final Calendar service = Helper.getService(CalendarScopes.CALENDAR_READONLY);

    @Test
    void list() throws IOException {
        String pageToken = null;
        do {
            var events = service.events().list(MY_CALENDAR_ID)
                    .setTimeMin(DateTime.parseRfc3339("2021-04-03T10:00:00-07:00"))
                    .setTimeMax(DateTime.parseRfc3339("2021-08-03T10:00:00-07:00"))
                    .setPageToken(pageToken).execute();
            var eventList = events.getItems();
            eventList.forEach(event -> System.out.printf("%s - %s\n", event.getId(), event.getSummary()));
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
    }
}

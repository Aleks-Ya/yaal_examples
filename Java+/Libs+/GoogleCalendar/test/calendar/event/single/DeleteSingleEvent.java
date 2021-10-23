package calendar.event.single;

import calendar.Helper;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static calendar.Helper.HOLIDAYS_CALENDAR_ID;
import static calendar.Helper.MY_CALENDAR_ID;
import static calendar.Helper.createEvent;
import static calendar.Helper.deleteEvent;
import static calendar.Helper.get10Events;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class DeleteSingleEvent {
    private static final Calendar service = Helper.getService(CalendarScopes.CALENDAR);

    @Test
    void delete() throws IOException {
        var event = createEvent(service, MY_CALENDAR_ID, "Delete me");
        var eventId = event.getId();
        service.events().delete(MY_CALENDAR_ID, eventId).execute();
    }

    @Test
    void deleteDeletedEvent() {
        var event = createEvent(service, MY_CALENDAR_ID, "Delete me");
        var eventId = event.getId();
        deleteEvent(service, MY_CALENDAR_ID, eventId);
        var e = catchThrowableOfType(
                () -> service.events().delete(MY_CALENDAR_ID, eventId).execute(),
                GoogleJsonResponseException.class);
        assertThat(e.getStatusCode()).isEqualTo(410);
        assertThat(e.getMessage()).contains("""
                {
                  "code": 410,
                  "errors": [
                    {
                      "domain": "global",
                      "message": "Resource has been deleted",
                      "reason": "deleted"
                    }
                  ],
                  "message": "Resource has been deleted"
                }""");
    }

    @Test
    void deleteAbsentEvent() {
        var eventId = "not-existing-event-id";
        var e = catchThrowableOfType(
                () -> service.events().delete(MY_CALENDAR_ID, eventId).execute(),
                GoogleJsonResponseException.class);
        assertThat(e.getStatusCode()).isEqualTo(404);
        assertThat(e.getMessage()).contains("""
                {
                  "code": 404,
                  "errors": [
                    {
                      "domain": "global",
                      "message": "Not Found",
                      "reason": "notFound"
                    }
                  ],
                  "message": "Not Found"
                }""");
    }

    @Test
    void deleteEventNoWritePermission() {
        var event = get10Events(service, HOLIDAYS_CALENDAR_ID).get(0);
        var eventId = event.getId();
        var e = catchThrowableOfType(
                () -> service.events().delete(HOLIDAYS_CALENDAR_ID, eventId).execute(),
                GoogleJsonResponseException.class);
        assertThat(e.getStatusCode()).isEqualTo(403);
        assertThat(e.getMessage()).contains("""
                {
                  "code": 403,
                  "errors": [
                    {
                      "domain": "calendar",
                      "message": "This calendar is read-only.",
                      "reason": "virtualCalendarManipulation"
                    }
                  ],
                  "message": "This calendar is read-only."
                }""");
    }
}

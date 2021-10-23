package calendar.event.single;

import calendar.Helper;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static calendar.Helper.MY_CALENDAR_ID;
import static calendar.Helper.createEvent;
import static org.assertj.core.api.Assertions.assertThat;

class UpdateSingleEvent {
    private static final Calendar service = Helper.getService(CalendarScopes.CALENDAR);

    @Test
    void updateEvent() throws IOException {
        var oldEvent = createEvent(service, MY_CALENDAR_ID, "Delete me");
        var eventId = oldEvent.getId();

        var newSummary = "Delete me now";
        oldEvent.setSummary(newSummary);
        var updatedEvent = service.events()
                .update(MY_CALENDAR_ID, eventId, oldEvent)
                .execute();

        assertThat(updatedEvent.getId()).isEqualTo(eventId);
        assertThat(updatedEvent.getSummary()).isEqualTo(newSummary);

        service.events().delete(MY_CALENDAR_ID, eventId).execute();
    }

}

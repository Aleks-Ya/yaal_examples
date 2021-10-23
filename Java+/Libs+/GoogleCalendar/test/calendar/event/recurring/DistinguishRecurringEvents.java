package calendar.event.recurring;

import calendar.Helper;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import org.junit.jupiter.api.Test;

import java.util.List;

import static calendar.Helper.MY_CALENDAR_ID;
import static calendar.Helper.createEvent;
import static calendar.Helper.deleteEvent;
import static calendar.Helper.getEventInstances;
import static org.assertj.core.api.Assertions.assertThat;


class DistinguishRecurringEvents {
    private static final Calendar service = Helper.getService(CalendarScopes.CALENDAR);

    @Test
    void distinguish() {
        var singleEvent = createEvent(service, MY_CALENDAR_ID, "Delete single");
        var recurrence = List.of("RRULE:FREQ=DAILY;COUNT=2");
        var recurrentEvent = createEvent(service, MY_CALENDAR_ID, "Delete recurrent", recurrence);
        var instance = getEventInstances(service, MY_CALENDAR_ID, recurrentEvent.getId()).get(0);

        assertThat(isRecurrent(singleEvent)).isFalse();
        assertThat(isRecurrent(recurrentEvent)).isTrue();
        assertThat(isRecurrent(instance)).isFalse();

        assertThat(isInstance(singleEvent)).isFalse();
        assertThat(isInstance(recurrentEvent)).isFalse();
        assertThat(isInstance(instance)).isTrue();

        deleteEvent(service, MY_CALENDAR_ID, singleEvent.getId());
        deleteEvent(service, MY_CALENDAR_ID, recurrentEvent.getId());
    }

    private boolean isRecurrent(Event event) {
        return event.getRecurrence() != null;
    }

    private boolean isInstance(Event event) {
        return event.getRecurringEventId() != null;
    }
}

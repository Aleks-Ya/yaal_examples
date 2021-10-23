package calendar.batch;

import calendar.Helper;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static calendar.Helper.MY_CALENDAR_ID;
import static org.assertj.core.api.Assertions.assertThat;

class CreateDeleteEventBatch {
    private static final Calendar service = Helper.getService(CalendarScopes.CALENDAR);

    @Test
    void batchRequests() throws IOException {
        var createdSuccess = createEventsBatch();
        deleteEventsBatch(createdSuccess);
    }

    private List<Event> createEventsBatch() throws IOException {
        var count = 3;
        var success = new ArrayList<Event>();
        var errors = new ArrayList<GoogleJsonError>();
        var callback = new JsonBatchCallback<Event>() {
            public void onSuccess(Event event, HttpHeaders responseHeaders) {
                success.add(event);
            }

            public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) {
                errors.add(e);
            }
        };
        var batch = service.batch();

        for (var i = 0; i < count; i++) {
            var event = new Event().setSummary("Delete me " + i)
                    .setStart(new EventDateTime().setDateTime(new DateTime("2021-10-23T11:00:00")))
                    .setEnd(new EventDateTime().setDateTime(new DateTime("2021-10-24T11:00:00")));
            service.events().insert(MY_CALENDAR_ID, event).queue(batch, callback);
        }
        batch.execute();
        assertThat(errors).isEmpty();
        assertThat(success).hasSize(count);
        return success;
    }

    private void deleteEventsBatch(List<Event> events) throws IOException {
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
        for (var createdEvent : events) {
            service.events().delete(MY_CALENDAR_ID, createdEvent.getId()).queue(batch, callback);
        }
        batch.execute();
        assertThat(errors).isEmpty();
        assertThat(success.get()).isEqualTo(events.size());
    }

}

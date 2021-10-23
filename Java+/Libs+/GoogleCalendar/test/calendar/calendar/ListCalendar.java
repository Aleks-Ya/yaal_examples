package calendar.calendar;

import calendar.Helper;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class ListCalendar {
    private static final Calendar service = Helper.getService(CalendarScopes.CALENDAR_READONLY);
    private static final String OWNER_ROLE = "owner";
    private static final String WRITER_ROLE = "writer";
    private static final String READER_ROLE = "reader";
    private static final String FREE_BUSY_READER_ROLE = "freeBusyReader";

    @Test
    void listAllCalendars() throws IOException {
        String pageToken = null;
        var allCalendars = new ArrayList<CalendarListEntry>();
        var ownedCalendars = new ArrayList<CalendarListEntry>();
        var writerCalendars = new ArrayList<CalendarListEntry>();
        var readerCalendars = new ArrayList<CalendarListEntry>();
        var freeBusyReaderCalendars = new ArrayList<CalendarListEntry>();
        do {
            var calendarList = service.calendarList().list().setPageToken(pageToken).execute();
            var items = calendarList.getItems();
            for (var entry : items) {
                allCalendars.add(entry);
                var accessRole = entry.getAccessRole();
                switch (accessRole) {
                    case OWNER_ROLE -> ownedCalendars.add(entry);
                    case WRITER_ROLE -> writerCalendars.add(entry);
                    case READER_ROLE -> readerCalendars.add(entry);
                    case FREE_BUSY_READER_ROLE -> freeBusyReaderCalendars.add(entry);
                    default -> throw new RuntimeException("Unknown access role: " + accessRole);
                }
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);

        printCalendarItemList(allCalendars, "all");
        printCalendarItemList(ownedCalendars, OWNER_ROLE);
        printCalendarItemList(writerCalendars, WRITER_ROLE);
        printCalendarItemList(readerCalendars, READER_ROLE);
        printCalendarItemList(freeBusyReaderCalendars, FREE_BUSY_READER_ROLE);
    }

    @Test
    void listOwnedCalendars() throws IOException {
        String pageToken = null;
        do {
            var calendarList = service.calendarList().list()
                    .setPageToken(pageToken)
                    .setMinAccessRole(OWNER_ROLE)
                    .execute();
            var ownedCalendars = calendarList.getItems();
            pageToken = calendarList.getNextPageToken();
            printCalendarItemList(ownedCalendars, OWNER_ROLE);
        } while (pageToken != null);
    }


    private static void printCalendarItemList(List<CalendarListEntry> entries, String role) {
        System.out.printf("Calendars '%s' (%d): %s\n", role, entries.size(),
                entries.stream().map(CalendarListEntry::getSummary).collect(Collectors.joining(", ")));
    }

}

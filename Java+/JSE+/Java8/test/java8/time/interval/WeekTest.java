package java8.time.interval;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

class WeekTest {
    private static final LocalDate jan1 = of(2020, 1, 1);
    private static final LocalDate jan6 = of(2020, 1, 6);
    private static final LocalDate jun30 = of(2020, 6, 30);
    private static final LocalDate dec27 = of(2020, 12, 27);
    private static final LocalDate dec31 = of(2020, 12, 31);

    @Test
    void dateToWeekOfYear() {
        assertThat(weekByDate(jan1)).isEqualTo(1);
        assertThat(weekByDate(jan6)).isEqualTo(2);
        assertThat(weekByDate(jun30)).isEqualTo(27);
        assertThat(weekByDate(dec27)).isEqualTo(52);
        assertThat(weekByDate(dec31)).isEqualTo(53);
    }

    @Test
    void dateToFirstDayOfWeek() {
        assertThat(firstDayOfWeek(jan1)).isEqualTo(of(2020, 1, 1));
        assertThat(firstDayOfWeek(jan6)).isEqualTo(of(2020, 1, 6));
        assertThat(firstDayOfWeek(jun30)).isEqualTo(of(2020, 6, 29));
        assertThat(firstDayOfWeek(dec27)).isEqualTo(of(2020, 12, 21));
        assertThat(firstDayOfWeek(dec31)).isEqualTo(of(2020, 12, 28));
    }

    @Test
    void dateToLastDayOfWeek() {
        assertThat(lastDayOfWeek(jan1)).isEqualTo(of(2020, 1, 5));
        assertThat(lastDayOfWeek(jan6)).isEqualTo(of(2020, 1, 12));
        assertThat(lastDayOfWeek(jun30)).isEqualTo(of(2020, 7, 5));
        assertThat(lastDayOfWeek(dec27)).isEqualTo(of(2020, 12, 27));
        assertThat(lastDayOfWeek(dec31)).isEqualTo(of(2020, 12, 31));
    }

    private static Integer weekByDate(LocalDate date) {
        return date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    private static LocalDate firstDayOfWeek(LocalDate date) {
        var byMonday = date.with(DayOfWeek.MONDAY);
        var byYear = date.withDayOfYear(1);
        return byMonday.isAfter(byYear) ? byMonday : byYear;
    }

    private static LocalDate lastDayOfWeek(LocalDate date) {
        var bySunday = date.with(DayOfWeek.SUNDAY);
        var byYear = date.withDayOfYear(date.lengthOfYear());
        return bySunday.isBefore(byYear) ? bySunday : byYear;
    }

}

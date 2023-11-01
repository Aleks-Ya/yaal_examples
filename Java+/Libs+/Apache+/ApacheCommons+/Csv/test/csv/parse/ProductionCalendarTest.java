package csv.parse;

import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Month;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ProductionCalendarTest {

    private static Map<Integer, Year> parseCalendar() throws IOException {
        var years = new HashMap<Integer, Year>();
        var file = ResourceUtil.resourceToFile(ProductionCalendarTest.class, "production_calendar.csv");
        var format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .build();
        try (Reader in = new FileReader(file, StandardCharsets.UTF_8);
             var parser = format.parse(in)) {
            for (var record : parser) {
                var year = Integer.parseInt(record.get("Год/Месяц"));

                Map<Month, List<Integer>> holidaysInMonth = new HashMap<>();
                holidaysInMonth.put(Month.JANUARY, parseHolidays(record.get("Январь")));
                holidaysInMonth.put(Month.FEBRUARY, parseHolidays(record.get("Февраль")));
                holidaysInMonth.put(Month.MARCH, parseHolidays(record.get("Март")));
                holidaysInMonth.put(Month.APRIL, parseHolidays(record.get("Апрель")));
                holidaysInMonth.put(Month.MAY, parseHolidays(record.get("Май")));
                holidaysInMonth.put(Month.JUNE, parseHolidays(record.get("Июнь")));
                holidaysInMonth.put(Month.JULY, parseHolidays(record.get("Июль")));
                holidaysInMonth.put(Month.AUGUST, parseHolidays(record.get("Август")));
                holidaysInMonth.put(Month.SEPTEMBER, parseHolidays(record.get("Сентябрь")));
                holidaysInMonth.put(Month.OCTOBER, parseHolidays(record.get("Октябрь")));
                holidaysInMonth.put(Month.NOVEMBER, parseHolidays(record.get("Ноябрь")));
                holidaysInMonth.put(Month.DECEMBER, parseHolidays(record.get("Декабрь")));

                var totalWorkingDays = Integer.parseInt(record.get("Всего рабочих дней"));
                var totalHolidays = Integer.parseInt(record.get("Всего праздничных и выходных дней"));
                var workingHours40 = new BigDecimal(record.get("Количество рабочих часов при 40-часовой рабочей неделе"));
                var workingHours36 = new BigDecimal(record.get("Количество рабочих часов при 36-часовой рабочей неделе"));
                var workingHours24 = new BigDecimal(record.get("Количество рабочих часов при 24-часовой рабочей неделе"));

                var yearPojo = new Year(year, holidaysInMonth, totalWorkingDays, totalHolidays, workingHours40,
                        workingHours36, workingHours24);
                years.put(year, yearPojo);
            }
            return years;
        }
    }

    private static List<Integer> parseHolidays(String daysStr) {
        if (daysStr == null) {
            return Collections.emptyList();
        }
        return Stream.of(daysStr.split(","))
                .map(dayStr -> dayStr.replace("*", ""))
                .map(dayStr -> dayStr.replace("+", ""))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    @Test
    void parseProductionCalendar() throws IOException {
        var years = parseCalendar();
        assertThat(years).hasSize(27);
        var year = 2020;
        var year2020 = years.get(year);
        assertThat(year2020.getHolidays(Month.FEBRUARY)).containsExactly(1, 2, 8, 9, 15, 16, 22, 23, 24, 29);
        assertThat(year2020.getHolidays(Month.APRIL)).containsExactly(4, 5, 11, 12, 18, 19, 25, 26, 30);
        assertThat(year2020.getYear()).isEqualTo(year);
        assertThat(year2020.getTotalWorkingDays()).isEqualTo(250);
        assertThat(year2020.getTotalHolidays()).isEqualTo(116);
        assertThat(year2020.getWorkingHours40()).isEqualTo(BigDecimal.valueOf(1995));
        assertThat(year2020.getWorkingHours36()).isEqualTo(BigDecimal.valueOf(1795));
        assertThat(year2020.getWorkingHours24()).isEqualTo(BigDecimal.valueOf(1195));
    }

    private static class Year {
        private final int year;
        private final Map<Month, List<Integer>> holidaysInMonth;
        private final int totalWorkingDays;
        private final int totalHolidays;
        private final BigDecimal workingHours40;
        private final BigDecimal workingHours36;
        private final BigDecimal workingHours24;

        Year(int year,
             Map<Month, List<Integer>> holidaysInMonth,
             int totalWorkingDays,
             int totalHolidays,
             BigDecimal workingHours40,
             BigDecimal workingHours36,
             BigDecimal workingHours24) {
            this.year = year;
            this.holidaysInMonth = holidaysInMonth;
            this.totalWorkingDays = totalWorkingDays;
            this.totalHolidays = totalHolidays;
            this.workingHours40 = workingHours40;
            this.workingHours36 = workingHours36;
            this.workingHours24 = workingHours24;
        }


        int getYear() {
            return year;
        }

        List<Integer> getHolidays(Month month) {
            return holidaysInMonth.get(month);
        }

        int getTotalWorkingDays() {
            return totalWorkingDays;
        }

        int getTotalHolidays() {
            return totalHolidays;
        }

        BigDecimal getWorkingHours40() {
            return workingHours40;
        }

        BigDecimal getWorkingHours36() {
            return workingHours36;
        }

        BigDecimal getWorkingHours24() {
            return workingHours24;
        }
    }
}
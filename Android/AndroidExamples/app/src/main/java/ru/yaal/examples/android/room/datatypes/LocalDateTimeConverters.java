package ru.yaal.examples.android.room.datatypes;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeConverters {
    private static final ZoneOffset offset = OffsetDateTime.now().getOffset();

    @TypeConverter
    public LocalDateTime fromTimestamp(Long value) {
        if (value == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(value);
        return LocalDateTime.ofInstant(instant, offset);
    }

    @TypeConverter
    public Long dateToTimestamp(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.toInstant(offset).toEpochMilli();
    }
}

package ru.yaal.examples.android.room.datatypes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
public class DataTypesUser {
    @PrimaryKey
    public int id;

    @ColumnInfo
    public String stringField;

    @ColumnInfo
    public int intField;

    @ColumnInfo
    @TypeConverters(DateConverters.class)
    public Date dateField;

    @ColumnInfo
    @TypeConverters(LocalDateTimeConverters.class)
    public LocalDateTime localDateTimeField;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataTypesUser that = (DataTypesUser) o;
        return id == that.id &&
                intField == that.intField &&
                Objects.equals(stringField, that.stringField) &&
                Objects.equals(dateField, that.dateField) &&
                Objects.equals(localDateTimeField, that.localDateTimeField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stringField, intField, dateField, localDateTimeField);
    }
}


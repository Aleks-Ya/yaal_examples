package ru.yaal.examples.android.room.datatypes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode
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
}


package ru.yaal.examples.android.room.datatypes;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataTypesUser.class}, version = 1)
public abstract class DataTypesDatabase extends RoomDatabase {
    public abstract DataTypesUserDao userDao();
}


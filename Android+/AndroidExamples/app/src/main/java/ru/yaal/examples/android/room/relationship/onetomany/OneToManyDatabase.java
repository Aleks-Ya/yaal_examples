package ru.yaal.examples.android.room.relationship.onetomany;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {OneToManyUser.class, OneToManyBook.class}, version = 1)
public abstract class OneToManyDatabase extends RoomDatabase {
    public abstract OneToManyUserDao userDao();

    public abstract OneToManyBookDao bookDao();
}


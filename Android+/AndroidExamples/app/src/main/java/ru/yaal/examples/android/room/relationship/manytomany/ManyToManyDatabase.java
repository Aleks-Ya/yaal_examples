package ru.yaal.examples.android.room.relationship.manytomany;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ManyToManyUser.class, ManyToManyBook.class, UserBookJoin.class}, version = 1)
public abstract class ManyToManyDatabase extends RoomDatabase {
    public abstract ManyToManyUserDao userDao();

    public abstract ManyToManyBookDao bookDao();

    public abstract UserBookJoinDao userBookJoinDao();
}


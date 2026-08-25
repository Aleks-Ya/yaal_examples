package ru.yaal.examples.android.room.relationship.manytomany;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Entity(primaryKeys = {"userId", "bookId"},
        foreignKeys = {
                @ForeignKey(entity = ManyToManyUser.class,
                        parentColumns = "uid",
                        childColumns = "userId"),
                @ForeignKey(entity = ManyToManyBook.class,
                        parentColumns = "uid",
                        childColumns = "bookId")
        })
public class UserBookJoin {
    public int userId;
    public int bookId;
}

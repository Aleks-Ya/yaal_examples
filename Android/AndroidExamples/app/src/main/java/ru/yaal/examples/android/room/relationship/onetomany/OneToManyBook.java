package ru.yaal.examples.android.room.relationship.onetomany;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = OneToManyUser.class,
        parentColumns = "uid",
        childColumns = "user_id"))
public class OneToManyBook {
    @PrimaryKey
    public int bookId;

    public String title;

    @ColumnInfo(name = "user_id")
    public int userId;
}

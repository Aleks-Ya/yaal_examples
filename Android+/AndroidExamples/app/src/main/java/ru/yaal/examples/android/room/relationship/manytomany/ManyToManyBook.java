package ru.yaal.examples.android.room.relationship.manytomany;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode
public class ManyToManyBook {
    @PrimaryKey
    public int uid;
    public String title;
}

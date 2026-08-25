package ru.yaal.examples.android.room.relationship.manytomany;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ManyToManyUserDao {
    @Query("SELECT * FROM ManyToManyUser")
    List<ManyToManyUser> getAll();

    @Query("SELECT * FROM ManyToManyUser WHERE uid IN (:userIds)")
    List<ManyToManyUser> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM ManyToManyUser WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    ManyToManyUser findByName(String first, String last);

    @Insert
    void insertAll(ManyToManyUser... users);

    @Delete
    void delete(ManyToManyUser user);
}

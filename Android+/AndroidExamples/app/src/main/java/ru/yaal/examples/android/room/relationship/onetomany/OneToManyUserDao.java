package ru.yaal.examples.android.room.relationship.onetomany;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OneToManyUserDao {
    @Query("SELECT * FROM OneToManyUser")
    List<OneToManyUser> getAll();

    @Query("SELECT * FROM OneToManyUser WHERE uid IN (:userIds)")
    List<OneToManyUser> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM OneToManyUser WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    OneToManyUser findByName(String first, String last);

    @Insert
    void insertAll(OneToManyUser... users);

    @Delete
    void delete(OneToManyUser user);
}

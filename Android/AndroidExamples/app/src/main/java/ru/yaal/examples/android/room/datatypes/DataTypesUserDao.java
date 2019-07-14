package ru.yaal.examples.android.room.datatypes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataTypesUserDao {
    @Query("SELECT * FROM DataTypesUser")
    List<DataTypesUser> getAll();

    @Query("SELECT * FROM DataTypesUser WHERE id = :userIds")
    DataTypesUser loadById(int userIds);

    @Insert
    void insertAll(DataTypesUser... users);

    @Delete
    void delete(DataTypesUser user);
}

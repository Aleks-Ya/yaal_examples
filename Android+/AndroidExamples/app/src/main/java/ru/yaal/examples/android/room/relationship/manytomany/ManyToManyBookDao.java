package ru.yaal.examples.android.room.relationship.manytomany;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ManyToManyBookDao {
    @Query("SELECT * FROM ManyToManyBook")
    List<ManyToManyBook> getAll();

    @Query("SELECT * FROM ManyToManyBook WHERE uid IN (:bookIds)")
    List<ManyToManyBook> loadAllByIds(int[] bookIds);

    @Query("SELECT * FROM ManyToManyBook WHERE title LIKE :title LIMIT 1")
    ManyToManyBook findByTitle(String title);

    @Insert
    void insertAll(ManyToManyBook... books);

    @Delete
    void delete(ManyToManyBook book);
}

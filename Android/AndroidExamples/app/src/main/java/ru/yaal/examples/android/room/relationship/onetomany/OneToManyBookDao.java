package ru.yaal.examples.android.room.relationship.onetomany;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OneToManyBookDao {
    @Query("SELECT * FROM OneToManyBook")
    List<OneToManyBook> getAll();

    @Query("SELECT * FROM OneToManyBook WHERE bookId IN (:bookIds)")
    List<OneToManyBook> loadAllByIds(int[] bookIds);

    @Query("SELECT * FROM OneToManyBook WHERE title LIKE :title LIMIT 1")
    OneToManyBook findByTitle(String title);

    @Insert
    void insertAll(OneToManyBook... books);

    @Delete
    void delete(OneToManyBook book);
}

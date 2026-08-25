package ru.yaal.examples.android.room.relationship.manytomany;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserBookJoinDao {
    @Insert
    void insertAll(UserBookJoin... userBookJoin);

    @Query("SELECT * FROM ManyToManyUser " +
            "INNER JOIN UserBookJoin " +
            "ON ManyToManyUser.uid=UserBookJoin.userId " +
            "WHERE UserBookJoin.bookId=:bookId")
    List<ManyToManyUser> getUsersForBook(final int bookId);

    @Query("SELECT * FROM ManyToManyBook " +
            "INNER JOIN UserBookJoin " +
            "ON ManyToManyBook.uid=UserBookJoin.bookId " +
            "WHERE UserBookJoin.userId=:userId")
    List<ManyToManyBook> getBooksForUser(final int userId);

}
